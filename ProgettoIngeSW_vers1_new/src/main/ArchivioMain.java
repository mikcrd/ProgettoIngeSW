package main;

import utility.LeggiInput;
import utility.MyMenu;

public class ArchivioMain {
	
	private final static String TITOLO ="ARCHIVIO RETI";
	private final static String RICHIESTA_NOME="inserire il nome della rete:";
	private final static String VOCI[] ={"Aggiungi rete", "Visualizza rete", "Elimina rete", "Visualizza l'archivio reti"};

	public static void main(String[] args) {
		ArchivioRetiNew RetiNormali =new ArchivioRetiNew();
		MyMenu menu = new MyMenu(TITOLO, VOCI);
		
		do {
			menu.stampaMenu();
			int scelta = menu.scegli();
			switch(scelta) {
				case 0: System.exit(0); break;
				case 1:{
					Rete r = new Rete();
					boolean continua,relFlusso;
					String richiestaNome = LeggiInput.leggiStringa(RICHIESTA_NOME);
					r.setName(richiestaNome);
					do {
							relFlusso=LeggiInput.aOrb("scegiere a per inserire una relazione posto-transizione \nscegliere b per inerire una relazione transizione-posto:");
							continua = LeggiInput.yesOrNo("si desidera inserire una nuva relaizone di flusso?");
					}while(continua);
					RetiNormali.aggiungiRete(r); break;
				}
				case 2:{
					String richiestaNome = LeggiInput.leggiStringa(RICHIESTA_NOME);
					RetiNormali.visualizzaRete(richiestaNome); break;
				}
				case 3:{
					String richiestaNome = LeggiInput.leggiStringa(RICHIESTA_NOME);
					RetiNormali.eliminaRete(richiestaNome); break;
				}
				case 4: RetiNormali.visualizzaArchivio(); break;
			}
		}while (true);

	}
	
	
}
