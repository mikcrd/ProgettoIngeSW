package MainProgram;

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
		public String toString() {
			if (inOut)
				return "posizione " + posizione + " transizione " + transizione;
			else 
				return  "transizione " + transizione + " posizione " + posizione;
		}
}
