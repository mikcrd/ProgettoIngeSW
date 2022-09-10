package tests;

import model.*;

import java.io.File;

import org.junit.*;

import controller.*;

public class TestCase_ConfiguratoreRetiPetriP {

	Rete R1;
	RelazioneDiFlusso rf1, rf2, rf3, rf4;
	RetePetri P1;
	RelazionePetri rp1, rp2, rp3, rp4;
	RetePetriP PP1;
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
		P1.stampaRete();
		
		PP1 = new RetePetriP(P1, archivio);
		PP1.setName("priorità1");
		PP1.inizializzaPriorità(2);
		PP1.setPriorità(2, 0);
		PP1.setPriorità(2, 1);
		
		archivio.salvaRete(R1);
		archivio.salvaRete(P1);
		
	}

	@Test
	public void retePetriPCorretta_VieneSalvataInArchivio() {
		archivio.salvaRete(PP1);
		Assert.assertTrue(archivio.getArchivio().contains(PP1));
	}
	
	
	
	@Test
	//immetti "priorità1" e "s" per eliminare la rete
	public void reteInArchivio_VieneEliminata() {
		archivio.salvaRete(PP1);
		Assert.assertTrue(archivio.getArchivio().contains(PP1));	
		archivio.eliminaRete(PP1);
		Assert.assertFalse(archivio.getArchivio().contains(PP1));	
	}
	
	
	@Test
	public void retePetriPCorretta_VieneSalvataInFileXML() {
		archivio.salvaRete(PP1);
		archivio = GestioneFile.xmlToObj(Menu.getFile()); //sovrascrive l'archivio con il contenuto dell'xml
		Assert.assertTrue(archivio.getArchivio().contains(PP1));
	}
	
	@Test
	//C:\Users\michela\Downloads\prova3.xml
	public void reteInXML_VieneInseritaDaFile() {
		
		archivio.salvaReteDaFile("C:\\data\\prior.xml");
		archivio.visualizzaNomeRetiPN();
	    Assert.assertTrue(archivio.getArchivio().stream()
			.filter(retepetrip -> retepetrip.getName().equals("prior")).findAny().isPresent());
	}

}
