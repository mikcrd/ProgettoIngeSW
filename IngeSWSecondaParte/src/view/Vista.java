package view;

public class Vista {
	
	    public static final String MYMENU_CORNICE = "--------------------------------";
	    public static final String MYMENU_VOCE_USCITA = "0\tEsci";
	    public static final String MYMENU_RICHIESTA_INSERIMENTO = "\nDigita il numero dell'opzione desiderata > ";
	
		public static final String TITOLO = "MENU PRINCIPALE \n";
		public static final String[] MENU = {"Sei un CONFIGURATORE?", "Sei un FRUITORE?"};
		
		public static final String TITOLO_CONF = "MENU CONFIGURATORE \n";
		public static final String[] MENU_CONF = {"Vuoi usare le RETI?", "Vuoi usare le RETI DI PETRI?", "Vuoi usare le RETI DI PETRI CON PRIORITA'?"};
		
		public static final String TITOLO_RETE = "ARCHIVIO RETI \n";
		public static final String MENU_RETE[] = {"Aggiungi rete", "Visualizza rete", 
				"Elimina rete", "Visualizza l'archivio reti", "Inserisci una rete da file"};

		public static final String TITOLO_RETEP = "ARCHIVIO RETI DI PETRI \n";
		public static final String[] MENU_RETEP = {"Aggiungi rete di Petri", "Visualizza rete di Petri", 
				"Elimina rete di Petri", "Visualizza l'archivio reti di Petri", 
				"Inserisci una rete di Petri da file"};
		
		public static final String TITOLO_RETEPP = "ARCHIVIO RETI DI PETRI CON PRIORITA'\n";
		public static final String[] MENU_RETEPP = {"Aggiungi rete di Petri con priorit�", "Visualizza rete di Petri con priorit�", 
				"Elimina rete di Petri con priorit�", "Visualizza l'archivio reti di Petri con priorit�",
				"Inserisci una rete di Petri con priorit� da file"};
		
		public static final String MESS_FILE_PATH = "Immetti il path del file: ";
		public static final String MESS_ERRORE_PATH = "Indirizzo inserito non trovato";
		
		public static final String TITOLO_FRUI = "MENU FRUITORE \n";
	    public static final String[] MENU_FRUI = {"Simula rete di Petri", "Simula rete di Petri con priorita'"};
		public static final String NO_RETI = "Attenzione: non ci sono reti nell'archivio \nAggiungere una rete prima di continuare";
		public static final String RETEP_NON_PRES="Attenzione, la rete di petri selezionata non � presente in archivio";
		public static final String RETEPP_NON_PRES="Attenzione, la rete di petri con priorit� selezionata non � presente in archivio";

		public final static String INOUT_ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
		public final static String INOUT_ERRORE_MINIMO= "Attenzione: e' richiesto un valore maggiore o uguale a ";
		public final static String INOUT_ERRORE_STRINGA_VUOTA= "Attenzione: non hai inserito alcun carattere";
		public final static String INOUT_ERRORE_MASSIMO= "Attenzione: e' richiesto un valore minore o uguale a ";
	    public final static String INOUT_MESSAGGIO_AMMISSIBILI= "Attenzione: i caratteri ammissibili sono: ";
		public final static char INOUT_RISPOSTA_SI='S';
		public final static char INOUT_RISPOSTA_NO='N';
	
		public static final String SIMULA_MARCATURA_DOPO_SCATTO = "Dopo lo scatto della transizione la marcatura e': ";
		public static final String SIMULA_TRANSIZIONE_DA_FAR_SCATTARE = "Inserire la transizione che si vuole fare scattare: ";
		public static final String SIMULA_BLOCCO_CRITICO_RAGGIUNTO = "Nessuna transizione abilitata, blocco critico raggiunto";
		public static final String SIMULA_VUOI_PROSEGUIRE = "Vuoi proseguire con la simulazione?";
		public static final String SIMULA_TERMINE_SIMULAZIONE = "Simulazione terminata";
		public static final String SIMULAZIONE_TRANSIZIONE_ABILITATA = "transizione abilitata: ";
	
		public final static String ARCHIVIO_ISEQUAL_MESS_DOPPIONE = "Attenzione: non si puo' inserire una rete  gia' esistente!";
		public final static String ARCHIVIO_ISEQUAL_MESS_NOME_GIA_PRESENTE = "Nell'archivio � gi� presente una "
				+ "rete con questo nome. Inserire un altro nome: ";
		public final static String ARCHIVIO_ISEQUAL_MESS_STESSA_TOPOLOGIA = "Attenzione: nell'archivio � gi� presente una rete con la stessa topologia: ";
		public final static String ARCHIVIO_MESS_CERCA_RETE = "Inserisci il nome della rete: ";
		public final static String ARCHIVIO_MESS_RIMOZIONE = " : confermi la rimozione di questa rete?";
		public final static String ARCHIVIO_MESS_NON_TROVATA = "Rete richiesta non trovata";
		public static final String ARCHIVIO_NOME_RETE_VISUALIZZA = "Inserisci il nome della rete da visualizzare: ";
		public static final String ARCHIVIO_ERRORE_ARCHIVIO_VUOTO = "Attenzione archivio vuoto";
		public static final String ARCHIVIO_DEBUGGING_SALVA_RETE = "Sto salvando la rete ...";
		public static final String RETE = "Rete";
		public static final String RETEP = "RetePetri";
		public static final String RETEPP = "RetePetriP";
		public static final String ARCHIVIO_NOMI_RETIPP_PRESENTI = "Nomi delle reti di Petri con priorit� presenti: \n";
		public static final String ARCHIVIO_NOMI_RETIP_PRESENTI = "Nomi delle reti di Petri presenti: \n";
		public static final String ARCHIVIO_NOMI_RETI_PRESENTI = "Nomi delle reti presenti: \n";
		


}
