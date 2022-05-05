package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mainProgram.*;

public class TestArchivio {

	ArchivioReti archivio;
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		archivio = new ArchivioReti();
//		System.setOut(new PrintStream(outputStreamCaptor));
		
		Rete R1 = new Rete();
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf4 = new RelazioneDiFlusso(1,2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.aggiungiRelazione(rf4);
		R1.setName("corretta");	
		archivio.salvaRete(R1);
		
		Rete R2 = new Rete();
		RelazioneDiFlusso rf5 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf6 = new RelazioneDiFlusso(3,2,false);
		R2.aggiungiRelazione(rf5);
		R2.aggiungiRelazione(rf6);
		R2.setName("NONcorretta");
		archivio.salvaRete(R2);  //non viene salvata
		
		Rete R3 = new Rete();
		RelazioneDiFlusso rf7 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf8 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf9 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf10 = new RelazioneDiFlusso(3,2,false);
		RelazioneDiFlusso rf11 = new RelazioneDiFlusso(3,3,true);
		RelazioneDiFlusso rf12 = new RelazioneDiFlusso(1,3,false);
		R3.aggiungiRelazione(rf7);
		R3.aggiungiRelazione(rf8);
		R3.aggiungiRelazione(rf9);
		R3.aggiungiRelazione(rf10);
		R3.aggiungiRelazione(rf11);
		R3.aggiungiRelazione(rf12);
		R3.setName("2corretta");
		archivio.salvaRete(R3);
		
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@Test
	public void TestArchivio_CorrectNetNamePresent() {
		archivio.visualizzaArchivio();
		Assert.assertTrue(outputStreamCaptor.toString().trim().contains("corretta"));
	}
	
	@Test
	public void TestArchivio_NotCorrectNetNameNotPresent() {
		archivio.visualizzaArchivio();
		Assert.assertFalse(outputStreamCaptor.toString().trim().contains("NONcorretta"));
	}
	
	@Test
	public void TestArchivio_CorrectNetsAllNamesPresent() {
		archivio.visualizzaArchivio();
		Assert.assertTrue(outputStreamCaptor.toString().trim().contains("corretta"));
		Assert.assertTrue(outputStreamCaptor.toString().trim().contains("2corretta"));
	}

	@After
	public void tearDown() {
	    System.setOut(standardOut);
	}
}
