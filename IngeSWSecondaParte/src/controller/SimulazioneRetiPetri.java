package controller;

import model.AbstractRelazioneDiFlusso;
import model.AbstractRete;
import model.RelazionePetri;
import model.RetePetri;

public class SimulazioneRetiPetri extends AbstractSimulazione {
	
	public SimulazioneRetiPetri(AbstractRete rete) {
		super(rete);
	}
	
	@Override
	public int contaTransizioniAbilitate() {
		int contatore=0;
		for(AbstractRelazioneDiFlusso rel: rete.getRelazioni()) {
			if (rel.isInOut()==true && ((RelazionePetri)rel).getPeso()<=((RetePetri)rete).getMarcatura(rel.getPosizione()-1) ) {
				System.out.println("transizione abilitata:" + rel.getTransizione());
				//incrementa il contatore delle transizioni abilitate 
				contatore++;
				
			}
		}
		
		return contatore;
	}
	
	@Override
	public boolean[]  cercaTransizioniAbilitate() {
	
		boolean [] transAbilitate= new boolean [rete.getNumTrans()];
		for(AbstractRelazioneDiFlusso rel: rete.getRelazioni()) {
			if (rel.isInOut()==true && ((RelazionePetri)rel).getPeso()<=((RetePetri)rete).getMarcatura(rel.getPosizione()-1) ) {
				transAbilitate[rel.getPosizione()-1]=true;
			}
		}
		
		return transAbilitate;
	}

	
	
	
	

}
