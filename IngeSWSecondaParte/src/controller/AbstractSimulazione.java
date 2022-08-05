package controller;
import model.*;

import javax.swing.plaf.nimbus.AbstractRegionPainter;

import utility.LeggiInput;

public abstract class AbstractSimulazione {
	
	AbstractRete rete;
	int [] marcature=((RetePetri) rete).getMarcature();
	
	public AbstractSimulazione(AbstractRete rete) {
		this.rete=rete;
	}
	


	public void simulaRete() {
		boolean risposta;
		int numTransAbil;
		rete.contaTransizioni();
		rete.contaPosizioni();
		boolean [] abilitate = new boolean[((Rete) rete).getTrans()];
		//stampo le marcature prima dello scatto della transizione
		((RetePetri) rete).stampaMarcature();
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
						System.out.println("dopo lo scatto della transizione la marcatura e':");
						((RetePetri) rete).stampaMarcature();
						break;
					}
				}
			
			}else if(numTransAbil>1)
			{
				//utente sceglie quale delle transizioni abilitate fare scattare
				int transUtente=LeggiInput.leggiInteroPositivo("inserire la transizione che si vuole fare scattare");
				if(abilitate[transUtente-1]) {
					scattaTransizione(transUtente);
					((RetePetri) rete).stampaMarcature();
				}
			}else if(numTransAbil==0) {
				System.out.println("Nessuna transizione abilitata, blocco critico raggiunto");
				break;
			}
			//stampa rete dopo scatto transizione 
			
			risposta=LeggiInput.yesOrNo("vuoi proseguire con la simulazione?");
			
		}while (risposta);
		System.out.println("Simulazione terminata");
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
