package MainProgram;

import java.awt.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rete", propOrder = {
    "relazioni"
})
public class Rete {
	@XmlTransient
	int numPos, numTrans;
	@XmlElement(name = "relazione", required = true)
	ArrayList<RelazioneDiFlusso> relazioni;
	@XmlTransient
	int [][] in;
	
	public Rete (){
		numPos=0;
		numTrans=0;
		relazioni = new ArrayList<RelazioneDiFlusso>();
	}
	
	public ArrayList<RelazioneDiFlusso> getRelazioni() {
        if (relazioni == null) {
        	relazioni = new ArrayList<RelazioneDiFlusso>();
        }
        return this.relazioni;
    }
	
	public void aggiungiRelazione(RelazioneDiFlusso r) {
		this.getRelazioni().add(r);
	}
	
	public void contaTransizioni() {
		int max=0;
		for(RelazioneDiFlusso r : relazioni) {
			if(r.getTransizione()>max)
				max=r.getTransizione();
		}
		numTrans=max;
		System.out.println("numero transizioni" + max);
	}
	
	public void contaPosizioni() {
		int max=0;
		for(RelazioneDiFlusso r : relazioni) {
			if(r.getPosizione()>max)
				max=r.getPosizione();
		}
		numPos=max;
		System.out.println("numero posizioni" + max);
	}
	
	public void inizializzaRete() {
		contaPosizioni();
		contaTransizioni();
		in = new int [numPos][numTrans];
		
		for (int i=0; i<numPos; i++)
		{
			for(int j=0; j<numTrans; j++)
			{
				for(RelazioneDiFlusso r: relazioni)
				{
					if(r.getPosizione()==i+1 && r.getTransizione()==j+1 && r.getInOut())
						in[i][j]=1; 
				}
				
				System.out.printf("%d", in[i][j]);
			}
			System.out.println();
		}
	}
	
	
	
	
	public void stampaRete() {
		for (RelazioneDiFlusso r : relazioni) {
			System.out.println(r.toString());
		}
	}
}
