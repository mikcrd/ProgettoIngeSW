package mainProgram5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;

	
public class RelazioneDiFlusso extends AbstractRelazioneDiFlusso {
	
	private static final String POSTO = "Inserisci un intero positivo per il posto: ";
	private static final String TRANSIZIONE = "Inserisci un intero positivo per la transizione: ";

	
	public RelazioneDiFlusso() {
				
	}
	
	public RelazioneDiFlusso(int posizione, int transizione) {
		this.posizione=posizione;
		this.transizione=transizione;
	}
	
	public RelazioneDiFlusso(int posizione, int transizione, boolean inOut) {
		this.inOut=inOut;
		this.posizione=posizione;
		this.transizione=transizione;
	}

	
	public RelazioneDiFlusso creaPosto_Trans() {
		setPosizione(LeggiInput.leggiInteroPositivo(POSTO));								
		setTransizione(LeggiInput.leggiInteroPositivo(TRANSIZIONE));
		setInOut(true);
		return this;		
	}
	
	public RelazioneDiFlusso creaTrans_Posto() {
		setTransizione(LeggiInput.leggiInteroPositivo(TRANSIZIONE));
		setPosizione(LeggiInput.leggiInteroPositivo(POSTO));
		setInOut(false);
		return this;
	}
	
	public String toString() {
		if (isInOut())
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
		if (posizione != other.posizione)
			return false;
		if (transizione != other.transizione)
			return false;
		return true;
	}
		
}

