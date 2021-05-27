package MainProgram2;

import java.io.File;

import utility.GestioneFile;
import utility.MyMenu;

public class Menu {

private static File file = new File("src\\data\\prova_xml.xml");
	
	private static final String TITOLO = "MENU PRINCIPALE \n";
	private static final String[] MENU = {"Vuoi usare le RETI", "Vuoi usare le RETI DI PETRI"};


	public static final String TITOLO_RETE = "ARCHIVIO RETI \n";
	public static final String MENU_RETE[] = {"Aggiungi rete", "Visualizza rete", 
			"Elimina rete", "Visualizza l'archivio reti"};

	private static final String TITOLO_RETEP = "ARCHIVIO RETI DI PETRI \n";
	private static final String[] MENU_RETEP = {"Aggiungi rete di Petri", "Visualizza rete di Petri", 
			"Elimina rete di Petri", "Visualizza l'archivio reti di Petri"};

	
	public ArchivioReti riempiArchivio() {
		ArchivioReti archivio = new ArchivioReti();
  		if(file.length() != 0L) {
  			archivio = (ArchivioReti) GestioneFile.xmlToObj(file);
  		}
  		return archivio;
	}
		
	
	
	
	
	public void cicloApplicazione() {
		
		MyMenu menu = new MyMenu(TITOLO,MENU);
		ArchivioReti archivio = riempiArchivio();
		
		do{
			
	  		   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   switch(cmd)
	   		   {
	   		       case 0: System.exit(0); break;
	   		       case 1: menuReti(TITOLO_RETE, MENU_RETE); break;
	   		       case 2: menuReti(TITOLO_RETEP, MENU_RETEP); break;  
	   		   }
	  		
	  		}while(true);	
  				
	}	


	public void menuReti(String titolo, String[] scelte) {
		
		MyMenu menu = new MyMenu(titolo,scelte);
		ArchivioReti archivio = riempiArchivio();
		
		do{
			
	  		   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   switch(cmd)
	   		   {
	   		       case 0: cicloApplicazione(); break;
	   		       case 1: archivio.aggiungiRete(); break;
	   		       case 2: archivio.visualizzaRete(); break;
	   		       case 3: archivio.eliminaRete(); break;
	   		       case 4: archivio.visualizzaArchivio(); break;
	   		       
	   		   }
	  		
	  		}while(true);	
	}	
	
}



