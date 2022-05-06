package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mainProgram.*;

public class TestReteNonCorretta {

	Rete R1;
	ArchivioReti archivio;
	
	@Before
	public void setUp() throws Exception {
		R1 = new Rete();		
		archivio = new ArchivioReti();	
	}

	@Test
	public void testReteNonConnessa_shouldNotBeCorrect() {
		
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(3,2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.setName("NONcorretta");
		
		assertThat(R1.isCorrect()).isFalse();
	}
 
	@Test
	public void testReteNonConnessa_shouldNotBeSaved() {
		
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(3,2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.setName("NONcorretta");
		
		archivio.salvaRete(R1);
		assertThat(archivio.getArchivio()).doesNotContain(R1);
	}
	
	@Test
	public void testReteConRDFduplicati_shouldNotBeCorrect() {
		
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(1,1,true);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.setName("NONcorretta");
	
		assertThat(rf1.equals(rf3)).isTrue();
		
		assertThat(R1.getRelazioni()).contains(rf1, rf3);
		
		assertThat(R1.isCorrect()).isFalse();
	}
	
	@Test
	public void testReteConRDFduplicati_shouldNotBeSaved() {
		
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(1,1,true);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.setName("NONcorretta");
		
		archivio.salvaRete(R1);
		assertThat(archivio.getArchivio()).doesNotContain(R1);
	}
}
