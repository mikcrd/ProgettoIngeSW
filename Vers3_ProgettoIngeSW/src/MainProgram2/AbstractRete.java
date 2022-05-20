package MainProgram2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractRete", propOrder = {
    "relazioni"
})
@XmlSeeAlso({
    RetePN.class,
    Rete.class
})
public abstract class AbstractRete {

		private final static String MESS_NOME_GIA_PRESENTE = "Nell'archivio è già presente una "
				+ "rete con questo nome. Inserire un altro nome: ";
		
	    @XmlTransient
		ArchivioReti arch;
	    
	    @XmlTransient
		int numPos, numTrans;
	    
	    @XmlTransient
		int [][] in;
		@XmlTransient
		int [][]out;
		@XmlTransient
		int [][] inc;
		
		@XmlAttribute(name = "nome")
		String name;
		
		@XmlElementWrapper(name="relazioni")
		@XmlElement(name="relazione", required = true)
		ArrayList<AbstractRelazioneDiFlusso> relazioni;
		
		
		public AbstractRete() {
			name=null;
			relazioni = new ArrayList<AbstractRelazioneDiFlusso>();
		}
		
		public AbstractRete(ArchivioReti arc) {
			this.arch = arc;                           //dependency inj
		}
		
		

		public AbstractRete(String name, ArrayList<AbstractRelazioneDiFlusso> relazioni) {
			super();
			this.name = name;
			this.relazioni = relazioni;
		}
		
		public void setNumPos(int numPos) {
			this.numPos = numPos;
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
		
		
				

		abstract public boolean isCorrect();
		
		abstract public void stampaRete();
	
		abstract public AbstractRete creaRete();

		@Override
		abstract public int hashCode();
			
	
		@Override
		abstract public boolean equals(Object obj);
	
			
}