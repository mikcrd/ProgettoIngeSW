package controller;
import model.*;
import utility.MyMenu;
import view.*;

public class GestioneFruitore {
	
	private static final String TITOLO_FRUI = "MENU FRUITORE \n";
	private static final String[] MENU_FRUI = {"Simula rete di Petri", "Simula rete di Petri con priorita'"};
	public static final String NO_RETI = "Attenzione: non ci sono reti nell'archivio \nAggiungere una rete prima di continuare";
	public static final String RETEP_NON_PRES="Attenzione, la rete di petri selezionata non è presente in archivio";
	public static final String RETEPP_NON_PRES="Attenzione, la rete di petri con priorità selezionata non è presente in archivio";

	ArchivioReti archivio;
	
	public GestioneFruitore(ArchivioReti archivio) {
		this.archivio = archivio;
	}


	public void cicloFruitore() {
		MyMenu menu = new MyMenu(TITOLO_FRUI, MENU_FRUI);
		boolean flag = true;
		do{
			   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   switch(cmd)
	   		   {
			   		case 0: flag=false; break;
		   		    case 1: Stampa.visualizzaNomeRetiPN(archivio);
		   		    if(Stampa.noRetiPNInArchivio(archivio)) break;
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
		   		    case 2: Stampa.visualizzaNomeRetiPNP(archivio);;
		   		    if(Stampa.noRetiPNPInArchivio(archivio)) break;
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
		}while(flag);
	}
}
