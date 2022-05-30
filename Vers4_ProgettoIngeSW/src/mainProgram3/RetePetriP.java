package mainProgram3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetePetriP", propOrder = {
		"priorit\u00e0",
})
public class RetePetriP extends RetePetri{

		private static final String PRIORITA ="inserire il valore di priorit�";
		private static final String SCEGLI_RETE_PETRI = "Scegli una delle reti di Petri nell'archivio: ";
		private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
		private static final String MESS_NOME = "Inserisci il nome della rete di Petri con priorit� da aggiungere: ";
		
		
		@XmlElementWrapper(name="priorit\u00e0")
		@XmlElement(name="priorit\u00e0", required = true)
		
		int [] priorit�;
		
		public RetePetriP(ArchivioReti arch) { //dependency injection
			this.arch = arch;	 
		}
		
		public RetePetriP() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();		
			marcature = new int[numPos];
			priorit� = new int[numTrans];
		}
		
		public RetePetriP(RetePetri p) {
			this.name = null;
			this.relazioni = p.getRelazioni();
			this.marcature = p.getMarcature();
			this.priorit� = new int[numTrans];;
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
		
		public void aggiungiPriorit�(int nt) {
			for(int i=0; i<nt; i++) {
				int j=i;
				priorit�[i]=LeggiInput.leggiInteroPositivo(PRIORITA + " per la transizione " + ++j + ": ");
			}
		}
		
		public void inizializzaRetePetriP(RetePetri p) {
			this.numTrans = p.getTrans();
			priorit� = new int[numTrans];
        	this.aggiungiRelazioni(p);
        	this.aggiungiMarcature(p);
			this.aggiungiPriorit�(numTrans);
		}
	
		public RetePetriP creaRete() {
			RetePetri p;
			arch.visualizzaSoloRetiPNArchivio();
			String nome = LeggiInput.leggiStringaNonVuota(SCEGLI_RETE_PETRI);
			p = (RetePetri) arch.trovaRete(nome);
			if(p == null) System.out.println(MESS_NON_TROVATA);
			else {
				this.setName(LeggiInput.leggiStringaNonVuota(MESS_NOME));
				p.contaTransizioni();
				this.inizializzaRetePetriP(p);
			}
			return this;
		}
	
		public void stampaPriorit�() {
			System.out.println("Priorit�: ");
			for(int i=0; i<priorit�.length; i++) {
				int j=i;
				System.out.println("Transizione " + ++j + " priorit� " + priorit�[i]);
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
			stampaPriorit�();
		}

		@Override
		public boolean isCorrect() {
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + Arrays.hashCode(priorit�);
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
			if (!Arrays.equals(priorit�, other.priorit�))
				return false;
			return true;
		}

		

		
}
