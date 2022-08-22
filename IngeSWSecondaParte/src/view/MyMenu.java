package view;

public class MyMenu {
	
	  private String titolo;
	  private String [] voci;

		
	  public MyMenu (String titolo, String [] voci)
	  {
		this.titolo = titolo;
		this.voci = voci;
	  }

	  public int scegli ()
	  {
		//stampaMenu();
		return InputOutput.leggiIntero(Vista.MYMENU_RICHIESTA_INSERIMENTO, 0, voci.length);	 
	  }
			
	  public void stampaMenu ()
	  {
		  InputOutput.mostraMessaggio(Vista.MYMENU_CORNICE);
		  InputOutput.mostraMessaggio(titolo);
		  InputOutput.mostraMessaggio(Vista.MYMENU_CORNICE);
	    for (int i=0; i<voci.length; i++)
		 {
	    	InputOutput.mostraMessaggio( (i+1) + "\t" + voci[i]);
		 }
	    System.out.println();
	    InputOutput.mostraMessaggio(Vista.MYMENU_VOCE_USCITA);
	    System.out.println();
	  }
}
