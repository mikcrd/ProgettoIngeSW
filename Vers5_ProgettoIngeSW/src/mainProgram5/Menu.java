package mainProgram5;

import java.io.File;


import utility.MyMenu;

public class Menu {
		private static final String TITOLO = "MENU PRINCIPALE \n";
		private static final String[] MENU = {"Sei un CONFIGURATORE?", "Sei un FRUITORE?"};
	
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
		
		private static final String TITOLO_FRUI = "MENU FRUITORE \n";
		private static final String[] MENU_FRUI = {"Simula rete di Petri", "Simula rete di Petri con priorita'"};
		
		public static final String NO_RETI = "Attenzione: non ci sono reti nell'archivio \nAggiungere una rete prima di continuare";
		private static final String NO_RETI_PETRI = "Attenzione: non ci sono reti di Petri nell'archivio \nAggiungere una rete prima di continuare";
		public static final String RETEP_NON_PRES="Attenzione, la rete di petri selezionata non è presente in archivio";
		public static final String RETEPP_NON_PRES="Attenzione, la rete di petri con priorità selezionata non è presente in archivio";


		public static final File file = new File("C:\\data\\reti5_xml.xml");


		ArchivioReti archivio;
		String differenziaRete;

		
		public void cicloApplicazione() {
			MyMenu menu = new MyMenu(TITOLO, MENU);
			do{
					this.SetArchivio();
					menu.stampaMenu();
					int cmd = menu.scegli();
					switch(cmd){
						case 0:System.exit(0); break;
						case 1:cicloConfiguratore(); break;
						case 2:cicloFruitore(); break;
					}
			}while(true);
			
		}
		
		public void cicloConfiguratore() {
			MyMenu menu = new MyMenu(TITOLO_CONF,MENU_CONF);
			this.SetArchivio();
			do{
					
		  		   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
				   	    case 0: cicloApplicazione(); break;
			   		    case 1: differenziaRete = "rete";
			   		    	   menuReti(TITOLO_RETE, MENU_RETE, differenziaRete); break;
			   		    case 2: differenziaRete = "retePn";
			   		    	   menuReti(TITOLO_RETEP, MENU_RETEP, differenziaRete); break;    
			   		    case 3: differenziaRete = "retePnP";
	   		    	           menuReti(TITOLO_RETEPP, MENU_RETEPP, differenziaRete); break;    	   
		   		   }	
		  		}while(true);					
	    	}

			public void cicloFruitore() {
			MyMenu menu = new MyMenu(TITOLO_FRUI, MENU_FRUI);
			this.SetArchivio();
			do{
				   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
				   		case 0: cicloApplicazione(); break;
			   		    case 1: archivio.visualizzaNomeRetiPN();
			   		    if(archivio.noRetiPNInArchivio()) break;
			   		    else {
			   		    	RetePetri petri = new RetePetri();
			   		    	petri= (RetePetri) archivio.cercaRete();
			   		    	if(petri==null) {
				    				System.out.println(RETEP_NON_PRES);
				    			}else {
				    				petri.simulaRete();
				    			}
			   		    }
		
			   		    		break;
			   		    case 2: archivio.visualizzaNomeRetiPNP();
			   		    if(archivio.noRetiPNPInArchivio()) break;
			   		    else {
			   		    		RetePetriP petrip=new RetePetriP();
			   		    		petrip=(RetePetriP)archivio.cercaRete();
			   		    		if(petrip==null) {
				    				System.out.println(RETEPP_NON_PRES);
				    			}else {
				    				petrip.simulaRete();
				    			}
			   		    }
			   		    		break;
		
		   		   }

			}while(true);
		
		}
		
		public void menuReti(String titolo, String[] scelte, String differenzia) {		
			MyMenu menu = new MyMenu(titolo,scelte);
			
	  		this.SetArchivio();
			do{
		  		   menu.stampaMenu();
		   		   int cmd = menu.scegli();
		   		   switch(cmd)
		   		   {
				   		case 0: cicloConfiguratore(); break;
			   		    case 1: if(differenziaRete == "rete") {
			   		    	   			AbstractRete r = new Rete();
			   		    	   			archivio.aggiungiRete(r); break;
			   		       		}
			   		       		else if(differenziaRete == "retePn") {
				   		       		    AbstractRete r = new RetePetri(archivio);
				   		       		    if(archivio.noRetiInArchivio()) {
				   		       		    	System.out.println(NO_RETI);
				   		       		    	break;
				   		       		    }
				   		       		    else {archivio.aggiungiRete(r); break;}
			   		       		}
			   		       		else if(differenziaRete == "retePnP") {
				   		       			AbstractRete r = new RetePetriP(archivio);
				   		       			if(archivio.noRetiPNInArchivio()) {
					   		       			System.out.println(NO_RETI_PETRI);
				   		       		    	break;
				   		       			}
				   		       			else {archivio.aggiungiRete(r); break;}	
			   		       		}
			   		    
			   		     case 2:if(differenziaRete == "rete") {
			   		    	 		archivio.visualizzaRete(); break;
			   		     		} else if(differenziaRete == "retePn") {
			   		     				archivio.visualizzaRetePetri();break;
			   		     		} else if(differenziaRete == "retePnP") {
		   		     				archivio.visualizzaRetePetriP();break;
			   		     		}
			   		     
			   		     case 3: if(differenziaRete == "rete") {
			   		    	 			archivio.visualizzaNomeReti();
			   		    	 			if(archivio.noRetiInArchivio()) break;
			   		     		 } else if(differenziaRete == "retePn") {
			   		     			 	archivio.visualizzaNomeRetiPN();
			   		     			 	if(archivio.noRetiPNInArchivio()) break;
			   		     		 } else if(differenziaRete == "retePnP") {
			   		     			 	archivio.visualizzaNomeRetiPNP();
			   		     			 	if(archivio.noRetiPNPInArchivio()) break;
			   		     		 }
			   		     		 archivio.eliminaRete(); break;
			   		     		 
			   		     case 4: if(differenziaRete == "rete") {
					   		    	    archivio.visualizzaNomeReti(); break;
					   		     } else if(differenziaRete == "retePn") { 
					   		    	    archivio.visualizzaNomeRetiPN(); break;
					   		     } else if(differenziaRete == "retePnP") { 
					   		    	    archivio.visualizzaNomeRetiPNP(); break;
					   		     }
			   		     
			   		     case 5: archivio.salvaReteDaFile(); break;
		   		   }	  		
		  		}while(true);	
		}
		
		public void SetArchivio() {
			if(file.length() != 0L) {
	  			archivio = GestioneFile.xmlToObj(file);
	  		}
			else archivio = new ArchivioReti();
		}	
	}



