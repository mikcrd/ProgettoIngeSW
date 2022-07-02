package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.*;

import mainProgram.*;


public class TestCase_ConfiguratoreRetiPetri {
	
	Rete R1;
	RelazioneDiFlusso rf1, rf2, rf3, rf4;
	RetePetri P1;
	RelazionePetri rp1, rp2, rp3, rp4;
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
		R1.setName("corretta");
		
		P1 = new RetePetri(archivio);
	    rp1 = new RelazionePetri(rf1, 1);
		rp2 = new RelazionePetri(rf2, 1);
		rp3 = new RelazionePetri(rf3, 1);
        rp4 = new RelazionePetri(rf4, 1);
		P1.inizializzaMarcature(2);
		P1.aggiungiRelazione(rp1);
		P1.aggiungiRelazione(rp2);
		P1.aggiungiRelazione(rp3);
		P1.aggiungiRelazione(rp4);
		P1.setName("correttapn");
		P1.setMarcatura(5,0);
		P1.setMarcatura(5,1);
	}

	@Test
	public void retePetriCorretta_VieneSalvataInArchivio() {
		archivio.salvaRete(P1);
		Assert.assertTrue(archivio.getArchivio().contains(P1));
	}

	@Test
	public void retePetriCorretta_VieneSalvataInFileXML() {
		archivio.salvaRete(P1);
		archivio = GestioneFile.xmlToObj(Menu.file); //sovrascrive l'archivio con il contenuto dell'xml
		Assert.assertTrue(archivio.getArchivio().contains(P1));
	}
	
	@Test
	//immetti "correttapn" per eliminare la rete
	public void reteInArchivio_VieneEliminata() {
		archivio.salvaRete(P1);
		Assert.assertTrue(archivio.getArchivio().contains(P1));
		RetePetri net = new RetePetri(archivio);
		archivio.eliminaRete(net);
		Assert.assertFalse(archivio.getArchivio().contains(P1));	
	}
	
	@Test
	//C:\Users\michela\Downloads\prova3.xml
	public void reteInXML_VieneInseritaDaFile() {
		archivio.salvaRete(R1);
		archivio.salvaReteDaFile("C:\\Users\\michela\\Downloads\\prova3.xml");
	    Assert.assertTrue(archivio.getArchivio().stream()
			.filter(retepetri -> retepetri.getName().equals("petri1")).findAny().isPresent());
	}
	
}
