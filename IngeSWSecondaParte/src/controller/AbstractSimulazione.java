package controller;
import model.*;
import view.*;



public abstract class AbstractSimulazione {
	
	AbstractRete rete;
	int [] marcature;
	
	public AbstractSimulazione(AbstractRete rete) {
		this.rete=rete;
		this.marcature=((RetePetri) rete).getMarcature();
	}
	


	public void simulaRete() {
		boolean risposta;
		int numTransAbil;
		rete.contaTransizioni();
		rete.contaPosizioni();
		boolean [] abilitate = new boolean[((RetePetri) rete).getTrans()];
		//stampo le marcature prima dello scatto della transizione
		Controller.stampaMarcature((RetePetri) rete);
		do {	
			numTransAbil=this.contaTransizioniAbilitate();
			abilitate=this.cercaTransizioniAbilitate();
			if (numTransAbil==1) 
			{
				
				//cerco con il ciclo nel vettore di booleani la transizione abilitata e la faccio scattare
				for(int i=0; i<((RetePetri) rete).getTrans(); i++) {
					if (abilitate[i]) {
						//scatta transizione
						scattaTransizione(++i);
						InputOutput.mostraMessaggio(Vista.SIMULA_MARCATURA_DOPO_SCATTO);
						Controller.stampaMarcature((RetePetri) rete);
						break;
					}
				}
			
			}else if(numTransAbil>1)
			{
				//utente sceglie quale delle transizioni abilitate fare scattare
				int transUtente=InputOutput.leggiInteroPositivo(Vista.SIMULA_TRANSIZIONE_DA_FAR_SCATTARE);
				if(abilitate[transUtente-1]) {
					scattaTransizione(transUtente);
					Controller.stampaMarcature((RetePetri) rete);
				}
			}else if(numTransAbil==0) {
				InputOutput.mostraMessaggio(Vista.SIMULA_BLOCCO_CRITICO_RAGGIUNTO);
				break;
			}
			//stampa rete dopo scatto transizione 
			
			risposta=InputOutput.yesOrNo(Vista.SIMULA_VUOI_PROSEGUIRE);
			
		}while (risposta);
		InputOutput.mostraMessaggio(Vista.SIMULA_TERMINE_SIMULAZIONE);
	}
	
	public boolean [] trovaPostiPredecessori(int trans) {
		boolean[] pred = new boolean[rete.getNumTrans()];
		for(model.AbstractRelazioneDiFlusso relazione : rete.getRelazioni()) {
			for(int i=0; i<rete.getNumPos(); i++) {
				if(relazione.getTransizione()==trans && relazione.isInOut()==true) {
					pred[i] = true;
				}
			}
		}
		return pred;	
	}
	
	public boolean [] trovaPostiSucessori(int trans) {
		boolean[] succ = new boolean[rete.getNumPos()];
		for(model.AbstractRelazioneDiFlusso relazione : rete.getRelazioni()) {
			for(int i=0; i<rete.getNumPos(); i++) {
				if(relazione.getTransizione()==trans && relazione.isInOut()==false) {
					succ[i]=true;
				}
			}
		}
		return succ;	
	}
	
	public void scattaTransizione(int trans) {
		boolean[] pred = new boolean[rete.getNumPos()];
		boolean[] succ = new boolean[rete.getNumPos()];
		pred=this.trovaPostiPredecessori(trans);
		succ=this.trovaPostiSucessori(trans);
		for(int i=0; i<rete.getNumPos(); i++){
			if (pred[i]) {
				int j=i;
				for(model.AbstractRelazioneDiFlusso rel: rete.getRelazioni()) {
					if (rel.isInOut() && rel.getPosizione()==j+1 && rel.getTransizione()==trans) {
						marcature[i]= marcature[i] - ((RelazionePetri)rel).getPeso();
					}
				}
			}if (succ[i]) {
				int j=i;
				for(model.AbstractRelazioneDiFlusso rel: rete.getRelazioni()) {
					if (!rel.isInOut() && rel.getPosizione()==j+1 && rel.getTransizione()==trans) {
						marcature[i]= marcature[i] + ((RelazionePetri)rel).getPeso();
					}
				}
			}
		
		}
		
	}
	
	public abstract int contaTransizioniAbilitate();
	
	public abstract boolean[]  cercaTransizioniAbilitate();
}
