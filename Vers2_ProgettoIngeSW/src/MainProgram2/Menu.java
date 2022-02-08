package MainProgram2;

import java.io.File;

import utility.MyMenu;

public class Menu {

		private static final String TITOLO = "MENU PRINCIPALE \n";
		private static final String[] MENU = {"Vuoi usare le RETI", "Vuoi usare le RETI DI PETRI"};


		public static final String TITOLO_RETE = "ARCHIVIO RETI \n";
		public static final String MENU_RETE[] = {"Aggiungi rete", "Visualizza rete", 
				"Elimina rete", "Visualizza l'archivio reti"};

		private static final String TITOLO_RETEP = "ARCHIVIO RETI DI PETRI \n";
		private static final String[] MENU_RETEP = {"Aggiungi rete di Petri", "Visualizza rete di Petri", 
				"Elimina rete di Petri", "Visualizza l'archivio reti di Petri"};
		
		ArchivioReti archivio;
		String differenziaRete;

		public Menu(ArchivioReti arch) { // dependency injection
				this.archivio = arch;
		}

		
		public void cicloApplicazione() {
			MyMenu menu = new MyMenu(TITOLO,MENU);
			do{
					
		  		   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
				   	    case 0: System.exit(0); break;
			   		    case 1: differenziaRete = "rete";
			   		    	   menuReti(TITOLO_RETE, MENU_RETE, differenziaRete); break;
			   		    case 2: differenziaRete = "retePn";
			   		    	   menuReti(TITOLO_RETEP, MENU_RETEP, differenziaRete); break;    
		   		   }	
		  		}while(true);					
	    }


		public void menuReti(String titolo, String[] scelte, String differenzia) {		
			MyMenu menu = new MyMenu(titolo,scelte);
			do{
		  		   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
				   		case 0: cicloApplicazione(); break;
			   		    case 1: if(differenziaRete == "rete") {
			   		    	   			AbstractRete r = new Rete();
			   		    	   			archivio.aggiungiRete(r); break;
			   		       		}
			   		       		else if(differenziaRete == "retePn") {
				   		       		    AbstractRete r = new RetePN();
			   		    	   		    archivio.aggiungiRete(r); break;
			   		       		}
			   		     case 2: archivio.visualizzaRete(); break;
			   		     case 3: archivio.eliminaRete(); break;
			   		     case 4: if(differenziaRete == "rete") {
					   		    	    archivio.visualizzaNomeReti(); break;
					   		     } else if(differenziaRete == "retePn") { 
					   		    	    archivio.visualizzaNomeRetiPN(); break;
					   		     }  
		   		   }	  		
		  		}while(true);	
		}	
		
		
	}



