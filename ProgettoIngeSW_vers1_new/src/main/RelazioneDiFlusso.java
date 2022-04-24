package main;

import javax.xml.bind.annotation.*;

    @XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "Relazione", propOrder = {
	    "posizione",
	    "transizione"
	})
	public class RelazioneDiFlusso {
    	
    	@XmlElement(required = true)
        @XmlSchemaType(name = "positiveInteger")
		Integer posizione;
    	@XmlElement(required = true)
        @XmlSchemaType(name = "positiveInteger")
		Integer transizione;
		//se questo valore è vero relazione pos-trans altrimenti trans-pos
    	
    	@XmlAttribute(name = "posto-trans", required = true)
    	boolean inOut;
		
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
		
		public RelazioneDiFlusso() {
			
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
		
		public boolean getInOut() {
			return inOut;
		}
		public void setTransizione(int transizione) {
			this.transizione = transizione;
		}
		
		public void setInOut(boolean b) {
			this.inOut=b;
		}
		
		public String toString() {
			if (inOut)
				return "posizione " + posizione + " transizione " + transizione;
			else 
				return  "transizione " + transizione + " posizione " + posizione;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (inOut ? 1231 : 1237);
			result = prime * result + ((posizione == null) ? 0 : posizione.hashCode());
			result = prime * result + ((transizione == null) ? 0 : transizione.hashCode());
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
