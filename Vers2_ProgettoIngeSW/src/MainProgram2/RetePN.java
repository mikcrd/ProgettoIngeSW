package MainProgram2;

import java.util.ArrayList;

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
public class RetePN extends AbstractRete  {
	
		private final static String MESS_NOME = "Inserisci il nome della rete da aggiungere: ";
		private static final String ERRORE_SCELTA_AB = "Inserisci solo i caratteri 'a' o 'b' : ";
		private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
		private static final String SCEGLI_CREA = "Premi a per scegliere una rete da usare per costruire la rete di Petri \n"
				+ "Premi b per inserire una nuova rete su cui costruire la rete di Petri";
		private static final String SCEGLI_RETE = "Scegli una delle reti nell'archivio: ";
		private static final String VUOI_QUESTA_RETE = "Vuoi scegliere questa rete? ";
		private static final String PESO = "Immetti un peso per la relazione corrente: ";
		private static final String ERR_PESO="il peso deve essere un valore maggiore o uguale a 1";
		private static final String MARCATURA="inserire il valore di marcatura";
		
		int [] marcature;
		
		public RetePN() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
		}

/**		
		public RetePN(String name, ArrayList<RelazionePN> relazioni) {
			this.name = name;
			this.relazioni = relazioni;
		}
**/

		public RetePN(ArchivioReti arch) { //dependency injection
			this.arch = arch;	 
		}

/**
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	
		public ArrayList<IRelazioneDiFlusso> getRelazioni() {
			if (relazioni == null) {
	        	relazioni = new ArrayList<IRelazioneDiFlusso>();
	        }
	        return this.relazioni;
		}
 **/
		public void aggiungiRelazione(RelazionePN r) {
			this.getRelazioni().add(r);		
		}


		/**
		 * Se almeno un peso o una marcatura non sono validi, ritorna falso
		 * marcature: da 0 a +infinito
		 * pesi: da 1 a +infinito
		 */
		@Override
		/*public boolean isCorrect() {
           for(IRelazioneDiFlusso rel : getRelazioni()) {
        	   if(rel instanceof RelazionePN) {
	        	   if(((RelazionePN)rel).getMarcatura() < 0 || ((RelazionePN)rel).getPeso() <= 0)
	        		   return false; break;
        	   }
           }
   
		   return true;  
		}*/
//dasa
	    public RetePN creaRete() {
//			RetePN pn = new RetePN();
//			return arch.creaRetePN(pn); // risolto con dependency inj
	    	char aOb;
	    	do {
	    		aOb = LeggiInput.leggiChar(SCEGLI_CREA);
	    		if (aOb=='a') {
	    			System.out.println("lista reti");
	    			arch.visualizzaSoloRetiArchivio(); // prima gli faccio vedere l'archivio, poi gli faccio scegliere ...
	    			
	    			casoA_ScegliReteCostruisciPN();
	    			
	    		}else if(aOb=='b'){
	    			Rete r1 = new Rete();
	    			arch.aggiungiRete(r1);
	    			casoA_ScegliReteCostruisciPN();
	    			
	    		}else {
	    			System.out.println("immettere a o b per selezionare un'opzione");
	    		}
	    	}while (aOb!='a' || aOb!='b');
	    	
			this.setName(LeggiInput.leggiStringa(MESS_NOME));
			
			// deve visualizzare solo reti -> vedi xml reti
			
		
			return this;
		}
			
			
		public void casoA_ScegliReteCostruisciPN() {
			Rete r;
	//		do{
					String nomeRete = LeggiInput.leggiStringa(SCEGLI_RETE);
					r = (Rete) arch.trovaRete(nomeRete); // se sbaglia a scrivere ...
					if(r == null) {
						LeggiInput.leggiStringa(MESS_NON_TROVATA);
					}
					else{
						this.name=LeggiInput.leggiStringa(MESS_NOME);
						r.stampaRete();
						System.out.println("----------------------------------------------");
					}
					
					this.assegnaMarcetureEPesi(r);
					
						
		//      } while(!LeggiInput.yesOrNo(VUOI_QUESTA_RETE));
		}
		
		
		public void assegnaMarcetureEPesi(Rete t) {
			marcature = new int [t.getPos()];
			
			for (int i=0; i<t.getPos(); i++) {
				marcature[i]= -1;
				for(AbstractRelazioneDiFlusso rf: t.getRelazioni()) {
					if(rf instanceof RelazioneDiFlusso) {
						System.out.println(((RelazioneDiFlusso)rf).toString());
						int peso;
						do {
							peso = LeggiInput.leggiIntero(PESO);
							if (peso>0) {
								RelazionePN relPN= new RelazionePN((RelazioneDiFlusso)rf, peso);
								relazioni.add(relPN);
							}else
								System.out.println(ERR_PESO);
						}while(peso<1);
					}
					if (rf.getPosizione()==i && marcature[i]==-1){
						System.out.println("posizione");
						LeggiInput.leggiIntero(MARCATURA);
					
					}
				}
			}
			
			
		}
		
		/*public void assegnaMarcaturaEPesi(Rete t) {
			marcature= new int [t.getPos()];
			for(IRelazioneDiFlusso rf: t.getRelazioni())	{
				if(rf instanceof RelazioneDiFlusso) {
					System.out.println(((RelazioneDiFlusso)rf).toString());
					//int marcatura = LeggiInput.leggiIntero(MARCATURA);
					int peso = LeggiInput.leggiIntero(PESO);
					//RelazionePN relPN = new RelazionePN((RelazioneDiFlusso)rf, marcatura, peso);
					//this.aggiungiRelazione(relPN);
				}
			}
			for (int i=0; i<t.getPos(); i++) {
				
			}
		}*/
	    
		
		
		@Override
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (AbstractRelazioneDiFlusso r : this.relazioni) {
				if(r instanceof RelazionePN) {
				   System.out.println(((RelazionePN)r).toString());
				}
			}			
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 0;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((relazioni == null) ? 0 : relazioni.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (getClass() != obj.getClass())
				return false;
			
			RetePN other = (RetePN) obj;
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
			return false;
		}
		
	
		
		
}
//NOTA: i metodi hashcode e isEqual di Rete e RetePN sono uguali, considerare di fare una classe astratta?

