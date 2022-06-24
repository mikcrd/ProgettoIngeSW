package mainProgram3;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


import utility.LeggiInput;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetePN")
public class RetePetri extends AbstractRete  {
	
		private final static String MESS_NOME = "Inserisci il nome della rete da aggiungere: ";
		private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
		private static final String SCEGLI_RETE = "Scegli una delle reti nell'archivio: ";
		private static final String MARCATURA="inserire il valore di marcatura";
		
		int [] marcature;
		
		public RetePetri() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
		}
		

		public RetePetri(ArchivioReti arch) { //dependency injection
			this.arch = arch;	 
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
		

		public RetePetri creaRete() {

			// deve visualizzare solo reti -> vedi xml reti
			Rete r=new Rete();
			arch.visualizzaSoloRetiArchivio();
			String nomeRete = LeggiInput.leggiStringaNonVuota(SCEGLI_RETE);
			
			try {
				r=(Rete)arch.trovaRete(nomeRete);
			} catch (Exception e) {
				System.out.println(MESS_NON_TROVATA);
				return null;
			}
			
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
					int transUtente=LeggiInput.leggiInteroPositivo("inserire la transizione che si vuole fare scattare");
					if(abilitate[transUtente-1]) {
						this.scattaTransizione(transUtente);
						this.stampaMarcature();
					}else {
						System.out.println("la transizione inserita non è abilitata allo scatto");
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
		
		public boolean []  cercaTransizioniAbilitate() {
		
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
				if(relazione.getTransizione()==trans && relazione.isInOut()==true) {
					pred[relazione.getPosizione()-1] = true;
					
				}
			}
			return pred;	
		}
		
		public boolean [] trovaPostiSucessori(int trans) {
			boolean[] succ = new boolean[numPos];
			for(AbstractRelazioneDiFlusso relazione : getRelazioni()) {
				
				if(relazione.getTransizione()==trans && relazione.isInOut()==false) {
					succ[relazione.getPosizione()-1]=true;
				
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


		@Override
		public boolean isCorrect() {
			// TODO Auto-generated method stub
			return true;
		}
		
		
}

