package MainProgram2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "Relazione", propOrder = {
		    "posizione",
		    "transizione"
	})
	public class RelazioneDiFlusso implements IRelazioneDiFlusso {
	
		@XmlElement(required = true)
        @XmlSchemaType(name = "positiveInteger")
		Integer posizione;
		
		@XmlElement(required = true)
        @XmlSchemaType(name = "positiveInteger")
		Integer transizione;
		
		@XmlAttribute(name = "posto-trans", required = true)
		boolean inOut;
		
		
		public RelazioneDiFlusso() {
					
		}
		
		public RelazioneDiFlusso(Integer posizione, Integer transizione) {
			inOut=false;
			this.posizione=posizione;
			this.transizione=transizione;
		}
		
		public RelazioneDiFlusso(Integer posizione, Integer transizione, boolean inOut) {
			this.inOut=inOut;
			this.posizione=posizione;
			this.transizione=transizione;
		}
		
		
		public int getPosizione() {
			return posizione;
		}

		public void setPosizione(int posizione) {
			this.posizione = posizione;
		}

		public int getTransizione() {
			return transizione;
		}

		public void setTransizione(int transizione) {
			this.transizione = transizione;
		}

		public boolean isInOut() {
			return inOut;
		}

		
		
		public String toString() {
			if (inOut)
				return "posto " + posizione + " transizione " + transizione;
			else 
				return  "transizione " + transizione + " posto " + posizione;
		}
	
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (inOut ? 1231 : 1237);
			result = prime * result + posizione;
			result = prime * result + transizione;
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
			RelazioneDiFlusso other = (RelazioneDiFlusso) obj;
			if (inOut != other.inOut)
				return false;
			if (posizione == null) {
				if (other.posizione != null)
					return false;
			} else if (!posizione.equals(other.posizione))
				return false;
			if (transizione == null) {
				if (other.transizione != null)
					return false;
			} else if (!transizione.equals(other.transizione))
				return false;
			return true;
		}
		

		
	}
