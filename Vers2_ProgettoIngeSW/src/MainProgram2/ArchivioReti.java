package MainProgram2;


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
			
		
		public void visualizzaRete() {
			 if(reti != null && !(reti.isEmpty())) { 
					String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
					AbstractRete daVisualizzare = this.trovaRete(nome);
					if(daVisualizzare == null) {
						System.out.println("La rete richiesta non è presente in archivio");
					}
					else {
						daVisualizzare.stampaRete();}	
			 }else { 
					System.out.println(ERRORE_ARCHIVIO_VUOTO);
				}
		}

		
		public void visualizzaNomeReti() {
			System.out.println("Nomi delle reti presenti: \n");
			if(reti != null && !(reti.isEmpty())) {
				for(AbstractRete elem : reti) {
					if(elem instanceof Rete) {
						System.out.println(elem.getName());
					}
				}
			} else { 
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		 
		
		public void visualizzaNomeRetiPN() {
			if(reti != null) {
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePN) {
						System.out.println("Nomi delle reti di Petri presenti: \n");
						System.out.println(elem.getName());
					}
				}
			}
		}
		
		
		public void visualizzaSoloRetiArchivio() {
			if(reti != null) {
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
			if(reti != null) {
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePN) {
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
					return true; }
				else if(!(rete.getRelazioni().containsAll(daConfrontare.getRelazioni())) 
						&& (rete.getName().equals(daConfrontare.getName()))) {
					String nuovoNome;
					boolean flag;
					do {
							nuovoNome = LeggiInput.leggiStringaNonVuota(MESS_NOME_GIA_PRESENTE);
							flag = true;
							
							for(AbstractRete retexNomi: getArchivio()) {
									if(nuovoNome.equals(retexNomi.getName())) {
								//	System.out.println(MESS_NOME_GIA_PRESENTE);
									flag = false;	
									}
						    }
					}while(!flag);

					daConfrontare.setName(nuovoNome);
					return false;
				}
				else if((rete.getRelazioni().containsAll(daConfrontare.getRelazioni()))) {
					return true;
				}
			}
			return false;
		}

		
}
