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
					String nome = LeggiInput.leggiStringa(RICHIESTA_NOME);
					r.setName(nome);
					do {
							relFlusso=LeggiInput.aOrb("scegiere a per inserire una relazione posto-transizione \n"
									+ "scegliere b per inerire una relazione transizione-posto:");
							int posto, transizione;
							if(relFlusso) {
								posto = LeggiInput.leggiIntero("inserire il numero della posizione");
								transizione = LeggiInput.leggiIntero("inserire il numero della transizione");
							}else {
								transizione = LeggiInput.leggiIntero("inserire il numero della transizione");
								posto = LeggiInput.leggiIntero("inserire il numero della posizione");
							}
							RelazioneDiFlusso rel = new RelazioneDiFlusso(posto, transizione,relFlusso);
							r.aggiungiRelazione(rel);
							continua = LeggiInput.yesOrNo("si desidera inserire una nuva relaizone di flusso?");
					}while(continua);
					if(r.isCorrect() && !RetiNormali.trovaNome(nome) ) {
						RetiNormali.aggiungiRete(r);
					}else if(RetiNormali.trovaNome(nome)) {
						System.out.println("la rete inserita ha lo stesso nome di una già esistente");
					}else {
						System.out.println("la rete inserita non è corretta");
					}
					
					break;
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
