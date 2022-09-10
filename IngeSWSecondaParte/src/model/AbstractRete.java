package model;

import controller.*;

import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractRete", propOrder = {
    "relazioni"
})
@XmlSeeAlso({
    RetePetri.class,
    RetePetriP.class,
    Rete.class
})
public abstract class AbstractRete {
			
		/*@invariant
		  @arch != null;
		  @numPos, numTrans > 0;
		  @relazioni != null;
		  @*/
		
	    @XmlTransient
	    /*@spec_public@*/ArchivioReti arch;
	    
	    @XmlTransient
	    /*@spec_public@*/int numPos, numTrans;
	    
	    @XmlTransient
		int [][] in;
		@XmlTransient
		int [][]out;
		@XmlTransient
		int [][] inc;
		
		@XmlAttribute(name = "nome")
		/*@spec_public@*/String name;
		
		@XmlElementWrapper(name="relazioni")
		@XmlElement(name="relazione", required = true)
		/*@spec_public@*/List<AbstractRelazioneDiFlusso> relazioni;
		
		
		public AbstractRete() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
		}
		
		public AbstractRete(ArchivioReti arc) {
			this.arch = arc;                           //dependency inj
		}
		
		

		public AbstractRete(String name, List<AbstractRelazioneDiFlusso> relazioni) {
			super();
			this.name = name;
			this.relazioni = relazioni;
		}

	
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public List<AbstractRelazioneDiFlusso> getRelazioni() {
	        if (relazioni == null) {
	            relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
	        }
	        return this.relazioni;
	    }
		
		public ArchivioReti getArchivio() {
			return arch;
		}
		
		public void setArchivio(ArchivioReti arch) {
			this.arch=arch;
		}
		
		public int getNumPos() {
			return numPos;
		}
		
		public int getNumTrans() {
			return numTrans;
		}
		
		public abstract void contaPosizioni();
		
		public abstract void contaTransizioni();
		
		
		abstract public boolean isCorrect();
		
		abstract public void stampaRete();
	
		
		@Override
		abstract public int hashCode();
			
	
		@Override
		abstract public boolean equals(Object obj);

		abstract public void visualizzaElencoParziale();

		abstract public boolean retiInArchivio();

		abstract public boolean stessaTopologia(AbstractRete abs);
		
		public abstract AbstractRete creaRete();
	
			
}