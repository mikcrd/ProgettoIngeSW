package MainProgram;

import utility.MyMenu;

public class Menu {

	public static final String TITOLO = "ARCHIVIO RETI \n";
	public static final String MENU[] = {"Aggiungi rete", "Visualizza rete", 
			"Elimina rete", "Visualizza l'archivio reti"};
	
	public void cicloApplicazione() {
		
		MyMenu menu = new MyMenu(TITOLO,MENU);
  		menu.stampaMenu();
  		
  		ArchivioReti archivio = new ArchivioReti();
		
  		do{
			
   		   int cmd = menu.scegli();
   		   switch(cmd)
   		   {
   		       case 0: System.exit(0); break;
   		       case 1: archivio.aggiungiRete(); break;
   		       case 2: archivio.visualizzaRete(); break;
   		       case 3: archivio.eliminaRete(); break;
   		       case 4: archivio.visualizzaArchivio(); break;
   		       
   		   }
  		
  		}while(true);
	
	}
}
