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

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "relazioni"
})
public class RetePN extends AbstractRete implements IRelazioneDiFlusso {
	
		private final static String MESS_NOME = "Inserisci il nome della rete da aggiungere: ";
		private static final String ERRORE_SCELTA_AB = "Inserisci solo i caratteri 'a' o 'b' : ";
		private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
		private static final String SCEGLI_CREA = "Scegli una rete esistente per costruirci sopra una rete di Petri (premi 'a')/n"
				+ "Oppure crea prima una rete (premi 'b'): ";
		private static final String SCEGLI_RETE = "Scegli una delle reti nell'archivio: ";
		private static final String VUOI_QUESTA_RETE = "Vuoi scegliere questa rete? ";
		private static final String MARCATURA = "Immetti una marcatura per la relazione corrente: ";
		private static final String PESO = "Immetti un peso per la relazione corrente: ";
	
	    @XmlAttribute(name = "name", required = true)
		String name;
		
	    @XmlElementWrapper(name= "relazioni")
		@XmlElement(name = "", required = true, type=RelazioneDiFlusso.class)
		ArrayList<RelazionePN> relazioni;
	
		
		public RetePN() {
			name=null;
			relazioni = new ArrayList<RelazionePN>();
		}

		public RetePN(String name, ArrayList<IRelazioneDiFlusso> relazioni) {
			super(name, relazioni);
		}


		public RetePN(ArchivioReti arch) { //dependency injection
			this.arch = arch;	 
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	
		public ArrayList<RelazionePN> getRelazioni() {
			if (relazioni == null) {
	        	relazioni = new ArrayList<RelazionePN>();
	        }
	        return this.relazioni;
		}

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
           for(RelazionePN rel : getRelazioni()) {
        	   if(rel.getMarcatura() < 0 || rel.getPeso() <= 0)
        		   return false; break;
           }
   
		   return true;  
		}

	    public RetePN creaRete() {
			RetePN pn = new RetePN();
			return arch.creaRetePN(pn); // risolto con dependency inj
	    }
		
		@Override
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (RelazionePN r : relazioni) {
				System.out.println(r.toString());
			}			
		}
	
		
}
//NOTA: i metodi hashcode e isEqual di Rete e RetePN sono uguali, considerare di fare una classe astratta?
