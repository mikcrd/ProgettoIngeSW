package test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import javax.swing.SpringLayout.Constraints;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import mainProgram.*;

public class TestReteCorretta {

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
	public void testReteCorretta_ShouldBeCorrect() {
		assertThat(R1.isCorrect()).isTrue();
	}
	
	@Test
	public void testReteCorretta_ShouldHaveDifferentRDF() {
		assertThat(R1.getRelazioni()).doesNotHaveDuplicates();
	}

	@Test
	public void testReteCorretta_CanBeSaved() {
		archivio.salvaRete(R1);
		assertThat(archivio.getArchivio()).contains(R1);
	}
	
	@Test
	public void testReteCorretta_ShouldNotBeSavedIfAlreadyPresent() {
		Rete R2 = new Rete();
		RelazioneDiFlusso rf5 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf6 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf7 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf8 = new RelazioneDiFlusso(1,2,false);
		R2.aggiungiRelazione(rf5);
		R2.aggiungiRelazione(rf6);
		R2.aggiungiRelazione(rf7);
		R2.aggiungiRelazione(rf8);
		R2.setName("vienesalvataprima");
		archivio.salvaRete(R2);
		
		archivio.salvaRete(R1);
		assertThat(archivio.getArchivio()).doesNotContain(R1);
	}
	
	@Test
	public void testReteCorretta_CanBeViewed() {
		/**
		 * corretta
			posizione 1 transizione 1
			transizione 1 posizione 2
			posizione 2 transizione 2
			transizione 2 posizione 1
		 */
		String str = R1.toString();
		assertThat(str.contains(R1.getName()));
		assertThat(str.contains(R1.getRelazioni().toString()));
	}
	
	// Viene richiesto l'input del nome della rete da eliminare!! Scrivere: "corretta" e poi "s"
	@Ignore
	public void testReteCorretta_CanBeDeleted() {
		archivio.salvaRete(R1);
		archivio.eliminaRete();
		assertThat(archivio.getArchivio()).doesNotContain(R1);
	}
}
