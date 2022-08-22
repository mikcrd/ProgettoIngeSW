package controller;
import model.*;
import view.*;

public class GestioneConfiguratore {
  
	ArchivioReti archivio;
	
	public GestioneConfiguratore(ArchivioReti archivio) {
		this.archivio = archivio;
	}

	public void cicloConfiguratore() {
		MyMenu menu = new MyMenu(Vista.TITOLO_CONF, Vista.MENU_CONF);
		boolean flag = true;
		do{
	  		   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   switch(cmd)
	   		   {
			   	    case 0: flag=false; break;
		   		    case 1: Rete rete = new Rete(archivio);
		   		    	   menuReti(Vista.TITOLO_RETE, Vista.MENU_RETE, rete); break;
		   		    case 2: RetePetri petri = new RetePetri(archivio);
		   		    	   menuReti(Vista.TITOLO_RETEP, Vista.MENU_RETEP, petri); break;    
		   		    case 3: RetePetriP priorità = new RetePetriP(archivio);
   		    	           menuReti(Vista.TITOLO_RETEPP, Vista.MENU_RETEPP, priorità); break;    	   
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
		   		    	String path = InputOutput.leggiStringaNonVuota(Vista.MESS_FILE_PATH);
		   		    	 archivio.salvaReteDaFile(path); break;
					} catch (Exception e) {
						 e.printStackTrace();
					}
	   		    	 
	   		   }	  		
	  		}while(flag);	
	}
	
}
