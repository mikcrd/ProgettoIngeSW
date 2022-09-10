package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.*;
import controller.*;



public class TestCase_metodoIsEqual_lista0Elem {

	Rete R1, R2, R3;
	ArchivioReti archivio;
	
	@Before
	public void setUp() {
		archivio = new ArchivioReti();		
	}
	
	@Test
	public void lista0Elem_isEqualSempreFalso_Rete() {
		Rete R0 = new Rete();
		RelazioneDiFlusso rf1 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf2 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf3 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf4 = new RelazioneDiFlusso(1,2,false);
		R0.aggiungiRelazione(rf1);
		R0.aggiungiRelazione(rf2);
		R0.aggiungiRelazione(rf3);
		R0.aggiungiRelazione(rf4);
		R0.setName("rete0");
		
		Assert.assertFalse(archivio.isEqual(R0));
	}
	
	@Test
	public void lista0Elem_isEqualSempreFalso_RetePetri() {
		RetePetri P0 = new RetePetri(archivio);
	    RelazionePetri rp1 = new RelazionePetri(1, 1, true, 1);
	    RelazionePetri rp2 = new RelazionePetri(2, 1, false, 1);
	    RelazionePetri rp3 = new RelazionePetri(2, 2, true, 1);
	    RelazionePetri rp4 = new RelazionePetri(1, 2, false, 1);
		P0.inizializzaMarcature(2);
		P0.aggiungiRelazione(rp1);
		P0.aggiungiRelazione(rp2);
		P0.aggiungiRelazione(rp3);
		P0.aggiungiRelazione(rp4);
		P0.setName("petri0");
		P0.setMarcatura(5,0);
		P0.setMarcatura(5,1);
		
		Assert.assertFalse(archivio.isEqual(P0));
	}
	
	@Test
	public void lista0Elem_isEqualSempreFalso_RetePetriP() {
		RetePetri P1 = new RetePetri(archivio);
		RelazionePetri rp1 = new RelazionePetri(1, 1, true, 1);
	    RelazionePetri rp2 = new RelazionePetri(2, 1, false, 1);
	    RelazionePetri rp3 = new RelazionePetri(2, 2, true, 1);
	    RelazionePetri rp4 = new RelazionePetri(1, 2, false, 1);
		P1.inizializzaMarcature(2);
		P1.aggiungiRelazione(rp1);
		P1.aggiungiRelazione(rp2);
		P1.aggiungiRelazione(rp3);
		P1.aggiungiRelazione(rp4);
		P1.setName("petri1");
		P1.setMarcatura(5,0);
		P1.setMarcatura(5,1);
		
		RetePetriP PP1 = new RetePetriP(P1);
		PP1.setName("petri1");
		PP1.inizializzaPriorità(2);
		PP1.setPriorità(2, 0);
		PP1.setPriorità(2, 1);
		
		Assert.assertFalse(archivio.isEqual(PP1));
	}
}
