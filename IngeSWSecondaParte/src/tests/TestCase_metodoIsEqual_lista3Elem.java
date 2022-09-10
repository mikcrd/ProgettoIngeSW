package tests;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.*;
import controller.*;




public class TestCase_metodoIsEqual_lista3Elem {
	
	Rete R3;
	RetePetri P1;
	ArchivioReti archivio;
	RelazioneDiFlusso rf5, rf6, rf7, rf8, rf9, rf10;
	RelazionePetri rp1, rp2, rp3, rp4;

	@Before
	public void setUp() throws Exception {
		archivio = new ArchivioReti();
		
		R3 = new Rete();
		rf5 = new RelazioneDiFlusso(1,1,true);
		rf6 = new RelazioneDiFlusso(2,1,false);
		rf7 = new RelazioneDiFlusso(2,2,true);
		rf8 = new RelazioneDiFlusso(3,2,false);
		rf9 = new RelazioneDiFlusso(3,3,true);
		rf10 = new RelazioneDiFlusso(1,3,false);
		R3.aggiungiRelazione(rf5);
		R3.aggiungiRelazione(rf6);
		R3.aggiungiRelazione(rf7);
		R3.aggiungiRelazione(rf8);
		R3.aggiungiRelazione(rf9);
		R3.aggiungiRelazione(rf10);
		R3.setName("rete1");
		archivio.salvaRete(R3);
		
		P1 = new RetePetri(archivio);
	    rp1 = new RelazionePetri(1, 1, true, 1);
		rp2 = new RelazionePetri(2, 1, false, 1);
		rp3 = new RelazionePetri(2, 2, true, 1);
        rp4 = new RelazionePetri(1, 2, false, 1);
		P1.inizializzaMarcature(2);
		P1.aggiungiRelazione(rp1);
		P1.aggiungiRelazione(rp2);
		P1.aggiungiRelazione(rp3);
		P1.aggiungiRelazione(rp4);
		P1.setName("petri1");
		P1.setMarcatura(5,0);
		P1.setMarcatura(5,1);
		archivio.salvaRete(P1);
		archivio.visualizzaNomeReti(); 
		archivio.visualizzaNomeRetiPN();
		
		RetePetriP PP1 = new RetePetriP(P1);
		PP1.setName("prior1");
		PP1.inizializzaPriorità(2);
		PP1.setPriorità(4, 0);
		PP1.setPriorità(2, 1);
		archivio.salvaRete(PP1);
		archivio.visualizzaNomeRetiPNP();
	}

	@Test
	public void lista3Elem_isEqualVero() {
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
		
		Assert.assertTrue(archivio.isEqual(P2));
	}
	
	@Test
	public void lista3Elem_isEqualFalso() {	
		Rete R4 = new Rete();
		RelazioneDiFlusso fr11 = new RelazioneDiFlusso(1, 1, true);
		RelazioneDiFlusso fr12 = new RelazioneDiFlusso(2, 1, false);
		RelazioneDiFlusso fr13 = new RelazioneDiFlusso(3, 1, false);
		RelazioneDiFlusso fr14 = new RelazioneDiFlusso(2, 2, true);
		RelazioneDiFlusso fr15 = new RelazioneDiFlusso(3, 2, true);
		RelazioneDiFlusso fr16 = new RelazioneDiFlusso(4, 2, false);
		RelazioneDiFlusso fr17 = new RelazioneDiFlusso(4, 3, true);
		RelazioneDiFlusso fr18 = new RelazioneDiFlusso(1, 3, false);
		R4.aggiungiRelazione(fr11);
		R4.aggiungiRelazione(fr12);
		R4.aggiungiRelazione(fr13);
		R4.aggiungiRelazione(fr14);
		R4.aggiungiRelazione(fr15);
		R4.aggiungiRelazione(fr16);
		R4.aggiungiRelazione(fr17);
		R4.aggiungiRelazione(fr18);
		R4.setName("rete2");
		
		Assert.assertFalse(archivio.isEqual(R4));
	}
	
	@Test
	public void lista3Elem_isEqualFalso_retiConLoStessoNome() {
		RetePetriP PP2 = new RetePetriP(P1);
		PP2.setName("rete1");
		PP2.inizializzaPriorità(2);
		PP2.setPriorità(2, 0);
		PP2.setPriorità(2, 1);
		
		Assert.assertFalse(archivio.isEqual(PP2));
	}
	
	@Test
	public void lista3Elem_isEqualVero_stessaTopologia() {
		RetePetriP PP3 = new RetePetriP(P1);
		PP3.setName("prior1");
		PP3.inizializzaPriorità(2);
		PP3.setPriorità(4, 0);
		PP3.setPriorità(2, 1);
		
		Assert.assertTrue(archivio.isEqual(PP3));
	}
	
/////////////////////////////////////////////////////
	@Test
	//con tipi diversi non dà il messaggio "stessa topologia"
	public void lista3Elem_isEqualFalso_stessaTopologia() {
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
		P2.setName("petri2");
		P2.setMarcatura(6,0);
		P2.setMarcatura(6,1);
		
		Assert.assertFalse(archivio.isEqual(P2));
	}
}
