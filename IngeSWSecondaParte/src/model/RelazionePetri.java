package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelazionePN", propOrder = {
   
    "peso",
})
public class RelazionePetri extends AbstractRelazioneDiFlusso{
   
		private static final String DESCRIZIONE = "[%s, peso= %d]"; 
	
		int peso;
		
		//valori di default
		public RelazionePetri() {
		
			peso = 1;
		}
	
		public RelazionePetri(RelazioneDiFlusso relazione, int peso) {

			this.posizione = relazione.getPosizione();
			this.transizione = relazione.getTransizione();
			this.inOut = relazione.isInOut();
			this.peso = peso;
		}

		public RelazionePetri(int posizione, int transizione, boolean inOut, int peso) {

			this.posizione = posizione;
			this.transizione = transizione;
			this.inOut = inOut;
			this.peso = peso;
		}
		
		public int getPeso() {
			return peso;
		}
		
		public void setPeso(int peso) {
			this.peso = peso;
		}
		
		public RelazionePetri creaRelazione() {
			return null;
			
		}

		@Override
		public AbstractRelazioneDiFlusso getRelazioneSottostante() {
			RelazioneDiFlusso relF = new RelazioneDiFlusso(getPosizione(), getTransizione(), isInOut());
			return relF;
		}

		/*
		public String toString() {
			return String.format(DESCRIZIONE, super.toString(), getPeso());
		}*/
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + peso;
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
			RelazionePetri other = (RelazionePetri) obj;
			if (peso != other.peso)
				return false;
			return true;
		}
		
}
