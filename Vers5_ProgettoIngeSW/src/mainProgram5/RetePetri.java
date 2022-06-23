package mainProgram5;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetePetri", propOrder = {
	    "marcature"
	})
@XmlSeeAlso({
    RetePetriP.class
})
public class RetePetri extends AbstractRete  {
	
		private final static String MESS_NOME = "Inserisci il nome della rete di Petri da aggiungere: ";
		private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
		private static final String SCEGLI_RETE = "Scegli una delle reti nell'archivio: ";
		private static final String VUOI_QUESTA_RETE = "Vuoi scegliere questa rete? ";
		private static final String PESO = "Immetti un peso per la relazione corrente: ";
		private static final String ERR_PESO="il peso deve essere un valore maggiore o uguale a 1";
		private static final String MARCATURA="inserire il valore di marcatura";
		private static final String MESS_NORETETOPOLOGIA = "La rete di Petri che si vuole salvare non ha una rete "
				+ "con la stessa topologia a cui appoggiarsi. \nCrea prima una rete con la stessa topologia";
		
		/*@invariant marcature != null;
		  @(\forall int i; 0<=i<numPos; marcature[i]>=0);
		  @*/
		
		@XmlElementWrapper(name="marcature")
		@XmlElement(name="marcatura", required = true)
		/*@spec_public@*/int [] marcature;
		
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
			// deve visualizzare solo reti -> vedi xml reti
			Rete r= new Rete();
			arch.visualizzaSoloRetiArchivio();
			String nomeRete = LeggiInput.leggiStringaNonVuota(SCEGLI_RETE);
			try {
				r=(Rete)arch.trovaRete(nomeRete);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(MESS_NON_TROVATA);
				return null;
			}
			
			// se sbaglia a scrivere ...
			if(r!=null) {
				this.setName(LeggiInput.leggiStringaNonVuota(MESS_NOME));
				r.contaPosizioni();				
				this.inizializzaReteP(r);
				System.out.println("----------------------------------------------");
			}
			
			return this;
		}
		
		public void aggiungiMarcature(int np) {
			for(int i=0; i<np; i++) {
				int j=i;
				marcature[i]=LeggiInput.leggiInteroNonNegativo(MARCATURA + " per la posizione " + ++j + ": ");
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
				int peso = LeggiInput.leggiInteroPositivo("inserire il peso in questa relazione di flusso [" + rel.toString() + "]: ");
				RelazionePetri pn = new RelazionePetri(((RelazioneDiFlusso)rel), peso);
				this.aggiungiRelazione(pn);
			}
		}
		
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

		public ArrayList<AbstractRelazioneDiFlusso> estraiRDFdaRP() {
			ArrayList<AbstractRelazioneDiFlusso> arrayRDF = new ArrayList<AbstractRelazioneDiFlusso>();
			for(AbstractRelazioneDiFlusso relP : getRelazioni()) {
				RelazioneDiFlusso relF = new RelazioneDiFlusso(relP.getPosizione(), 
						relP.getTransizione(), relP.isInOut());
				arrayRDF.add(relF);
			}
			return arrayRDF;
		}
		
		//la rete di Petri da file deve basarsi su una rete già presente nell'archivio
		@Override
		public boolean controlloPerSalvataggioDaFile() {
			for(AbstractRete rete : arch.getArchivio()) {
				if(rete.getRelazioni().equals(estraiRDFdaRP())) return true;
			}
			System.out.println(MESS_NORETETOPOLOGIA);
			return false;
		}

	
		public void stampaMarcature() {
			System.out.println("Marcature:");
			for (int i =0; i<marcature.length; i++) {
				int j=i;
				System.out.println("Posizione " + ++j + " marcatura " + marcature[i]);
			}
		}
		
		
		@Override
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (AbstractRelazioneDiFlusso r : this.relazioni) {
				if(r instanceof RelazionePetri) {
				   System.out.println(((RelazionePetri)r).toString());
				}
			}
			this.stampaMarcature();
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
				System.out.println("la topologia e' uguale a quella di una rete gia' presente in archivio");
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
