package MainProgram;

import java.util.ArrayList;

import javax.sound.midi.ControllerEventListener;
import javax.xml.bind.annotation.*;

import utility.LeggiInput;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reti"
})
@XmlRootElement(name = "archivioReti")
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

	
	@XmlElementWrapper(name= "reti")
	@XmlElement(name="rete", required = true)
	ArrayList <Rete> reti;
		
		public ArchivioReti(ArrayList <Rete> arch) {
			this.reti = arch;
		}
		
		public ArchivioReti() {
			super();
		}


		public ArrayList<Rete> getArchivio() {
			if (reti == null) {
				reti = new ArrayList<Rete>();
	        }
	        return this.reti;
		}

/**
		public boolean isEqual(String daConfrontare)
		{
			for(Rete arch: getArchivio()) {
							
				if(arch.getName().equals(daConfrontare)) {	
					LeggiInput.leggiStringa(MESS_DOPPIONE);
					System.out.println("La rete è già presente");
					return true;
				}
			}
			return false;
		}
**/
		
		public boolean isEqual(Rete daConfrontare) {
			if(getArchivio().contains(daConfrontare)) {
				System.out.println(MESS_DOPPIONE); 
				return true;
			}
			
			for(Rete r: getArchivio()) {
				for(RelazioneDiFlusso relaz: r.getRelazioni()) {
					if(r.getRelazioni().containsAll(daConfrontare.getRelazioni()) 
							&& !(r.getName().equals(daConfrontare.getName()))) {
						
						return true;
					}
					else if(!(r.getRelazioni().containsAll(daConfrontare.getRelazioni())) 
							&& (r.getName().equals(daConfrontare.getName()))) {
						
						String nuovoNome = LeggiInput.leggiStringaNonVuota(MESS_NOME_GIA_PRESENTE);
						daConfrontare.setName(nuovoNome);
						return false;
					}
					
				}
			}
			return false;
		}
		
		
		public Rete cercaRete()
		{
			String net = LeggiInput.leggiStringaNonVuota(MESS_CERCA_RETE);
			return trovaRete(net);
		}

		public Rete trovaRete (String reteRichiesta)
		{ 
			for(Rete elem : reti){	  
				if(elem.getName().equalsIgnoreCase(reteRichiesta))
					return elem;
			} 
			return null;  
		 }

	public void aggiungiRete()	{
		
		Rete re = new Rete();
		re.setName(LeggiInput.leggiStringa(MESS_NOME));
		
		do {
				
				char aOb = LeggiInput.leggiChar(POSTOTRANS_TRANSPOSTO);
				
				int posto;
				int transizione;
				
				RelazioneDiFlusso rf = null;
				
				do {
						if(aOb == 'a') {
							posto = LeggiInput.leggiInteroPositivo(POSTO);
							transizione = LeggiInput.leggiInteroPositivo(TRANSIZIONE);
							rf = new RelazioneDiFlusso(posto, transizione, true);
							break;
						}
						
						else if(aOb == 'b') {
							transizione = LeggiInput.leggiInteroPositivo(TRANSIZIONE);
							posto = LeggiInput.leggiInteroPositivo(POSTO);
							rf = new RelazioneDiFlusso(posto, transizione, false);
							break;
						}
						
						else {
							aOb = LeggiInput.leggiChar(ERRORE_SCELTA_AB);
						}
					
			      } while(aOb != 'a' || aOb != 'b');
			
				if(!re.controllaRelazione(rf)) {
					
				      re.aggiungiRelazione(rf);
				}
			
		} while(LeggiInput.yesOrNo(INSERIMENTO_RELAZIONI));
		
		re.inizializzaRete();
		
		// controlliamo che la rete sia corretta e non sia uguale a una rete già esistenete 
		if(re.isCorrect() && !isEqual(re)) {
			reti.add(re);
			salvaLista();
			re.stampaRete();
		}
		
	// visualizza rete che è stata appena aggiunta: R.stampaRete();
		
	}	
	
	
		public void eliminaRete()
		{
			Rete elemento = cercaRete();
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
		
		// salva nel file xml 
		public void salvaLista() 
		{
			GestioneFile.objToXml(this);
		}
		

		public void visualizzaArchivio()
		{
			if(reti != null) {
				for(Rete elem : reti) {
					System.out.println(elem.getName());
				}
			}
			else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		public void visualizzaRete() {
			String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
			Rete daVisualizzare = this.trovaRete(nome);
			if(daVisualizzare != null){
				daVisualizzare.stampaRete();	
			} 
			else{  
				System.out.println(MESS_NON_TROVATA);
		    }
     }
		
		

		
}
