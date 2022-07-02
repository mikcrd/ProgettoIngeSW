package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mainProgram.*;


public class TestCase_ConfiguratoreReti {
	
	Rete R1;
	ArchivioReti archivio;
	
	@Before
	public void setUp() {
		
		R1 = new Rete();
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf4 = new RelazioneDiFlusso(1,2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.aggiungiRelazione(rf4);
		R1.setName("corretta");
		
		archivio = new ArchivioReti();	
	}

	@Test
	public void reteCorretta_VieneSalvataInArchivio() {
		archivio.salvaRete(R1);
		Assert.assertTrue(archivio.getArchivio().contains(R1));
	}

	@Test
	public void reteCorretta_VieneSalvataInFileXML() {
		archivio.salvaRete(R1);
		archivio = GestioneFile.xmlToObj(Menu.file); //sovrascrive l'archivio con il contenuto dell'xml
		Assert.assertTrue(archivio.getArchivio().contains(R1));
	}

	@Test
	//immetti "corretta" per eliminare la rete
	public void reteInArchivio_VieneEliminata() {
		archivio.salvaRete(R1);
		Assert.assertTrue(archivio.getArchivio().contains(R1));	
		Rete rete = new Rete(archivio);
		archivio.eliminaRete(rete);
		Assert.assertFalse(archivio.getArchivio().contains(R1));	
	}
	
	@Test
	//immetti il path di un file contenente una rete chiamata "rete2" 
	//C:\Users\michela\Documents\prova2.xml
	public void reteInXML_VieneInseritaDaFile() {
		archivio.salvaReteDaFile("C:\\Users\\michela\\Documents\\prova2.xml");
		archivio.visualizzaNomeReti();
		Assert.assertTrue(archivio.getArchivio().stream()
				.filter(rete -> rete.getName().equals("rete2")).findAny().isPresent());
	}
}
