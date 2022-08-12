package view;
import controller.*;

public class Stampa {
	
	private static final String ERRORE_ARCHIVIO_VUOTO = "Attenzione archivio vuoto";
	
	public static boolean noRetiInArchivio(ArchivioReti archivio) {
		for (AbstractRete rete : archivio.getArchivio()) {
			if (rete.getClass().getSimpleName().equals("Rete"))
				return false;
		}
		return true;
	}

	public static boolean noRetiPNInArchivio(ArchivioReti archivio) {
		for (AbstractRete rete : archivio.getArchivio()) {
			if (rete.getClass().getSimpleName().equals("RetePetri"))
				return false;
		}
		return true;
	}

	public static boolean noRetiPNPInArchivio(ArchivioReti archivio) {
		for (AbstractRete rete : archivio.getArchivio()) {
			if (rete.getClass().getSimpleName().equals("RetePetriP"))
				return false;
		}
		return true;
	}
	
	// visualizza i nomi di tutte e sole le reti presenti nell'archivio
		public static void visualizzaNomeReti(ArchivioReti archivio) {
			if (archivio.getArchivio() != null && !(archivio.getArchivio().isEmpty()) && !noRetiInArchivio(archivio)) {
				System.out.println("Nomi delle reti presenti: \n");
				for (AbstractRete elem : archivio.getArchivio()) {
					if (elem.getClass().getSimpleName().equals("Rete")) {
						System.out.println(elem.getName());
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}

		// visualizza il nome di tutte e sole le PN nell'archivio
		public static void visualizzaNomeRetiPN(ArchivioReti archivio) {
			if (archivio.getArchivio() != null && !(archivio.getArchivio().isEmpty()) && !noRetiPNInArchivio(archivio)) {
				System.out.println("Nomi delle reti di Petri presenti: \n");
				for (AbstractRete elem : archivio.getArchivio()) {
					if (elem.getClass().getSimpleName().equals("RetePetri")) {
						System.out.println(elem.getName());
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}

		public static void visualizzaNomeRetiPNP(ArchivioReti archivio) {
			if (archivio.getArchivio() != null && !(archivio.getArchivio().isEmpty()) && !noRetiPNPInArchivio(archivio)) {
				System.out.println("Nomi delle reti di Petri presenti: \n");
				for (AbstractRete elem : archivio.getArchivio()) {
					if (elem.getClass().getSimpleName().equals("RetePetriP")) {
						System.out.println(elem.getName());
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}

		public static void visualizzaSoloRetiArchivio(ArchivioReti archivio) {
			if (archivio.getArchivio() != null && !(archivio.getArchivio().isEmpty()) && !noRetiInArchivio(archivio)) {
				for (AbstractRete elem : archivio.getArchivio()) {
					if (elem.getClass().getSimpleName().equals("Rete")) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}

		public static void visualizzaSoloRetiPNArchivio(ArchivioReti archivio) {
			if (archivio.getArchivio() != null && !(archivio.getArchivio().isEmpty()) && !noRetiPNInArchivio(archivio)) {
				for (AbstractRete elem : archivio.getArchivio()) {
					if (elem.getClass().getSimpleName().equals("RetePetri")) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}

		public static void visualizzaSoloRetiPNPArchivio(ArchivioReti archivio) {
			if (archivio.getArchivio() != null && !(archivio.getArchivio().isEmpty()) && !noRetiPNPInArchivio(archivio)) {
				for (AbstractRete elem : archivio.getArchivio()) {
					if (elem.getClass().getSimpleName().equals("RetePetriP")) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
}

// in una classe controller devo fare un metodo che faccia scorrere l'archivio
