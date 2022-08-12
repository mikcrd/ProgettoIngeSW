package controller;
import model.*;

import utility.LeggiInput;
import utility.MyMenu;

public class GestioneConfiguratore {

	private static final String TITOLO_CONF = "MENU CONFIGURATORE \n";
	private static final String[] MENU_CONF = {"Vuoi usare le RETI?", "Vuoi usare le RETI DI PETRI?", "Vuoi usare le RETI DI PETRI CON PRIORITA'?"};
	
	public static final String TITOLO_RETE = "ARCHIVIO RETI \n";
	public static final String MENU_RETE[] = {"Aggiungi rete", "Visualizza rete", 
			"Elimina rete", "Visualizza l'archivio reti", "Inserisci una rete da file"};

	private static final String TITOLO_RETEP = "ARCHIVIO RETI DI PETRI \n";
	private static final String[] MENU_RETEP = {"Aggiungi rete di Petri", "Visualizza rete di Petri", 
			"Elimina rete di Petri", "Visualizza l'archivio reti di Petri", 
			"Inserisci una rete di Petri da file"};
	
	private static final String TITOLO_RETEPP = "ARCHIVIO RETI DI PETRI CON PRIORITA'\n";
	private static final String[] MENU_RETEPP = {"Aggiungi rete di Petri con priorità", "Visualizza rete di Petri con priorità", 
			"Elimina rete di Petri con priorità", "Visualizza l'archivio reti di Petri con priorità",
			"Inserisci una rete di Petri con priorità da file"};
	
	private static final String MESS_FILE_PATH = "Immetti il path del file: ";
	private static final String MESS_ERRORE_PATH = "Indirizzo inserito non trovato";
	  
	ArchivioReti archivio;
	
	public GestioneConfiguratore(ArchivioReti archivio) {
		this.archivio = archivio;
	}

	public void cicloConfiguratore() {
		MyMenu menu = new MyMenu(TITOLO_CONF,MENU_CONF);
		boolean flag = true;
		do{
	  		   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   switch(cmd)
	   		   {
			   	    case 0: flag=false; break;
		   		    case 1: Rete rete = new Rete(archivio);
		   		    	   menuReti(TITOLO_RETE, MENU_RETE, rete); break;
		   		    case 2: RetePetri petri = new RetePetri(archivio);
		   		    	   menuReti(TITOLO_RETEP, MENU_RETEP, petri); break;    
		   		    case 3: RetePetriP priorità = new RetePetriP(archivio);
   		    	           menuReti(TITOLO_RETEPP, MENU_RETEPP, priorità); break;    	   
	   		   }	
	  		}while(flag);					
    	}
	
	public void menuReti(String titolo, String[] scelte, AbstractRete abs) {		
		MyMenu menu = new MyMenu(titolo,scelte);
		boolean flag = true;
		do{
	  		   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   abs.setArchivio(archivio);
	   		   switch(cmd)
	   		   {
			   		case 0: flag=false; break;
			   		
		   		    case 1: archivio.aggiungiRete(abs); break;
		   		    
		   		    case 2: archivio.visualizzaRete(abs); break;
		   		     
		   		    case 3: archivio.eliminaRete(abs); break;
		   		     		 
		   		    case 4: abs.visualizzaElencoParziale(); break;
		   		     
		   		    case 5: try {
		   		    	String path = LeggiInput.leggiStringaNonVuota(MESS_FILE_PATH);
		   		    	 archivio.salvaReteDaFile(path); break;
					} catch (Exception e) {
						 e.printStackTrace();
					}
	   		    	 
	   		   }	  		
	  		}while(flag);	
	}
	
}
