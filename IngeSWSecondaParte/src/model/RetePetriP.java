package model;
import controller.*;

import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetePetriP", propOrder = {
		"priorit\u00e0",
})

//@XmlTransient

public class RetePetriP extends RetePetri implements ICercaTopologiaBase {

		
		/*@invariant priorit� != null;
		  @(\forall int i; 0<=i<numTrans; priorit�[i]>0);
		  @*/
		
		@XmlElementWrapper(name="priorit\u00e0")
		@XmlElement(name="priorit\u00e0", required = true)
		
		/*@spec_public@*/int [] priorit�;
		
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
			this.priorit� = new int[numTrans];
		}
		
		public RetePetriP(RetePetri p, ArchivioReti arch) {
			this.name = null;
			this.relazioni = p.getRelazioni();
			this.marcature = p.getMarcature();
			this.priorit� = new int[numTrans];
			this.arch=arch;
		}
		
		public int getPriorita(int i) {
			return priorit�[i];
		}
		
		public int[] getPriorita() {
			return priorit�;
		}
		
		public void inizializzaPriorit�(int num) {
			priorit�= new int [num];
		}
		
		public void setPriorit�(int pri, int i) {
			priorit�[i]=pri;
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
				priorit�[i]=Controller.messInserimentoPriorit�(++j);
			}
		}
		
		public void inizializzaRetePetriP(RetePetri p) {
			this.numTrans = p.getTrans();
			priorit� = new int[numTrans];
        	this.aggiungiRelazioni(p);
        	this.aggiungiMarcature(p);
			this.aggiungiPriorit�(numTrans);
		}
	
		/*@assignable numPos, numTrans, name, relazioni, marcature, priorit�;@*/
		public RetePetriP creaRete() {
			if(arch.noRetiPNInArchivio()) {
	       			Controller.messErroreAggiungiPrimaUnaReteP();
	       			return null;
			}
			else {
					RetePetri p;
					arch.visualizzaSoloRetiPNArchivio();
					String nome = Controller.scegliRetePetriPerCostruireRetePetriP();
					p = (RetePetri) arch.trovaRete(nome);
					if(p == null) Controller.messErroreReteNonTrovata();
					else {
						this.setName(Controller.nomeRetePetriP());
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


		
		@Override
		public boolean stessaTopologia(AbstractRete abs) {
			
			if (abs == null)
				return false;
			if (getClass() != abs.getClass())
				return false;
			
			RetePetriP other = (RetePetriP) abs;
			if (!Arrays.equals(priorit�, other.priorit�))
				return false;
			if (!Arrays.equals(marcature, other.marcature))
				return false;
			if (relazioni == null) {
				if (other.relazioni != null)
					return false;
			} else if (!relazioni.equals(other.relazioni)) {
				return false;
			    }
			return true;
		}
		
		public void stampaRete() {
			Controller.stampaRetePetriPController(this);
		}

		@Override
		public boolean isCorrect() {
			for(int priorit� : getPriorita()) {
				if(priorit� <= 0) return false; break;
			}
			return true;
		}

		
		@Override
		public List<AbstractRelazioneDiFlusso> getTopologiaSottostante() {
			return this.getRelazioni();
		}

		@Override
		public boolean controlloPerSalvataggioDaFile(AbstractRete rete) {
			
			if(rete instanceof RetePetri && !(rete instanceof RetePetriP) && rete.getRelazioni().equals(this.getTopologiaSottostante())
					&& Arrays.equals(((RetePetri) rete).getMarcature(), this.getMarcature()))
				return true;
			
			//Controller.messAssenzaRetePetriSuCuiCostruireRetePetriP();
			return false;
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
