package controller;
import model.*;
import view.*;

import java.io.File;

public class Menu {
		
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
			MyMenu menu = new MyMenu(Vista.TITOLO, Vista.MENU);
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



