package MainProgram2;


import utility.*;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reti"
})
@XmlRootElement(name = "ArchivioReti")
public class ArchivioReti {
	
	private final static String MESS_NOME = "Inserisci il nome della rete da aggiungere: ";
	private final static String MESS_DOPPIONE = "Attenzione: non si puo' inserire una rete  gia' esistente!";
	private final static String MESS_NOME_GIA_PRESENTE = "Nell'archivio è già presente una "
			+ "rete con questo nome. Inserire un altro nome: ";
	private final static String MESS_CERCA_RETE = "Inserisci il nome della rete: ";
	private final static String MESS_RIMOZIONE = " : confermi la rimozione di questa rete?";
	private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
	
	private static final String INSERIMENTO_RELAZIONI = "Vuoi inserire un'altra relazione?";
	private static final String POSTOTRANS_TRANSPOSTO = "Per aggiungere una coppia posto-transizione premere 'a'\n"
			+ "Per aggiungere una coppia transizione-posto premere 'b' : ";
	private static final String POSTO = "Inserisci un intero positivo per il posto: ";
	private static final String TRANSIZIONE = "Inserisci un intero positivo per la transizione: ";
	private static final String ERRORE_SCELTA_AB = "Inserisci solo i caratteri 'a' o 'b' : ";
	private static final String NOME_RETE_VISUALIZZA = "Inserisci il nome della rete da visualizzare: ";
	private static final String ERRORE_ARCHIVIO_VUOTO = "Attenzione archivio vuoto";

	private static final String SCEGLI_CREA = "Scegli una rete esistente per costruirci sopra una rete di Petri (premi 'a')/n"
			+ "Oppure crea prima una rete (premi 'b'): ";
	private static final String SCEGLI_RETE = "Scegli una delle reti nell'archivio: ";
	private static final String VUOI_QUESTA_RETE = "Vuoi scegliere questa rete? ";
	private static final String MARCATURA = "Immetti una marcatura per la relazione corrente: ";
	private static final String PESO = "Immetti un peso per la relazione corrente: ";
	
	
	
		@XmlElementWrapper(name= "reti")
		@XmlElement(name="rete", required = true)
		ArrayList <AbstractRete> reti;
		
		public ArchivioReti(ArrayList <AbstractRete> arch) {
			this.reti = arch;
		}
		
		public ArchivioReti() {
			
		}


		public ArrayList<AbstractRete> getArchivio() {
			if (reti == null) {
				reti = new ArrayList<AbstractRete>();
	        }
	        return this.reti;
		}
		
		public AbstractRete trovaRete (String reteRichiesta)
		{ 
			for(AbstractRete elem : reti){	  
				if(elem.getName().equalsIgnoreCase(reteRichiesta))
					return elem;
			} 
			return null;  
		}	
			
		public AbstractRete cercaRete()
		{
			String net = LeggiInput.leggiStringaNonVuota(MESS_CERCA_RETE);
			return trovaRete(net);
		}
		
		public void eliminaRete()
		{
			AbstractRete elemento = cercaRete();
			if (elemento!= null)
			{
				boolean procedi = LeggiInput.yesOrNo(elemento.getName() + MESS_RIMOZIONE);
					if (procedi)
						reti.remove(elemento);
			}
			else {
				LeggiInput.leggiStringa(MESS_NON_TROVATA);
			}
		}
		
		
		public void aggiungiRete(AbstractRete r) {
					
				r=r.creaRete();
					
				if(r.isCorrect() && !isEqual(r)) {
	 				reti.add(r);
					salvaLista();
					r.stampaRete();
				}
		}
	
		
		public void salvaLista() 
		{
			GestioneFile.objToXml(this);
		}
			
		
		public void visualizzaRete() {
			String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
			AbstractRete daVisualizzare = this.trovaRete(nome);
			daVisualizzare.stampaRete();		
		}
			
			
		public void visualizzaArchivio()
		{
			if(reti != null) {
				for(AbstractRete elem : reti) {
					System.out.println(elem.getName());
				}
			}
			else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
				
			
		public boolean isEqual(AbstractRete daConfrontare) {
			if(getArchivio().contains(daConfrontare)) {
				System.out.println(MESS_DOPPIONE); 
				return true;
			}
			
			for(AbstractRete reti: getArchivio()) {
				for(IRelazioneDiFlusso relaz: reti.getRelazioni()) {
					if(reti.getRelazioni().containsAll(daConfrontare.getRelazioni()) 
							&& !(reti.getName().equals(daConfrontare.getName()))) {
						System.out.println("la topologia e' uguale a quella di una rete gia' presente in archivio");
						return true;
					}
					else if(!(reti.getRelazioni().containsAll(daConfrontare.getRelazioni())) 
							&& (reti.getName().equals(daConfrontare.getName()))) {
						
						String nuovoNome = LeggiInput.leggiStringaNonVuota(MESS_NOME_GIA_PRESENTE);
						daConfrontare.setName(nuovoNome);
						return false;
					}
					
				}
			}
			return false;
		}
		
		
		
/**			
		public boolean isEqual(AbstractRete daConfrontare) {
			for(AbstractRete rete: getArchivio()) {
				if(rete.equals(daConfrontare)) 
					return true;
			}
			return false;
		}
		
	**/
		
}
