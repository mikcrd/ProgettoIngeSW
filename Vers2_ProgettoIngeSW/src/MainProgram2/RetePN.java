package MainProgram2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import utility.LeggiInput;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "relazioni"
})
public class RetePN extends AbstractRete implements IRelazioneDiFlusso {
	
	    @XmlAttribute(name = "name", required = true)
		String name;
		
	    @XmlElementWrapper(name= "relazioni")
		@XmlElement(name = "", required = true, type=RelazioneDiFlusso.class)
		ArrayList<RelazionePN> relazioni;
	
		
		public RetePN() {
			name=null;
			relazioni = new ArrayList<RelazionePN>();
		}

		public RetePN(String name, ArrayList<IRelazioneDiFlusso> relazioni) {
			super(name, relazioni);
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	
		public ArrayList<RelazionePN> getRelazioni() {
			if (relazioni == null) {
	        	relazioni = new ArrayList<RelazionePN>();
	        }
	        return this.relazioni;
		}

		public void aggiungiRelazione(RelazionePN r) {
			this.getRelazioni().add(r);		
		}


		/**
		 * Se almeno un peso o una marcatura non sono validi, ritorna falso
		 * marcature: da 0 a +infinito
		 * pesi: da 1 a +infinito
		 */
		@Override
		public boolean isCorrect() {
           for(RelazionePN rel : getRelazioni()) {
        	   if(rel.getMarcatura() < 0 || rel.getPeso() <= 0)
        		   return false; break;
           }
   
		   return true;  
		}
	

		
}

//NOTA: i metodi hashcode e isEqual di Rete e RetePN sono uguali, considerare di fare una classe astratta?
