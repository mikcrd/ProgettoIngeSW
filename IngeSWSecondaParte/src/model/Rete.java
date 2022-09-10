package model;
import controller.*;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rete")
public class Rete extends AbstractRete {

	

	public Rete() {
		numPos = 0;
		numTrans = 0;
		name = null;
		relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
	}

	public Rete(ArchivioReti arch) {
		this.arch = arch;
	}

	public void aggiungiRelazione(RelazioneDiFlusso r) {
		this.getRelazioni().add(r);
	}

	public int getPos() {
		return numPos;
	}

	public int getTrans() {
		return numTrans;
	}

	// ritorna il numero massimo delle transizioni
	public void contaTransizioni() {
		int max = 0;
		for (AbstractRelazioneDiFlusso r : relazioni) {
			if (((RelazioneDiFlusso) r).getTransizione() > max)
				max = ((RelazioneDiFlusso) r).getTransizione();
		}
		numTrans = max;
	}

	// ritorna il numero massimo della posizione
	public void contaPosizioni() {
		int max = 0;
		for (AbstractRelazioneDiFlusso r : relazioni) {
			if (((RelazioneDiFlusso) r).getPosizione() > max)
				max = ((RelazioneDiFlusso) r).getPosizione();
		}
		numPos = max;
	}

	// stampa le matrici: debug
	private void stampaMatrice(int[][] m) {
		for (int i = 0; i < numPos; i++) {
			for (int j = 0; j < numTrans; j++) {
				System.out.printf("%d", m[i][j]);
			}
			System.out.println();
		}
	}

	// inizializza tutta la rete
	public void inizializzaRete() {
		contaPosizioni();
		contaTransizioni();
		in = new int[numPos][numTrans];
		out = new int[numPos][numTrans];
		for (int i = 0; i < numPos; i++) {
			for (int j = 0; j < numTrans; j++) {
				for (AbstractRelazioneDiFlusso r : relazioni) {
					if (r instanceof AbstractRelazioneDiFlusso) {
						if (((RelazioneDiFlusso) r).getPosizione() == i + 1
								&& ((RelazioneDiFlusso) r).getTransizione() == j + 1
								&& ((RelazioneDiFlusso) r).isInOut() == true)
							in[i][j] = 1;
						else if (((RelazioneDiFlusso) r).getPosizione() == i + 1
								&& ((RelazioneDiFlusso) r).getTransizione() == j + 1
								&& ((RelazioneDiFlusso) r).isInOut() == false)
							out[i][j] = 1;
					} else {
						System.out.println("Debug: in questa rete ci sono relazioniPN");
					}
				}
			}
		}
	}

	// aggiorna matrice di incidenza
	public int[][] matriceIncidenza() {
		inizializzaRete();
		inc = new int[numPos][numTrans];

		for (int i = 0; i < numPos; i++) {
			for (int j = 0; j < numTrans; j++) {
				inc[i][j] = out[i][j] - in[i][j];
			}
		}
		return inc;
	}

	// controlla che non ci siano posti volanti
	public boolean controlloRighe(int[][] m) {
		boolean neg;
		boolean pos;

		if (numPos == 0 || numTrans == 0) {
			return false;
		}

		for (int i = 0; i < numPos; i++) {
			pos = false;
			neg = false;
			for (int j = 0; j < numTrans; j++) {
				if (m[i][j] == 1)
					pos = true;
				else if (m[i][j] == -1)
					neg = true;
			}
			if (pos == false || neg == false)
				return false;
		}
		return true;
	}

	// controllo che non ci siano transizioni volanti
	public boolean controlloColonne(int[][] m) {
		boolean neg;
		boolean pos;

		if (numPos == 0 || numTrans == 0) {
			return false;
		}

		for (int i = 0; i < numTrans; i++) {
			pos = false;
			neg = false;
			for (int j = 0; j < numPos; j++) {
				if (m[j][i] == 1)
					pos = true;
				else if (m[j][i] == -1)
					neg = true;

			}
			if (pos == false || neg == false)
				return false;
		}
		return true;
	}

	@Override
	public boolean isCorrect() {
		if (controlloColonne(matriceIncidenza()) && controlloRighe(matriceIncidenza())) {
			Controller.messReteCorretta();
			return true;
		} else {
			Controller.messReteNonCorretta();
			return false;
		}
	}

	public boolean controllaRelazione(RelazioneDiFlusso rf) {
		if (getRelazioni().contains(rf)) {
			return true;
		} else
			return false;
	}

	/* @assignable in[], out[], name, relazioni;@ */
	@Override
	public Rete creaRete() {
		Rete r = new Rete(); // altrimenti controllaRelazione mi dà problemi...
		r.setName(Controller.nomeRete());
		do {
			char aOb = Controller.sceltaPostoTrans_TransPostoRete();
			RelazioneDiFlusso rf = new RelazioneDiFlusso();
	
			do {
				if (aOb == 'a') {
					rf = Controller.creaPosto_Trans();
					break;
				} else if (aOb == 'b') {
					rf = Controller.creaTrans_Posto();
					break;
				} else {
					aOb = Controller.messErroreCharRete();
				}
	
			} while (aOb != 'a' || aOb != 'b');
	
			if (!(r).controllaRelazione(rf)) {
				r.aggiungiRelazione(rf);
			} else {
				Controller.messErroreRelazioneGiaPresenteRete();
			}
	
		} while (Controller.termineInserimentoRelazioniRete());
	
		r.inizializzaRete();
		return r;
	}


	@Override
	public void visualizzaElencoParziale() {
		arch.visualizzaNomeReti();
	}

	@Override
	public boolean retiInArchivio() {
		if (arch.getArchivio() != null && !(arch.getArchivio().isEmpty()) && !arch.noRetiInArchivio()) {
			return true;
		}
		return false;
	}

	public boolean stessaTopologia(AbstractRete abs) {
		
		if (getClass() != abs.getClass())
			return false;
		
		Rete other = (Rete) abs;
		if (relazioni == null) {
			if (other.relazioni != null)
				return false;
		} else if (!(relazioni).equals(other.relazioni))
			return false;
		return true;
	}
	
	public void stampaRete() {
		Controller.stampaReteController(this);
	}

	// cambiato hashcode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((relazioni).hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Rete))
			return false;
		Rete other = (Rete) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (relazioni == null) {
			if (other.relazioni != null)
				return false;
		} else if (!(relazioni).equals(other.relazioni))
			return false;
		return true;
	}

	
}
