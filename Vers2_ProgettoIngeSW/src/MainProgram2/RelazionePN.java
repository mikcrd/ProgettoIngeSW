package MainProgram2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Relazione", propOrder = {
	    "marcatura",
	    "peso"
})
public class RelazionePN implements IRelazioneDiFlusso{

		RelazioneDiFlusso relazione;
		
		@XmlElement(required = true)
	    @XmlSchemaType(name = "Integer")
		int marcatura;
		
		@XmlElement(required = true)
	    @XmlSchemaType(name = "positiveInteger")
		int peso;
		
		//valori di default
		public RelazionePN() {
			relazione = null;
			marcatura = 0;
			peso = 1;
		}
	
		public RelazionePN(RelazioneDiFlusso relazione, int marcatura, int peso) {
			super();
			this.relazione = relazione;
			this.marcatura = marcatura;
			this.peso = peso;
		}
	
		
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
	 	
		
		
		
		@Override
		public String toString() {
			return "RelazionePN [" + relazione + ", marcatura=" + marcatura + ", peso=" + peso + "]";
		}
	
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + marcatura;
			result = prime * result + peso;
			result = prime * result + ((relazione == null) ? 0 : relazione.hashCode());
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

}
