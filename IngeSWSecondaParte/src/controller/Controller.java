package controller;

import model.*;
import view.*;

public class Controller {
	
	
	public static final String DESCRIZIONE_STAMPA_PRIORITA = "Transizione ";

	public static RelazioneDiFlusso creaPosto_Trans() {
		RelazioneDiFlusso rdf = new RelazioneDiFlusso();
		rdf.setPosizione(InputOutput.leggiInteroPositivo(Vista.RDF_POSTO));
		rdf.setTransizione(InputOutput.leggiInteroPositivo(Vista.RDF_TRANSIZIONE));
		rdf.setInOut(true);
		return rdf;		
	}
	
	public static RelazioneDiFlusso creaTrans_Posto() {
		RelazioneDiFlusso rdf = new RelazioneDiFlusso();
		rdf.setTransizione(InputOutput.leggiInteroPositivo(Vista.RDF_TRANSIZIONE));
		rdf.setPosizione(InputOutput.leggiInteroPositivo(Vista.RDF_POSTO));
		rdf.setInOut(false);
		return rdf;
	}
	
	public static String stampaRDF(RelazioneDiFlusso rdf) {
		if (rdf.isInOut())
			return Vista.POSTO + rdf.getPosizione() + Vista.TRANSIZIONE + rdf.getTransizione();
		else 
			return  Vista.TRANSIZIONE + rdf.getTransizione() + Vista.POSTO + rdf.getPosizione();
	}
	
	public static String stampaRP(RelazionePetri rp) {
		return "[" + stampaRDF((RelazioneDiFlusso)(rp.getRelazioneSottostante())) + ", peso= " + rp.getPeso() + "]";
	}
	
	public static void stampaMarcature(RetePetri petri) {
		InputOutput.mostraMessaggio(Vista.ELENCO_MARCATURE);
		for (int i =0; i<petri.getMarcature().length; i++) {
			int j=i;
			String.format(Vista.DESCRIZIONE_STAMPA_MARCATURE , ++j , petri.getMarcatura(i));
		}
	}
	
	public static void stampaPriorità(RetePetriP prior) {
		System.out.println(Vista.ELENCO_PRIORITÀ);
		for(int i=0; i<prior.getPriorita().length; i++) {
			int j=i;
			String.format(Vista.DESCRIZIONE_STAMPA_PRIORITA , ++j , prior.getPriorita(i));
		}
	}
	

	public static String nomeRete() {
		return InputOutput.leggiStringaNonVuota(Vista.RETE_MESS_NOME);
	}
	
	public static char sceltaPostoTrans_TransPostoRete() {
		return InputOutput.leggiChar(Vista.RETE_POSTOTRANS_TRANSPOSTO);
	}
	
	public static char messErroreCharRete() {
		return InputOutput.leggiChar(Vista.RETE_ERRORE_SCELTA_AB);
	}
	
	public static String messErroreRelazioneGiaPresenteRete() {
		return Vista.RETE_RELAZIONE_DI_FLUSSO_GIÀ_PRESENTE;
	}
	
	public static boolean termineInserimentoRelazioniRete() {
		return InputOutput.yesOrNo(Vista.RETE_INSERIMENTO_RELAZIONI);	
	}
	
	public static void messReteCorretta() {
		InputOutput.mostraMessaggio(Vista.MESSAGGIO_RETE_CORRETTA);
	}
	
	public static void messReteNonCorretta() {
		InputOutput.mostraMessaggio(Vista.MESSAGGIO_RETE_NON_CORRETTA);
	}

	public static int messInserimentoMarcatura(int posizione) {
		return InputOutput.leggiInteroNonNegativo(String.format(Vista.INSERIMENTO_MARCATURA, posizione));
	}
	
	public static int messInserimentoPeso(AbstractRelazioneDiFlusso absRel) {
		return InputOutput.leggiInteroPositivo(String.format(Vista.INSERIMENTO_PESO, stampaRDF((RelazioneDiFlusso)absRel)));
	}
	
	public static int messInserimentoPriorità(int transizione) {
		return InputOutput.leggiInteroPositivo(String.format(Vista.INSERIMENTO_PRIORITA, transizione));
	}
	
	public static void messAssenzaReteSuCuiCostruireRetePetri() {
		InputOutput.mostraMessaggio(Vista.MESS_NORETETOPOLOGIA);
	}
	
	public static void messAssenzaRetePetriSuCuiCostruireRetePetriP() {
		InputOutput.mostraMessaggio(Vista.MESS_NORETEPETRITOPOLOGIA);
	}
	
	public static void messErroreAggiungiPrimaUnaRete() {
		InputOutput.mostraMessaggio(Vista.AGGIUNGI_PRIMA_UNA_RETE);
	}
	
	public static String scegliRetePerCostruireRetePetri() {
		return InputOutput.leggiStringaNonVuota(Vista.SCEGLI_RETE);
	}
	
	public static void messErroreReteNonTrovata() {
		InputOutput.mostraMessaggio(Vista.MESS_NON_TROVATA);
	}
	
	public static String nomeRetePetri() {
		return InputOutput.leggiStringaNonVuota(Vista.RETEP_MESS_NOME);
	}
	
	public static void cornice() {
		InputOutput.mostraMessaggio(Vista.MYMENU_CORNICE);
	}
	
	public static void messErroreStessaTopologia() {
		InputOutput.mostraMessaggio(Vista.ARCHIVIO_ISEQUAL_MESS_STESSA_TOPOLOGIA);
	}
	
	public static void messErroreAggiungiPrimaUnaReteP() {
		InputOutput.mostraMessaggio(Vista.AGGIUNGI_PRIMA_UNA_RETE_P);
	}
	
	public static String scegliRetePetriPerCostruireRetePetriP() {
		return InputOutput.leggiStringaNonVuota(Vista.SCEGLI_RETE_P);
	}
	
	public static String nomeRetePetriP() {
		return InputOutput.leggiStringaNonVuota(Vista.RETEPP_MESS_NOME);
	}
	
	public static void stampaReteController(Rete rete) {
		InputOutput.mostraMessaggio(Vista.NEWLINE);
		InputOutput.mostraMessaggio(rete.getName());
		for (AbstractRelazioneDiFlusso r : rete.getRelazioni()) {
			if (r instanceof RelazioneDiFlusso) {
				InputOutput.mostraMessaggio(stampaRDF((RelazioneDiFlusso) r));
			} 
		}
	}
	
	public static void stampaRetePetriController(RetePetri petri) {
		InputOutput.mostraMessaggio(Vista.NEWLINE);
		InputOutput.mostraMessaggio(petri.getName());
		for (AbstractRelazioneDiFlusso r : petri.getRelazioni()) {
			if (r instanceof RelazionePetri) {
				InputOutput.mostraMessaggio(stampaRP((RelazionePetri) r));
			} 
		} stampaMarcature(petri);
	}
	
	public static void stampaRetePetriPController(RetePetriP prior) {
		stampaRetePetriController(prior);
		stampaPriorità(prior);
	}
	
}
