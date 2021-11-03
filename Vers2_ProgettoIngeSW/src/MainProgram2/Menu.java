package MainProgram2;

import java.io.File;

import utility.MyMenu;

public class Menu {

	 //   private static File file = new File("src\\data\\reti_xml.xml");
	    
		private static final String TITOLO = "MENU PRINCIPALE \n";
		private static final String[] MENU = {"Vuoi usare le RETI", "Vuoi usare le RETI DI PETRI"};


		public static final String TITOLO_RETE = "ARCHIVIO RETI \n";
		public static final String MENU_RETE[] = {"Aggiungi rete", "Visualizza rete", 
				"Elimina rete", "Visualizza l'archivio reti"};

		private static final String TITOLO_RETEP = "ARCHIVIO RETI DI PETRI \n";
		private static final String[] MENU_RETEP = {"Aggiungi rete di Petri", "Visualizza rete di Petri", 
				"Elimina rete di Petri", "Visualizza l'archivio reti di Petri"};
		
		ArchivioReti archivio;

		public Menu(ArchivioReti arch) { // dependency injection
				this.archivio = arch;
			}


	/**	
		public ArchivioReti riempiArchivio(File f) {
			ArchivioReti archivio = new ArchivioReti();
	  		if(f.length() != 0L) {
	  			archivio = (ArchivioReti) GestioneFile.xmlToObj(f);
	  		}
	  		return archivio;
		}
	**/	
		
		public void cicloApplicazione() {
				
			MyMenu menu = new MyMenu(TITOLO,MENU);
		
			do{
					
		  		   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
		   		       case 0: System.exit(0); break;
		   		       case 1: AbstractRete r = new Rete();
		   		    	   menuReti(TITOLO_RETE, MENU_RETE, r); break;
		   		       case 2: AbstractRete pn = new RetePN(archivio);
		   		    	   menuReti(TITOLO_RETEP, MENU_RETEP, pn); break;  
		   		   }
			  		
		  		}while(true);	
		  				
		}	


		public void menuReti(String titolo, String[] scelte, AbstractRete r) {
			
			MyMenu menu = new MyMenu(titolo,scelte);
//			ArchivioReti archivio = riempiArchivio(file);
			
			do{
		  		   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
		   		       case 0: cicloApplicazione(); break;
		   		       case 1: archivio.aggiungiRete(r); break;
		   		       case 2: archivio.visualizzaRete(); break;
		   		       case 3: archivio.eliminaRete(); break;
		   		    case 4: if(r instanceof Rete) {
		   		    	 archivio.visualizzaNomeReti(); break;
		   		       } else archivio.visualizzaNomeRetiPN(); break;
		   		       
		   		   }
		  		
		  		}while(true);	
		}	
		
		
	}



