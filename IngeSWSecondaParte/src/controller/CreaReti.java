package controller;

import model.*;
import view.*;

public class CreaReti {
	
	public CreaReti() {
		
	}

	public RelazioneDiFlusso creaPosto_Trans() {
		RelazioneDiFlusso rdf = new RelazioneDiFlusso();
		rdf.setPosizione(InputOutput.leggiInteroPositivo(Vista.RDF_POSTO));
		rdf.setTransizione(InputOutput.leggiInteroPositivo(Vista.RDF_TRANSIZIONE));
		rdf.setInOut(true);
		return rdf;		
	}
	
	public RelazioneDiFlusso creaTrans_Posto() {
		RelazioneDiFlusso rdf = new RelazioneDiFlusso();
		rdf.setTransizione(InputOutput.leggiInteroPositivo(Vista.RDF_TRANSIZIONE));
		rdf.setPosizione(InputOutput.leggiInteroPositivo(Vista.RDF_POSTO));
		rdf.setInOut(false);
		return rdf;
	}
	
	public String stampaRDF(RelazioneDiFlusso rdf) {
		if (rdf.isInOut())
			return Vista.POSTO + rdf.getPosizione() + Vista.TRANSIZIONE + rdf.getTransizione();
		else 
			return  Vista.TRANSIZIONE + rdf.getTransizione() + Vista.POSTO + rdf.getPosizione();
	}
	
/*	public Rete datixCreaRete() {
		String nome = InputOutput.leggiStringaNonVuota(Vista.RETE_MESS_NOME);
		char aOb = InputOutput.leggiChar(Vista.RETE_POSTOTRANS_TRANSPOSTO);
		String messErroreChar = Vista.RETE_ERRORE_SCELTA_AB;
		String messRelazioneGiaPresente	= Vista.RETE_RELAZIONE_DI_FLUSSO_GIÀ_PRESENTE;
		boolean yesNo = InputOutput.yesOrNo(Vista.RETE_INSERIMENTO_RELAZIONI);	
	}*/
	
	public String nomeRete() {
		return InputOutput.leggiStringaNonVuota(Vista.RETE_MESS_NOME);
	}
	
	public char sceltaPostoTrans_TransPostoRete() {
		return InputOutput.leggiChar(Vista.RETE_POSTOTRANS_TRANSPOSTO);
	}
	
	public String messErroreCharRete() {
		return Vista.RETE_ERRORE_SCELTA_AB;
	}
	
	public String messErroreRelazioneGiaPresenteRete() {
		return Vista.RETE_RELAZIONE_DI_FLUSSO_GIÀ_PRESENTE;
	}
	
	public boolean termineInserimentoRelazioniRete() {
		return InputOutput.yesOrNo(Vista.RETE_INSERIMENTO_RELAZIONI);	
	}
	
	public void messReteCorretta() {
		InputOutput.mostraMessaggio(Vista.MESSAGGIO_RETE_CORRETTA);
	}
	
	public void messReteNonCorretta() {
		InputOutput.mostraMessaggio(Vista.MESSAGGIO_RETE_NON_CORRETTA);
	}

	/*
	public void stampaRete(AbstractRete rete) {
		InputOutput.mostraMessaggio(Vista.NEWLINE);
		InputOutput.mostraMessaggio(rete.getName());
		for (AbstractRelazioneDiFlusso r : rete.getRelazioni()) {
			if (r instanceof RelazioneDiFlusso) {
				InputOutput.mostraMessaggio(stampaRDF((RelazioneDiFlusso) r));
			} //else ...
		}
	}*/
	
}
