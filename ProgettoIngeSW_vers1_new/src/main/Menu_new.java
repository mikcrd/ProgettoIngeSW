package main;

import utility.LeggiInput;
import utility.MyMenu;

public class Menu_new {
	private final static String TITOLO ="ARCHIVIO RETI";
	private final static String RICHIESTA_NOME="inserire il nome della rete";
	private final static String VOCI[] ={"Aggiungi rete", "Visualizza rete", "Elimina rete", "Visualizza l'archivio reti"};
	
	MyMenu menu = new MyMenu(TITOLO, VOCI);
	ArchivioRetiNew RetiNormali =new ArchivioRetiNew();
	
	public void cicloAppliczione() {
		do {
			menu.stampaMenu();
			int scelta = menu.scegli();
			switch(scelta) {
				case 0: System.exit(0); break;
				case 1: RetiNormali.aggiungiRete(); break;
				case 2: RetiNormali.visualizzaRete(); break;
				case 3:	RetiNormali.eliminaRete(this.richiestaNomeRete()); break;
				case 4: RetiNormali.visualizzaArchivio(); break;
			}
		}while (true);
	}
	
	public String richiestaNomeRete() {
		return LeggiInput.leggiStringa(RICHIESTA_NOME);
	}
		
	
	
	
		
	
}
