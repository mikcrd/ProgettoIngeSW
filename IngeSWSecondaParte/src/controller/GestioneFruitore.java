package controller;
import model.*;
import view.*;

public class GestioneFruitore {
	
    
	ArchivioReti archivio;
	
	public GestioneFruitore(ArchivioReti archivio) {
		this.archivio = archivio;
	}


	public void cicloFruitore() {
		MyMenu menu = new MyMenu(Vista.TITOLO_FRUI, Vista.MENU_FRUI);
		boolean flag = true;
		do{
			   menu.stampaMenu();
	   		   int cmd = menu.scegli();
	   		   switch(cmd)
	   		   {
			   		case 0: flag=false; break;
		   		    case 1: archivio.visualizzaNomeRetiPN();
		   		    if(archivio.noRetiPNInArchivio()) break;
		   		    else {
		   		    	RetePetri petri = new RetePetri();
		   		    	petri= (RetePetri) archivio.cercaRete();
		   		    	if(petri==null) {
		   		    		InputOutput.mostraMessaggio(Vista.RETEP_NON_PRES);
			    			}else {
			    				AbstractSimulazione simPetri = new SimulazioneRetiPetri(petri);
			    				simPetri.simulaRete();
			    			}
		   		    }
	
		   		    		break;
		   		    case 2: archivio.visualizzaNomeRetiPNP();;
		   		    if(archivio.noRetiPNPInArchivio()) break;
		   		    else {
		   		    		RetePetriP petrip=new RetePetriP();
		   		    		petrip=(RetePetriP)archivio.cercaRete();
		   		    		if(petrip==null) {
		   		    			InputOutput.mostraMessaggio(Vista.RETEPP_NON_PRES);
			    			}else {
			    				AbstractSimulazione simPetriP = new SimulazioneRetePetriP(petrip);
			    				simPetriP.simulaRete();
			    			}
		   		    }
		   		    		break;
	   		   }
		}while(flag);
	}
}
