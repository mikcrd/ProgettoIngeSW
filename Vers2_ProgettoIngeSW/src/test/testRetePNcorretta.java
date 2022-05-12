package test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import MainProgram2.*;


public class testRetePNcorretta {
	Rete R1;
	ArchivioReti archivio;
	RelazioneDiFlusso rf1;
	RelazioneDiFlusso rf2;
	RelazioneDiFlusso rf3;
	RelazioneDiFlusso rf4;
	
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
		archivio.salvaRete(R1);
	}
	
	@Test
	public void testRetePNCorrettaInserimentoInterattivo_ShouldBeSaved() {
		RetePN pn = new RetePN(archivio);
		archivio.aggiungiRete(pn); //inserisci i campi di una rete di Petri corretta
		assertThat(archivio.getArchivio()).contains(pn);
	}
	
	@Test
	public void testRetePNCorretta_ShouldBeSaved() {//non funziona
		RetePN pn = new RetePN(archivio);
		RelazionePN rpn1 = new RelazionePN(rf1, 1);
		RelazionePN rpn2 = new RelazionePN(rf2, 1);
		RelazionePN rpn3 = new RelazionePN(rf3, 1);
		RelazionePN rpn4 = new RelazionePN(rf4, 1);
		pn.aggiungiRelazione(rpn1);
		pn.aggiungiRelazione(rpn2);
		pn.aggiungiRelazione(rpn3);
		pn.aggiungiRelazione(rpn4);
		pn.setName("correttapn");
		pn.aggiungiMarcature(R1.getPos());
		archivio.salvaRete(pn);
		assertThat(archivio.getArchivio()).contains(pn);
	}
}
