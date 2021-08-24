package MainProgram2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "relazioni"
})
public class Rete extends AbstractRete implements IRelazioneDiFlusso {

	    private final static String MESS_NOME = "Inserisci il nome della rete da aggiungere: ";
	    private static final String INSERIMENTO_RELAZIONI = "Vuoi inserire un'altra relazione?";
		private static final String POSTOTRANS_TRANSPOSTO = "Per aggiungere una coppia posto-transizione premere 'a'\n"
				+ "Per aggiungere una coppia transizione-posto premere 'b' : ";
		private static final String POSTO = "Inserisci un intero positivo per il posto: ";
		private static final String TRANSIZIONE = "Inserisci un intero positivo per la transizione: ";
		private static final String ERRORE_SCELTA_AB = "Inserisci solo i caratteri 'a' o 'b' : ";
		
		@XmlTransient
		int numPos, numTrans;
		
		@XmlElementWrapper(name= "relazioni")
		@XmlElement(name = "relazione", required = true, type=RelazioneDiFlusso.class)
		ArrayList<IRelazioneDiFlusso> relazioni;
		
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
			relazioni = (ArrayList<IRelazioneDiFlusso>) (ArrayList<?>) new ArrayList<RelazioneDiFlusso>();
		}
		
		public Rete(String name, ArrayList<RelazioneDiFlusso> relazioni) {
			this.name = name;
			this.relazioni = (ArrayList<IRelazioneDiFlusso>) (ArrayList<?>) relazioni;	
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
	        	relazioni = (ArrayList<IRelazioneDiFlusso>) (ArrayList<?>) new ArrayList<RelazioneDiFlusso>();
	        }
	        return (ArrayList<RelazioneDiFlusso>) (ArrayList<? extends IRelazioneDiFlusso>)this.relazioni;
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
			for(RelazioneDiFlusso r :  (ArrayList<RelazioneDiFlusso>) (ArrayList<? extends IRelazioneDiFlusso>)relazioni) {
				if(r.getPosizione()>max)
					max=r.getPosizione();
			}
			numPos=max;
			//System.out.println("numero posizioni" + max);
		}
		
		//ritorna il numero massimo delle transizioni 
		public void contaTransizioni() {
			int max=0;
			for(RelazioneDiFlusso r :  (ArrayList<RelazioneDiFlusso>) (ArrayList<? extends IRelazioneDiFlusso>)relazioni) {
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
					for(RelazioneDiFlusso r:  (ArrayList<RelazioneDiFlusso>) (ArrayList<? extends IRelazioneDiFlusso>)relazioni)
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
		
		@Override
		public Rete creaRete() {
			Rete r = new Rete();
			r.setName(LeggiInput.leggiStringa(MESS_NOME));
			do {
							
					char aOb = LeggiInput.leggiChar(POSTOTRANS_TRANSPOSTO);
							
					int posto;
					int transizione;
					
					RelazioneDiFlusso rf = null;
							
					do {
							if(aOb == 'a') {
								posto = LeggiInput.leggiInteroPositivo(POSTO);								
								transizione = LeggiInput.leggiInteroPositivo(TRANSIZIONE);
								rf = new RelazioneDiFlusso(posto, transizione, true);
								break;
							}
							
							else if(aOb == 'b') {
								transizione = LeggiInput.leggiInteroPositivo(TRANSIZIONE);
								posto = LeggiInput.leggiInteroPositivo(POSTO);
								rf = new RelazioneDiFlusso(posto, transizione, false);
								break;
							}
									
							else {
								aOb = LeggiInput.leggiChar(ERRORE_SCELTA_AB);
							}
							
				   } while(aOb != 'a' || aOb != 'b');
						
					if(!((Rete) r).controllaRelazione(rf)) {
						
					      ((Rete) r).aggiungiRelazione(rf);
					}
						
			} while(LeggiInput.yesOrNo(INSERIMENTO_RELAZIONI));
					
			((Rete) r).inizializzaRete();
			return (Rete) r;
		}

		@Override
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (RelazioneDiFlusso r :  (ArrayList<RelazioneDiFlusso>) (ArrayList<? extends IRelazioneDiFlusso>)relazioni) {
				System.out.println(r.toString());
			}
		}
		
		
}

