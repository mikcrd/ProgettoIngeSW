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
/*		
		public void simulaRete() {
			boolean risposta;
			int numTransAbil;
			this.contaTransizioni();
			this.contaPosizioni();
			boolean [] abilitate = new boolean[numTrans];
			//stampo le marcature prima dello scatto della transizione
			this.stampaMarcature();
			do {	
				numTransAbil=this.contaTransizioniAbilitate();
				abilitate=this.cercaTransizioniAbilitate();
				if (numTransAbil==1) 
				{
					
					//cerco con il ciclo nel vettore di booleani la transizione abilitata e la faccio scattare
					for(int i=0; i<numTrans; i++) {
						if (abilitate[i]) {
							//scatta transizione
							this.scattaTransizione(++i);
							System.out.println("dopo lo scatto della transizione la marcatura e':");
							this.stampaMarcature();
							break;
						}
					}
				
				}else if(numTransAbil>1)
				{
					//utente sceglie quale delle transizioni abilitate fare scattare
					int transUtente=LeggiInput.leggiInteroPositivo("inserire la transizione che si vuole fare scattare");
					if(abilitate[transUtente-1]) {
						this.scattaTransizione(transUtente);
						this.stampaMarcature();
					}
				}else if(numTransAbil==0) {
					System.out.println("Nessuna transizione abilitata, blocco critico raggiunto");
					break;
				}
				//stampa rete dopo scatto transizione 
				
				risposta=LeggiInput.yesOrNo("vuoi proseguire con la simulazione?");
				
			}while (risposta);
			System.out.println("Simulazione terminata");
		}
				
		 
			
		public int contaTransizioniAbilitate() {
			int contatore=0;
			for(AbstractRelazioneDiFlusso rel: this.relazioni) {
				if (rel.inOut==true && ((RelazionePetri)rel).getPeso()<=this.getMarcatura(rel.getPosizione()-1) ) {
					System.out.println("transizione abilitata:" + rel.getTransizione());
					//incrementa il contatore delle transizioni abilitate 
					contatore++;
					
				}
			}
			
			return contatore;
		}
		
		public boolean[]  cercaTransizioniAbilitate() {
		
			boolean [] transAbilitate= new boolean [numTrans];
			for(AbstractRelazioneDiFlusso rel: this.relazioni) {
				if (rel.inOut==true && ((RelazionePetri)rel).getPeso()<=this.getMarcatura(rel.getPosizione()-1) ) {
					transAbilitate[rel.getPosizione()-1]=true;
				}
			}
			
			return transAbilitate;
		}
	
		
		public boolean [] trovaPostiPredecessori(int trans) {
			boolean[] pred = new boolean[numPos];
			for(AbstractRelazioneDiFlusso relazione : getRelazioni()) {
				for(int i=0; i<numPos; i++) {
					if(relazione.getTransizione()==trans && relazione.isInOut()==true) {
						pred[i] = true;
					}
				}
			}
			return pred;	
		}
		
		public boolean [] trovaPostiSucessori(int trans) {
			boolean[] succ = new boolean[numPos];
			for(AbstractRelazioneDiFlusso relazione : getRelazioni()) {
				for(int i=0; i<numPos; i++) {
					if(relazione.getTransizione()==trans && relazione.isInOut()==false) {
						succ[i]=true;
					}
				}
			}
			return succ;	
		}
		
		public void scattaTransizione(int trans) {
			boolean[] pred = new boolean[numPos];
			boolean[] succ = new boolean[numPos];
			pred=this.trovaPostiPredecessori(trans);
			succ=this.trovaPostiSucessori(trans);
			for(int i=0; i<numPos; i++){
				if (pred[i]) {
					int j=i;
					for(AbstractRelazioneDiFlusso rel: relazioni) {
						if (rel.isInOut() && rel.getPosizione()==j+1 && rel.getTransizione()==trans) {
							marcature[i]= marcature[i] - ((RelazionePetri)rel).getPeso();
						}
					}
				}if (succ[i]) {
					int j=i;
					for(AbstractRelazioneDiFlusso rel: relazioni) {
						if (!rel.isInOut() && rel.getPosizione()==j+1 && rel.getTransizione()==trans) {
							marcature[i]= marcature[i] + ((RelazionePetri)rel).getPeso();
						}
					}
				}
			
			}
			
		}
*/
		
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
				if(rete.getRelazioni().equals(getTopologiaSottostante())) return true;
			
			Controller.messAssenzaReteSuCuiCostruireRetePetri();
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
