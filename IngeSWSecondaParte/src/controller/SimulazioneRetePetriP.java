package controller;

import model.AbstractRelazioneDiFlusso;
import model.AbstractRete;
import model.RelazionePetri;
import model.RetePetriP;

public class SimulazioneRetePetriP extends AbstractSimulazione{
	
	public SimulazioneRetePetriP(AbstractRete rete) {
		super(rete);
	}

	public int cercaPrioritaMax() {
		int i;
		int max=0;
		for (i=0; i<rete.getNumTrans(); i++) {
			if(((RetePetriP)rete).getPriorita(i)>max) {
				max=((RetePetriP) rete).getPriorita(i);
			}
		}
		return max;
	}
	
	@Override
	public int contaTransizioniAbilitate() {
		int contatore=0;
		int prior= this.cercaPrioritaMax();
		do {
			for(AbstractRelazioneDiFlusso rel: rete.getRelazioni()) {
				if (rel.isInOut()==true && ((RelazionePetri)rel).getPeso()<=((RetePetriP)rete).getMarcatura(rel.getPosizione()-1) && ((RetePetriP)rete).getPriorita(rel.getTransizione()-1)==prior ) {
					System.out.println("transizione abilitata:" + rel.getTransizione());
					//incrementa il contatore delle transizioni abilitate 
					contatore++;
				}
			}
			prior=prior-1;
		}while(contatore==0 && prior>0);
		return contatore;
	}
	
	
	
	@Override
	public boolean[]  cercaTransizioniAbilitate() {
		int priorita=this.cercaPrioritaMax();
		boolean [] transAbilitate= new boolean [rete.getNumTrans()];
		boolean ok=false;
		do {
			for(AbstractRelazioneDiFlusso rel: rete.getRelazioni()) {
				if (rel.isInOut()==true && ((RelazionePetri)rel).getPeso()<= ((RelazionePetri)rel).getPosizione()-1 &&((RelazionePetri)rel).getTransizione()-1==priorita ) {
					transAbilitate[rel.getPosizione()-1]=true;
					ok=true;
				}
			}
			priorita=priorita-1;
		}while(!ok && priorita >0);
		
		return transAbilitate;
	}

}
