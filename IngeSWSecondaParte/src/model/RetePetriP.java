package model;
import controller.*;
import view.InputOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetePetriP", propOrder = {
		"priorit\u00e0",
})

//@XmlTransient

public class RetePetriP extends RetePetri implements ICercaTopologiaBase {

		private static final String PRIORITA ="inserire il valore di priorità";
		private static final String SCEGLI_RETE_PETRI = "Scegli una delle reti di Petri nell'archivio: ";
		private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
		private static final String MESS_NOME = "Inserisci il nome della rete di Petri con priorità da aggiungere: ";
		private static final String MESS_RETEPETRINOTOPOLOGIA = "La rete di Petri con priorità che si "
				+ "vuole salvare non ha una rete di Petri con la stessa topologia a cui appoggiarsi. "
				+ "\nCrea prima una rete di Petri con la stessa topologia";
		private static final String NO_RETI_PETRI = "Attenzione: non ci sono reti di Petri nell'archivio \nAggiungere una rete prima di continuare";

		
		/*@invariant priorità != null;
		  @(\forall int i; 0<=i<numTrans; priorità[i]>0);
		  @*/
		
		@XmlElementWrapper(name="priorit\u00e0")
		@XmlElement(name="priorit\u00e0", required = true)
		
		/*@spec_public@*/int [] priorità;
		
		public RetePetriP(ArchivioReti arch) { //dependency injection
			this.arch = arch;	 
		}
		
		public RetePetriP() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();		
			marcature = new int[numPos];
			priorità = new int[numTrans];
		}
		
		public RetePetriP(RetePetri p) {
			this.name = null;
			this.relazioni = p.getRelazioni();
			this.marcature = p.getMarcature();
			this.priorità = new int[numTrans];;
		}
		
		public int getPriorita(int i) {
			return priorità[i];
		}
		
		public int[] getPriorita() {
			return priorità;
		}
		
		public void inizializzaPriorità(int num) {
			priorità= new int [num];
		}
		
		public void setPriorità(int pri, int i) {
			priorità[i]=pri;
		}
		
		public void aggiungiRelazioni(RetePetri p) {
			for(AbstractRelazioneDiFlusso rel : p.getRelazioni()) {
				if(rel instanceof RelazionePetri) {
				     RelazionePetri pnp = new RelazionePetri(rel.getPosizione(), rel.getTransizione(), rel.isInOut(), ((RelazionePetri) rel).getPeso());
				     this.aggiungiRelazione(pnp);
				}
			}
		}
		
		public void aggiungiMarcature(RetePetri p) {
			this.marcature = p.getMarcature();
		}
		
		public void aggiungiPriorità(int nt) {
			for(int i=0; i<nt; i++) {
				int j=i;
				priorità[i]=InputOutput.leggiInteroPositivo(PRIORITA + " per la transizione " + ++j + ": ");
			}
		}
		
		public void inizializzaRetePetriP(RetePetri p) {
			this.numTrans = p.getTrans();
			priorità = new int[numTrans];
        	this.aggiungiRelazioni(p);
        	this.aggiungiMarcature(p);
			this.aggiungiPriorità(numTrans);
		}
	
		/*@assignable numPos, numTrans, name, relazioni, marcature, priorità;@*/
		public RetePetriP creaRete() {
			if(arch.noRetiPNInArchivio()) {
	       			System.out.println(NO_RETI_PETRI);
	       			return null;
			}
			else {
				// ...
				
				
					RetePetri p;
					arch.visualizzaSoloRetiPNArchivio();
					String nome = InputOutput.leggiStringaNonVuota(SCEGLI_RETE_PETRI);
					p = (RetePetri) arch.trovaRete(nome);
					if(p == null) System.out.println(MESS_NON_TROVATA);
					else {
						this.setName(InputOutput.leggiStringaNonVuota(MESS_NOME));
						p.contaTransizioni();
						this.inizializzaRetePetriP(p);
					}
			}
			return this;
		}
	
		@Override
		public void visualizzaElencoParziale() {
			arch.visualizzaNomeRetiPNP();
		}

		@Override
		public boolean retiInArchivio() {
			if(arch.getArchivio() != null && !(arch.getArchivio().isEmpty()) && !(arch.noRetiPNPInArchivio())) {
				return true;
			}
			return false;
		}

		public void stampaPriorità() {
			System.out.println("Priorità: ");
			for(int i=0; i<priorità.length; i++) {
				int j=i;
				System.out.println("Transizione " + ++j + " priorità " + priorità[i]);
			}
		}
		
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (AbstractRelazioneDiFlusso r : this.relazioni) {
				if(r instanceof RelazionePetri) {
				   System.out.println(((RelazionePetri)r).toString());
				}
			}
			this.stampaMarcature();
			stampaPriorità();
		}
/*		
		public int cercaPrioritaMax() {
			int i;
			int max=0;
			for (i=0; i<numTrans; i++) {
				if(this.getPriorita(i)>max) {
					max=this.getPriorita(i);
				}
			}
			return max;
		}
		
		@Override
		public int contaTransizioniAbilitate() {
			int contatore=0;
			int prior= this.cercaPrioritaMax();
			do {
				for(AbstractRelazioneDiFlusso rel: this.relazioni) {
					if (rel.inOut==true && ((RelazionePetri)rel).getPeso()<=this.getMarcatura(rel.getPosizione()-1) && this.getPriorita(rel.getTransizione()-1)==prior ) {
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
			boolean [] transAbilitate= new boolean [numTrans];
			boolean ok=false;
			do {
				for(AbstractRelazioneDiFlusso rel: this.relazioni) {
					if (rel.inOut==true && ((RelazionePetri)rel).getPeso()<=this.getMarcatura(rel.getPosizione()-1) && this.getPriorita(rel.getTransizione()-1)==priorita ) {
						transAbilitate[rel.getPosizione()-1]=true;
						ok=true;
					}
				}
				priorita=priorita-1;
			}while(!ok && priorita >0);
			
			return transAbilitate;
		}
*/
		@Override
		public boolean isCorrect() {
			for(int priorità : getPriorita()) {
				if(priorità <= 0) return false; break;
			}
			return true;
		}

		
		@Override
		public ArrayList<AbstractRelazioneDiFlusso> getTopologiaSottostante() {
			return getRelazioni();
		}

		@Override
		public boolean controlloPerSalvataggioDaFile(AbstractRete rete) {
			
			if(rete instanceof RetePetri&& !(rete instanceof RetePetriP) && rete.getRelazioni().equals(getTopologiaSottostante())
					&& ((RetePetri) rete).getMarcature().equals(getMarcature())) {
				return true;
			}
			System.out.println(MESS_RETEPETRINOTOPOLOGIA);
			return false;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + Arrays.hashCode(priorità);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RetePetriP other = (RetePetriP) obj;
			if (!Arrays.equals(priorità, other.priorità))
				return false;
			return true;
		}
	
}
