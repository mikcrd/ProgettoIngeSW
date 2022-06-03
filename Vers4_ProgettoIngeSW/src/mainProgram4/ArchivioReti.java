package mainProgram4;


import utility.*;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;

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
	private final static String MESS_STESSA_TOPOLOGIA = "Attenzione: nell'archivio è già presente una rete con la stessa topologia: ";
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
					    salvaLista();
			}
			else {
				LeggiInput.leggiStringa(MESS_NON_TROVATA);
			}
		}
		
		
		public void aggiungiRete(AbstractRete r) {
			r=r.creaRete();                                 		
			salvaRete(r);
		}
	
		//mi serve per il testing
		public void salvaRete(AbstractRete r) {
			
			if(r.isCorrect() && !isEqual(r)) {
				System.out.println("Sto salvando la rete ...");
				reti.add(r);
				salvaLista();
				r.stampaRete();
			}
		}
		
		public void salvaLista() {
			GestioneFile.objToXml(this);
		}
		
		public boolean noRetiInArchivio() {
			for(AbstractRete rete: getArchivio()) {
				if(rete instanceof Rete) return false;
			}
			return true;
		}
		
		public boolean noRetiPNInArchivio() {
			for(AbstractRete rete: getArchivio()) {
				if(rete instanceof RetePetri) return false;
			}
			return true;
		}
		
		public boolean noRetiPNPInArchivio() {
			for(AbstractRete rete: getArchivio()) {
				if(rete instanceof RetePetriP) return false;
			}
			return true;
		}
		
		public void visualizzaRete() {
			this.visualizzaNomeReti();
			 if(reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) { 
					String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
					AbstractRete daVisualizzare = this.trovaRete(nome);
					if(daVisualizzare == null) {
						System.out.println(MESS_NON_TROVATA);
					}
					else {
						daVisualizzare.stampaRete();}	
			 }
		}
		
		public void visualizzaRetePetri() {
			this.visualizzaNomeRetiPN();
			 if(reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) { 
					String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
					AbstractRete daVisualizzare = this.trovaRete(nome);
					if(daVisualizzare == null) {
						System.out.println(MESS_NON_TROVATA);
					}
					else {
						daVisualizzare.stampaRete();}	
			 }
		}
		
		public void visualizzaRetePetriP() {
			this.visualizzaNomeRetiPNP();
			 if(reti != null && !(reti.isEmpty()) && !noRetiPNPInArchivio()) { 
					String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
					AbstractRete daVisualizzare = this.trovaRete(nome);
					if(daVisualizzare == null) {
						System.out.println(MESS_NON_TROVATA);
					}
					else {
						daVisualizzare.stampaRete();}	
			 }
		}

		//visualizza i nomi di tutte e sole le reti presenti nell'archivio
		public void visualizzaNomeReti() {
			if(reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) {
				System.out.println("Nomi delle reti presenti: \n");
				for(AbstractRete elem : reti) {
					if(elem instanceof Rete) {
						System.out.println(elem.getName());
					}
				}
			} else { 
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		 
		//visualizza il nome di tutte e sole le PN nell'archivio
		public void visualizzaNomeRetiPN() {
			if(reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) {
				System.out.println("Nomi delle reti di Petri presenti: \n");
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePetri && !(elem instanceof RetePetriP)) {
						System.out.println(elem.getName());
					}
				}
			} else { 
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		public void visualizzaNomeRetiPNP() {
			if(reti != null && !(reti.isEmpty()) && !noRetiPNPInArchivio()) {
				System.out.println("Nomi delle reti di Petri con priorità presenti: \n");
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePetriP) {
						System.out.println(elem.getName());
					}
				}
			} else { 
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		public void visualizzaSoloRetiArchivio() {
			if(reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) {
				for(AbstractRete elem : reti) {
					if(elem instanceof Rete) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		
		public void visualizzaSoloRetiPNArchivio() {
			if(reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) {
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePetri && !(elem instanceof RetePetriP)) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		public void visualizzaSoloRetiPNPArchivio() {
			if(reti != null && !(reti.isEmpty()) && !noRetiPNPInArchivio()) {
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePetriP) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		public boolean isEqual(AbstractRete daConfrontare) {
			for(AbstractRete rete: getArchivio()) {
				if(rete.equals(daConfrontare)) {
					System.out.println(MESS_DOPPIONE);
					return true; 
					}
				else if((rete.getName().equals(daConfrontare.getName()))) {
					daConfrontare.setName(changeName());				
					return isEqual(daConfrontare);
				}
				/**
				else if((rete.getRelazioni().containsAll(daConfrontare.getRelazioni())) //per RetiPetri
						&& (rete.getName().equals(daConfrontare.getName()))) {
					daConfrontare.setName(changeName());
					return false;
				}
				else if((rete.getRelazioni().containsAll(daConfrontare.getRelazioni()))  //per RetiPetriP
						&& rete instanceof RetePetriP && daConfrontare instanceof RetePetriP
						&& ((RetePetriP) rete).getMarcature().equals(((RetePetriP) daConfrontare).getMarcature())
						&& (rete.getName().equals(daConfrontare.getName()))) {
					daConfrontare.setName(changeName());
					return false;
				}
				**/
				else if((rete.getRelazioni().containsAll(daConfrontare.getRelazioni()))
						&& rete instanceof Rete && daConfrontare instanceof Rete) {
					System.out.print(MESS_STESSA_TOPOLOGIA);
					System.out.println(rete.getName());
					return true;
				}
			}
			return false;
		}

		
		public String changeName() {
			String nuovoNome;
			boolean flag;
			do {
					nuovoNome = LeggiInput.leggiStringaNonVuota(MESS_NOME_GIA_PRESENTE);
					flag = true;
					
					for(AbstractRete retexNomi: getArchivio()) {
							if(nuovoNome.equals(retexNomi.getName())) {
							flag = false;	
							}
				    }
			}while(!flag);
			
			return nuovoNome;
		}

		
}
