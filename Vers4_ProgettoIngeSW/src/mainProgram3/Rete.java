package mainProgram3;

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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rete")
public class Rete extends AbstractRete  {

	    private final static String MESS_NOME = "Inserisci il nome della rete da aggiungere: ";
	    private static final String INSERIMENTO_RELAZIONI = "Vuoi inserire un'altra relazione?";
		private static final String POSTOTRANS_TRANSPOSTO = "Per aggiungere una coppia posto-transizione premere 'a'\n"
				+ "Per aggiungere una coppia transizione-posto premere 'b' : ";
		private static final String ERRORE_SCELTA_AB = "Inserisci solo i caratteri 'a' o 'b' : ";
		
		
		
//		ArrayList<IRelazioneDiFlusso> relazioni;
//	    String name;
		
		
	
		
		public Rete (){
			numPos=0;
			numTrans=0;
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
		}

		/**
		public Rete(String name, ArrayList<RelazioneDiFlusso> relazioni) {
			this.name = name;
			this.relazioni = (ArrayList<IRelazioneDiFlusso>) (ArrayList<?>) relazioni;	
		}**/

///////////////////////////////////////////////////		
/**
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ArrayList<IRelazioneDiFlusso> getRelazioni() {
	        if (relazioni == null) {
	        	relazioni = new ArrayList<IRelazioneDiFlusso>();
	        }
	        return this.relazioni;
	    }
**/		

		public void aggiungiRelazione(RelazioneDiFlusso r) {
			this.getRelazioni().add(r);		
		}
		
		public int getPos() {
			return numPos;
		}
		
		public int getTrans() {
			return numTrans;
		}
/////////////////////////////////////////////////////////		
		
		//ritorna il numero massimo delle transizioni 
		public void contaTransizioni() {
			int max=0;
			for(AbstractRelazioneDiFlusso r :  relazioni) {
				//if(r instanceof RelazioneDiFlusso) {
					if(((RelazioneDiFlusso)r).getTransizione()>max)
						max=((RelazioneDiFlusso)r).getTransizione();
				//} else {System.out.println("Debug: in questa rete ci sono relazioniPN");}
			}
			numTrans=max;
			//System.out.println("numero transizioni" + max);
		}
		
		//ritorna il numero massimo della posizione
		public void contaPosizioni() {
			int max=0;
			for(AbstractRelazioneDiFlusso r :  relazioni) {
				//if(r instanceof RelazioneDiFlusso) {
					if(((RelazioneDiFlusso)r).getPosizione()>max)
						max=((RelazioneDiFlusso)r).getPosizione();
				//} else {System.out.println("Debug: in questa rete ci sono relazioniPN");}
			}
			numPos=max;
			//System.out.println("numero posizioni" + max);
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
					for(AbstractRelazioneDiFlusso r: relazioni)
					{
						if(r instanceof AbstractRelazioneDiFlusso) {
							if(((RelazioneDiFlusso)r).getPosizione()==i+1 && ((RelazioneDiFlusso)r).getTransizione()==j+1 && ((RelazioneDiFlusso)r).isInOut()==true) 
								in[i][j]=1; 	
							else if (((RelazioneDiFlusso)r).getPosizione()==i+1 && ((RelazioneDiFlusso)r).getTransizione()==j+1 && ((RelazioneDiFlusso)r).isInOut()==false)
								out[i][j]=1;
						} else {System.out.println("Debug: in questa rete ci sono relazioniPN");}
					}
				}
			}
			//debug
			//stampaMatrice(in);
			//System.out.println();
			//stampaMatrice(out);

		}
		
		
		//aggiorna matrice di incidenza 
		public int [][] matriceIncidenza(){
			inizializzaRete();
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
			
			if(numPos == 0|| numTrans == 0) {
				return false;
			}
			
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
			
			if(numPos == 0|| numTrans == 0) {
				return false;
			}
			
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
		//	Rete r = new Rete();
			this.setName(LeggiInput.leggiStringaNonVuota(MESS_NOME));
			do {
					char aOb = LeggiInput.leggiChar(POSTOTRANS_TRANSPOSTO);
					RelazioneDiFlusso rf = new RelazioneDiFlusso();
							
					do {
							if(aOb == 'a') {
								rf.creaPosto_Trans();
								break;
							}
							else if(aOb == 'b') {
								rf.creaTrans_Posto();
								break;
							}		
							else {
								aOb = LeggiInput.leggiChar(ERRORE_SCELTA_AB);
							}
							
				   } while(aOb != 'a' || aOb != 'b');
						
					if(!(this).controllaRelazione(rf)) {
					      this.aggiungiRelazione(rf);
					}
						
			} while(LeggiInput.yesOrNo(INSERIMENTO_RELAZIONI));
					
			this.inizializzaRete();
			return this;
		}

		@Override
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (AbstractRelazioneDiFlusso r :  this.relazioni) {
				if(r instanceof RelazioneDiFlusso) {
				   System.out.println(((RelazioneDiFlusso)r).toString());
				} else {System.out.println("Debug: in questa rete ci sono relazioniPN");}
			}
		}

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
			
			if (getClass() != obj.getClass())
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

