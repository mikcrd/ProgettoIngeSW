package model;
import controller.*;

import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetePetri", propOrder = {
	    "marcature"
		
	})
@XmlSeeAlso({
    RetePetriP.class
})

//@XmlTransient
public class RetePetri extends AbstractRete implements ICercaTopologiaBase {
	
		
		/*@invariant marcature != null;
		  @(\forall int i; 0<=i<numPos; marcature[i]>=0);
		  @*/
		
		@XmlElementWrapper(name="marcature")
		@XmlElement(name="marcatura", required = true)
		/*@spec_public@*/int [] marcature;
		
		
		@XmlTransient
		ArchivioReti arch;
	
		public RetePetri() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
		}

		public RetePetri(ArchivioReti arch) { //dependency injection
			this.arch = arch;	 
		}
		
		
		public int getTrans() {
			return numTrans;
		}

		public void aggiungiRelazione(RelazionePetri r) {
			this.getRelazioni().add(r);		
		}

		public int[] getMarcature() {
			return marcature;
		}
		
		public int getMarcatura(int i) {
			return marcature [i];
		}
		
		public void inizializzaMarcature(int num) {
			marcature= new int [num];
		}
		
		public void setMarcatura(int marc, int i) {
			marcature[i]=marc;
		}	
		
		/**
		 * Se almeno un peso o una marcatura non sono validi, ritorna falso
		 * marcature: da 0 a +infinito
		 * pesi: da 1 a +infinito
		 */
		@Override
		  public boolean isCorrect() {
           for(AbstractRelazioneDiFlusso rel : getRelazioni()) {
        	   if(rel instanceof RelazionePetri) {
	        	   if(((RelazionePetri)rel).getPeso() <= 0 || !controlloMarcature())
	        		   return false; break;
        	   }
           }
		   return true;  
		}
		
		public boolean controlloMarcature() {
			for(int marcatura : getMarcature()) {
				if(marcatura < 0) return false; break;
			}
			return true;
		}
		
		/*@assignable numPos, numTrans, name, relazioni, marcature;@*/
		public RetePetri creaRete() {
			 if(arch.noRetiInArchivio()) {
       		    	Controller.messErroreAggiungiPrimaUnaRete(); 
       		    	return null; }
			 else {
			           // deve visualizzare solo reti -> vedi xml reti
						Rete r= new Rete();
						arch.visualizzaSoloRetiArchivio();
						String nomeRete = Controller.scegliRetePerCostruireRetePetri();
						try {
							r=(Rete)arch.trovaRete(nomeRete);
						} catch (Exception e) {
							Controller.messErroreReteNonTrovata();
							return null;
						}
						
						// se sbaglia a scrivere ...
						if(r!=null) {
							this.setName(Controller.nomeRetePetri());
							r.contaPosizioni();				
							this.inizializzaReteP(r);
							Controller.cornice();
						}
			      }
			
			return this;
		}
		
		@Override
		public void visualizzaElencoParziale() {
             arch.visualizzaNomeRetiPN();			
		}

		@Override
		public boolean retiInArchivio() {
			if(arch.getArchivio() != null && !(arch.getArchivio().isEmpty()) && !(arch.noRetiPNInArchivio()))  {
				return true;
			}
			return false;
		}

		public void aggiungiMarcature(int np) {
			for(int i=0; i<np; i++) {
				int j=i;
				marcature[i]=Controller.messInserimentoMarcatura(++j);
			}
		}
		
		
		//ritorna il numero massimo della posizione
		public void contaPosizioni() {
			int max=0;
			for(AbstractRelazioneDiFlusso r :  relazioni) {
				if(((RelazionePetri)r).getPosizione()>max)
					max=((RelazionePetri)r).getPosizione();
				}
			numPos=max;
		}
		
		
		//ritorna il numero massimo delle transizioni 
		public void contaTransizioni() {
			int max=0;
			for(AbstractRelazioneDiFlusso r :  relazioni) {
					if(((RelazionePetri)r).getTransizione()>max)
						max=((RelazionePetri)r).getTransizione();
			}
			numTrans=max;
		}
		
		private void inizializzaReteP(Rete re) {
			this.numPos=re.getPos();
			
			marcature = new int [numPos];
			this.aggiungiMarcature(numPos);
			this.aggiungiPesiRelazione(re);
		}
		
		public void aggiungiPesiRelazione(Rete re) {
			for (AbstractRelazioneDiFlusso rel : re.getRelazioni()) {
				System.out.println();
				int peso = Controller.messInserimentoPeso(rel);
				RelazionePetri pn = new RelazionePetri(((RelazioneDiFlusso)rel), peso);
				this.aggiungiRelazione(pn);
			}
		}

		
		@Override
		public List<AbstractRelazioneDiFlusso> getTopologiaSottostante() {
			List<AbstractRelazioneDiFlusso> arrayRDF = new ArrayList<AbstractRelazioneDiFlusso>();
			for(AbstractRelazioneDiFlusso relP : getRelazioni()) {
				RelazioneDiFlusso relF = new RelazioneDiFlusso(relP.getPosizione(), 
						relP.getTransizione(), relP.isInOut());
				arrayRDF.add(relF);
			}
			return arrayRDF;
		}

		
		//la rete di Petri da file deve basarsi su una rete già presente nell'archivio
				@Override
				public boolean controlloPerSalvataggioDaFile(AbstractRete rete) {
					if(rete.getRelazioni().equals(getTopologiaSottostante()))
						return true;
					//Controller.messAssenzaReteSuCuiCostruireRetePetri();
					return false;
				}

/*	
		public void stampaMarcature() {
			System.out.println("Marcature:");
			for (int i =0; i<marcature.length; i++) {
				int j=i;
				System.out.println("Posizione " + ++j + " marcatura " + marcature[i]);
			}
		}*/
		
				@Override
				public boolean stessaTopologia(AbstractRete abs) {
					
					if (getClass() != abs.getClass())
						return false;
					
					RetePetri other = (RetePetri) abs;
					if (!Arrays.equals(marcature, other.marcature))
						return false;
					if (relazioni == null) {
						if (other.relazioni != null)
							return false;
					} else if (!relazioni.equals(other.relazioni)) {
						return false;
					    }
					return true;
				}
		
				
		
		@Override
		public void stampaRete() {
			Controller.stampaRetePetriController(this);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((relazioni == null) ? 0 : relazioni.hashCode());
			result = prime * result + Arrays.hashCode(marcature);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (getClass() != obj.getClass())
				return false;
			
			RetePetri other = (RetePetri) obj;
			if (!Arrays.equals(marcature, other.marcature))
				return false;
			if (relazioni == null) {
				if (other.relazioni != null)
					return false;
			} else if (relazioni.equals(other.relazioni)) {
				Controller.messErroreStessaTopologia();
				return true;
			    }
			
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (name.equals(other.name)) { //rel diverse e nome uguale
				return false;  //-> xchè poi cambio nome in ArchivioReti
			}

			return false;
		}

		

}
