package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import MainProgram.ArchivioReti;
import MainProgram.RelazioneDiFlusso;
import MainProgram.Rete;

public class TestReteCasiParticolari {

	Rete R1;
	ArchivioReti archivio;
	
	@Before
	public void setUp() throws Exception {
		R1 = new Rete();		
		archivio = new ArchivioReti();	
	}
	
	@Test
	public void testReteConSoloUnaRDF_ShouldBeCorrect() {
		R1 = new Rete();
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		R1.aggiungiRelazione(rf1);
		R1.setName("unRdf11");
		R1.stampaRete();
		
		assertThat(R1.isCorrect()).isTrue(); //non funziona
		// la prof vuole che ci sia almeno un posto e almeno una transizione ...
	}

	@Test
	public void testReteConRDFNegativi_ShouldNotBeCorrect() {
		R1 = new Rete();
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(-1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(2,-2,true);
		RelazioneDiFlusso rf4 = new RelazioneDiFlusso(1,2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.aggiungiRelazione(rf4); 
		R1.setName("negativa");

		assertThat(R1.isCorrect()).isFalse(); 
	}
	
	@Test
	public void testReteConTuttiRDFNegativi_ShouldNotBeCorrect() {
		R1 = new Rete();
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(-1,-1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(-2,-1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(-2,-2,true);
		RelazioneDiFlusso rf4 = new RelazioneDiFlusso(-1,-2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.aggiungiRelazione(rf4); 
		R1.setName("negativa");

		assertThat(R1.isCorrect()).isFalse(); //non funziona
	}
	
	@Test
	public void testReteNulla_ShouldNotBeCorrect() {
		R1 = new Rete();	
		R1.stampaRete();
		assertThat(R1.isCorrect()).isFalse(); //non funziona
	}
	
	@Test
	public void testReteNonInizializzata_ShouldNotBeCorrect() {
		R1.stampaRete();
		assertThat(R1.isCorrect()).isFalse(); //non funziona
	}
	
	@Test
	public void testReteNonInizializzata_ShouldNotBeSaved() {
		archivio.salvaRete(R1);
		assertThat(archivio.getArchivio()).doesNotContain(R1); //non funziona
		
		//questa rete non ha un nome, quindi non posso neanche cercarla nell'archivio ...
	}
}