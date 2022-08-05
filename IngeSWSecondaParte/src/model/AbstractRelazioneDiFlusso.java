package model;

import model.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IRelazioneDiFlusso", propOrder = {
	"inOut",
    "posizione",
    "transizione"
})
@XmlSeeAlso({
    RelazioneDiFlusso.class,
    RelazionePetri.class
})
public abstract class AbstractRelazioneDiFlusso {

	int posizione; 	
	int transizione;
	@XmlAttribute(name = "inOut")
	boolean inOut;
	
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
	
	public void setInOut(boolean inOut) {
		this.inOut = inOut;
	}
	
	public abstract AbstractRelazioneDiFlusso getRelazioneSottostante();

	public String toString() {
		if (isInOut())
			return "posto " + posizione + " transizione " + transizione;
		else 
			return  "transizione " + transizione + " posto " + posizione;
	}

	
}
