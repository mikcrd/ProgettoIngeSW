package MainProgram2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelazionePN", propOrder = {
    "marcatura",
    "peso",
    "relazione"
})
public class RelazionePN extends AbstractRelazioneDiFlusso{
   
		int marcatura;
		int peso;
		private RelazioneDiFlusso relazione;
		
		//valori di default
		public RelazionePN() {
			relazione = null;
			posizione = 0;
			transizione = 0;
			inOut = true;
			marcatura = 0;
			peso = 1;
		}
	
		public RelazionePN(RelazioneDiFlusso relazione, int peso) {
//			super();
			this.relazione = relazione;
			this.posizione = relazione.getPosizione();
			this.transizione = relazione.getTransizione();
			this.inOut = relazione.isInOut();
			this.peso = peso;
		}

/**
		@Override
		public int getPosizione() {
			return this.posizione;
		}

		@Override
		public int getTransizione() {
			return this.transizione;
		}

		@Override
		public boolean isInOut() {
			return this.inOut;
		}
**/
		public int getMarcatura() {
			return marcatura;
		}
	
		public void setMarcatura(int marcatura) {
			this.marcatura = marcatura;
		}
		
		public int getPeso() {
			return peso;
		}
		
		public void setPeso(int peso) {
			this.peso = peso;
		}
		
		public RelazionePN creaRelazione() {
			return null;
			
		}
		
		@Override
		public String toString() {
			return "RelazionePN ["/** + relazione **/+ ", peso=" + peso + "]";
		}

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
			RelazionePN other = (RelazionePN) obj;
			if (peso != other.peso)
				return false;
			return true;
		}
		
				
/**
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + marcatura;
			result = prime * result + peso;
//			result = prime * result + ((relazione == null) ? 0 : relazione.hashCode());
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
			RelazionePN other = (RelazionePN) obj;
			if (marcatura != other.marcatura)
				return false;
			if (peso != other.peso)
				return false;
		if (relazione == null) {
				if (other.relazione != null)
					return false;
			} else if (!relazione.equals(other.relazione))
				return false; 
			return true; 
		}
**/
		
}
