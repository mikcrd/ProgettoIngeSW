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
	
	@XmlAttribute(name = "name", required = true)
    String name;
	
	@XmlTransient
	int [][] in;
	int [][]out;
	
	//costruttore
	public Rete (){
		numPos=0;
		numTrans=0;
		name=null;
		relazioni = new ArrayList<RelazioneDiFlusso>();
	}
	
	public ArrayList<RelazioneDiFlusso> getRelazioni() {
        if (relazioni == null) {
        	relazioni = new ArrayList<RelazioneDiFlusso>();
        }
        return this.relazioni;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String nome) {
		this.name = nome;
	}
	
	
	//aggiunge relazioni di flusso 
	public void aggiungiRelazione(RelazioneDiFlusso r) {
		this.getRelazioni().add(r);
	}
	
	//ritorna il numero massimo delle transizioni 
	public void contaTransizioni() {
		int max=0;
		for(RelazioneDiFlusso r : relazioni) {
			if(r.getTransizione()>max)
				max=r.getTransizione();
		}
		numTrans=max;
		System.out.println("numero transizioni" + max);
	}
	
	//ritorna il numero massimo della posizione
	public void contaPosizioni() {
		int max=0;
		for(RelazioneDiFlusso r : relazioni) {
			if(r.getPosizione()>max)
				max=r.getPosizione();
		}
		numPos=max;
		System.out.println("numero posizioni" + max);
	}
	
	//stampa le matrici: debug
	private void stampaMatrice(int [][]m) {
		for (int i=0; i<numPos; i++) {
			for(int j=0; j<numTrans; j++) {
				System.out.printf("%d", m[i][j]);
			}
			System.out.println();
		}
	}
	
	//inizializza tutta la rete 
	public void inizializzaRete() {
		contaPosizioni();
		contaTransizioni();
		in = new int [numPos][numTrans];
		out = new int [numPos][numTrans];
		for (int i=0; i<numPos; i++)
		{
			for(int j=0; j<numTrans; j++)
			{
				for(RelazioneDiFlusso r: relazioni)
				{
					if(r.getPosizione()==i+1 && r.getTransizione()==j+1 && r.getInOut()==true) 
						in[i][j]=1; 	
					else if (r.getPosizione()==i+1 && r.getTransizione()==j+1 && r.getInOut()==false)
						out[i][j]=1;
				}
			}
		}
		//debug
		stampaMatrice(in);
		System.out.println();
		stampaMatrice(out);

	}
	
	
	public void stampaRete() {
		System.out.println(this.name);
		for (RelazioneDiFlusso r : relazioni) {
			System.out.println(r.toString());
		}
	}
}
