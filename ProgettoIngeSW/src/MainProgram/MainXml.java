package MainProgram;

public class MainXml {

	public static void main(String[] args) {
/**		
		Rete r = new Rete();
		RelazioneDiFlusso r1 = new RelazioneDiFlusso(1, 3, false);
		RelazioneDiFlusso r2 = new RelazioneDiFlusso(1, 4, true);
		RelazioneDiFlusso r3 = new RelazioneDiFlusso(5, 3, false);
		RelazioneDiFlusso r4 = new RelazioneDiFlusso(7, 3, true);
		
		
		r.aggiungiRelazione(r1);
		r.aggiungiRelazione(r2);
		r.aggiungiRelazione(r3);
		r.aggiungiRelazione(r4);
		
		Rete R2 = new Rete();
		RelazioneDiFlusso r5 = new RelazioneDiFlusso(1, 3, false);
		RelazioneDiFlusso r6 = new RelazioneDiFlusso(1, 4, true);
		RelazioneDiFlusso r7 = new RelazioneDiFlusso(5, 3, false);
		RelazioneDiFlusso r8 = new RelazioneDiFlusso(7, 3, true);
		
		R2.aggiungiRelazione(r5);
		R2.aggiungiRelazione(r6);
		R2.aggiungiRelazione(r7);
		R2.aggiungiRelazione(r8);
		
		r.stampaRete();
		
		
		
		r.inizializzaRete();
		
		ArchivioReti arch = new ArchivioReti();
		arch.getArchivio().add(r);
		arch.getArchivio().add(R2);
		
		GestioneFile.objToXml(arch);
    **/	
		
		Menu menu = new Menu();
		menu.cicloApplicazione();
	}

}
