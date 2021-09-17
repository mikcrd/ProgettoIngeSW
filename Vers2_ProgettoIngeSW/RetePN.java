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
		private static final String MARCATURA = "Immetti una marcatura per la relazione corrente: ";
		private static final String PESO = "Immetti un peso per la relazione corrente: ";
	
		
		public RetePN() {
			name=null;
			relazioni = new ArrayList<IRelazioneDiFlusso>();
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
		public boolean isCorrect() {
           for(IRelazioneDiFlusso rel : getRelazioni()) {
        	   if(rel instanceof RelazionePN) {
	        	   if(((RelazionePN)rel).getMarcatura() < 0 || ((RelazionePN)rel).getPeso() <= 0)
	        		   return false; break;
        	   }
           }
   
		   return true;  
		}

	    public RetePN creaRete() {
//			RetePN pn = new RetePN();
//			return arch.creaRetePN(pn); // risolto con dependency inj
	    	char aOb;
	    	do {
	    		aOb = LeggiInput.leggiChar(SCEGLI_CREA);
	    		if (aOb=='a') {
	    			System.out.println("lista reti");
	    			arch.visualizzaArchivio(); // prima gli faccio vedere l'archivio, poi gli faccio scegliere ...
	    			
	    			casoA_ScegliReteCostruisciPN();
	    			
	    		}else if(aOb=='b'){
	    			Rete r1 = new Rete();
	    			arch.aggiungiRete(r1);
	    			
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
					
					
					
						
		//      } while(!LeggiInput.yesOrNo(VUOI_QUESTA_RETE));
		}
		
		public void assegnaMarcatura(Rete t) {
			for(IRelazioneDiFlusso rf: t.getRelazioni())	{
				if(rf instanceof RelazioneDiFlusso) {
					System.out.println(((RelazioneDiFlusso)rf).toString());
					//int marcatura = LeggiInput.leggiIntero(MARCATURA);
					int peso = LeggiInput.leggiIntero(PESO);
					//RelazionePN relPN = new RelazionePN((RelazioneDiFlusso)rf, marcatura, peso);
					//this.aggiungiRelazione(relPN);
				}
			}	
		}
	    
		
		
		@Override
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (IRelazioneDiFlusso r : this.relazioni) {
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
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (relazioni == null) {
				if (other.relazioni != null)
					return false;
			} else if (!relazioni.equals(other.relazioni))
				return false;
			return true;
		}
	
		
		
}
//NOTA: i metodi hashcode e isEqual di Rete e RetePN sono uguali, considerare di fare una classe astratta?

