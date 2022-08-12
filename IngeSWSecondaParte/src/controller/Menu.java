package controller;
import model.*;

import java.io.File;

import utility.LeggiInput;
import utility.MyMenu;

public class Menu {
		private static final String TITOLO = "MENU PRINCIPALE \n";
		private static final String[] MENU = {"Sei un CONFIGURATORE?", "Sei un FRUITORE?"};
	
	    private static final File file = new File("C:\\data\\reti5_xml.xml");

		ArchivioReti archivio;
		String differenziaRete;

		public void SetArchivio() {
			if(file.length() != 0L) {
	  			archivio = GestioneFile.xmlToObj(file);
	  		}
			else archivio = new ArchivioReti();
		}	
		
		public void cicloApplicazione() {
			MyMenu menu = new MyMenu(TITOLO, MENU);
			do{
					this.SetArchivio();
					menu.stampaMenu();
					int cmd = menu.scegli();
					switch(cmd){
					case 0:System.exit(0); break;
					case 1:GestioneConfiguratore gestconf = new GestioneConfiguratore(archivio);
					       gestconf.cicloConfiguratore(); break;
					case 2:GestioneFruitore gestfrui = new GestioneFruitore(archivio);
				           gestfrui.cicloFruitore(); break;
				}
			}while(true);
			
		}	
		
	}



