package MainProgram2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rete", propOrder = {
    "relazioni"
})
public class Rete extends AbstractRete {

		
		@XmlTransient
		int numPos, numTrans;
		
		@XmlElement(name = "relazione", required = true)
		ArrayList<RelazioneDiFlusso> relazioni;
		
		@XmlAttribute(name = "name", required = true)
	    String name;
		
		@XmlTransient
		int [][] in;
		@XmlTransient
		int [][]out;
		@XmlTransient
		int [][] inc;
	
		
		public Rete (){
			numPos=0;
			numTrans=0;
			name=null;
			relazioni = new ArrayList<RelazioneDiFlusso>();
		}
		
		public Rete(String name, ArrayList<IRelazioneDiFlusso> relazioni) {
			super(name, relazioni);
			
		}

///////////////////////////////////////////////////		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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
/////////////////////////////////////////////////////////		
		
		//aggiorna matrice di incidenza 
		public int [][] matriceIncidenza(){
			inc = new int [numPos][numTrans];
			
			for (int i=0; i<numPos; i++) {
				for(int j=0; j<numTrans; j++) {
					inc[i][j]=out[i][j] - in[i][j]; 
				}
			}
			//stampaMatrice(inc);
			return inc;
		}
		
		//controlla che non ci siano posti volanti
		public boolean controlloRighe(int [][] m) {
			boolean neg;
			boolean pos;
			
			for (int i=0; i<numPos; i++)
			{
				pos=false;
				neg=false;
				for(int j=0; j<numTrans; j++)
				{
					if(m[i][j]==1)
						pos=true;
					else if (m[i][j]==-1)
						neg=true; 
				}
				if(pos==false || neg==false)
					return false;
			}
			return true;
		}
		
		//controllo che non ci siano transizioni volanti 
		public boolean controlloColonne(int [][] m) {
			boolean neg;
			boolean pos;
			
			for (int i=0; i<numTrans; i++)
			{
				pos=false;
				neg=false;
				for(int j=0; j<numPos; j++)
				{
					if(m[j][i]==1)
						pos=true;
					else if (m[j][i]==-1)
						neg=true; 
								
				}
				if(pos==false || neg==false)
					return false;
			}
			return true;
		}
		
		
		//ritorna il numero massimo della posizione
		public void contaPosizioni() {
			int max=0;
			for(RelazioneDiFlusso r : relazioni) {
				if(r.getPosizione()>max)
					max=r.getPosizione();
			}
			numPos=max;
			//System.out.println("numero posizioni" + max);
		}
		
		//ritorna il numero massimo delle transizioni 
		public void contaTransizioni() {
			int max=0;
			for(RelazioneDiFlusso r : relazioni) {
				if(r.getTransizione()>max)
					max=r.getTransizione();
			}
			numTrans=max;
			//System.out.println("numero transizioni" + max);
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
						if(r.getPosizione()==i+1 && r.getTransizione()==j+1 && r.isInOut()==true) 
							in[i][j]=1; 	
						else if (r.getPosizione()==i+1 && r.getTransizione()==j+1 && r.isInOut()==false)
							out[i][j]=1;
					}
				}
			}
			//debug
			//stampaMatrice(in);
			//System.out.println();
			//stampaMatrice(out);

		}
		

		@Override
		public boolean isCorrect() {
			if(controlloColonne(matriceIncidenza()) && controlloRighe(matriceIncidenza())){
				System.out.println("La rete è corretta");
				return true;
			}
			else {
				System.out.println("La rete non è corretta");
				return false;
			}
		}

		public boolean controllaRelazione(RelazioneDiFlusso rf) {
			if(getRelazioni().contains(rf)) {
				System.out.println("Relazione di flusso già presente");
				return true;
			}
			else return false;
		}
}
