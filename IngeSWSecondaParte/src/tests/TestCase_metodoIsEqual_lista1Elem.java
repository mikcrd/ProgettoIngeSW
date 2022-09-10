package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.*;
import controller.*;



public class TestCase_metodoIsEqual_lista1Elem {

	Rete R1, R2, R3;
	RelazioneDiFlusso rf1, rf2, rf3, rf4;
	ArchivioReti archivio;
	
	@Before
	public void setUp() throws Exception {
		archivio = new ArchivioReti();	
		
		R1 = new Rete();
		rf1 = new RelazioneDiFlusso(1,1,true);
		rf2 = new RelazioneDiFlusso(2,1,false);
		rf3 = new RelazioneDiFlusso(2,2,true);
		rf4 = new RelazioneDiFlusso(1,2,false);
		R1.aggiungiRelazione(rf1);
		R1.aggiungiRelazione(rf2);
		R1.aggiungiRelazione(rf3);
		R1.aggiungiRelazione(rf4);
		R1.setName("rete1");
		archivio.salvaRete(R1);
	}

	@Test
	public void lista1Elem_isEqualVero() {
		Rete R2 = new Rete();
		RelazioneDiFlusso rf5 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf6 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf7 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf8 = new RelazioneDiFlusso(1,2,false);
		R2.aggiungiRelazione(rf5);
		R2.aggiungiRelazione(rf6);
		R2.aggiungiRelazione(rf7);
		R2.aggiungiRelazione(rf8);
		R2.setName("rete1");
		
		Assert.assertTrue(archivio.isEqual(R2));
	}
	
	@Test
	public void lista1Elem_isEqualFalso() {
		archivio.visualizzaNomeReti(); 
		RetePetri P2 = new RetePetri(archivio);
	    RelazionePetri rp1 = new RelazionePetri(1, 1, true, 1);
		RelazionePetri rp2 = new RelazionePetri(2, 1, false, 1);
		RelazionePetri rp3 = new RelazionePetri(2, 2, true, 1);
        RelazionePetri rp4 = new RelazionePetri(1, 2, false, 1);
		P2.inizializzaMarcature(2);
		P2.aggiungiRelazione(rp1);
		P2.aggiungiRelazione(rp2);
		P2.aggiungiRelazione(rp3);
		P2.aggiungiRelazione(rp4);
		P2.setName("petri1");
		P2.setMarcatura(5,0);
		P2.setMarcatura(5,1);
		
        Assert.assertFalse(archivio.isEqual(P2));
	}

	@Test
	public void lista1Elem_isEqualFalso_retiConLoStessoNome() {
		archivio.visualizzaNomeReti(); 
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
		PP1.setName("rete1");
        Assert.assertFalse(archivio.isEqual(PP1));
	}
	
	@Test
	public void lista1Elem_isEqualVero_stessaTopologia() {
		Rete R4 = new Rete();
		RelazioneDiFlusso rf5 = new RelazioneDiFlusso(1,1,true);
		RelazioneDiFlusso rf6 = new RelazioneDiFlusso(2,1,false);
		RelazioneDiFlusso rf7 = new RelazioneDiFlusso(2,2,true);
		RelazioneDiFlusso rf8 = new RelazioneDiFlusso(1,2,false);
		R4.aggiungiRelazione(rf5);
		R4.aggiungiRelazione(rf6);
		R4.aggiungiRelazione(rf7);
		R4.aggiungiRelazione(rf8);
		R4.setName("stessaTop");
		
		Assert.assertTrue(archivio.isEqual(R4));
	}

//////////////////////////////////////////////////////
	@Test
	public void lista1Elem_isEqualFalso_stessaTopologia() {
		RetePetri P1 = new RetePetri(archivio);
		RelazionePetri rp1 = new RelazionePetri(rf1, 1);
	    RelazionePetri rp2 = new RelazionePetri(rf2, 1);
	    RelazionePetri rp3 = new RelazionePetri(rf3, 1);
	    RelazionePetri rp4 = new RelazionePetri(rf4, 1);
		P1.inizializzaMarcature(2);
		P1.aggiungiRelazione(rp1);
		P1.aggiungiRelazione(rp2);
		P1.aggiungiRelazione(rp3);
		P1.aggiungiRelazione(rp4);
		P1.setName("petri1");
		P1.setMarcatura(5,0);
		P1.setMarcatura(5,1);
		
		Assert.assertFalse(archivio.isEqual(P1));
	}
}
