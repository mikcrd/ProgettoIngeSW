package MainProgram2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reti"
})
@XmlRootElement(name = "archivioReti")
public class ArchivioReti {

		@XmlElementWrapper(name= "reti")
		@XmlElement(name="rete", required = true)
		ArrayList <AbstractRete> reti;
		
		public ArchivioReti(ArrayList <AbstractRete> arch) {
			this.reti = arch;
		}
		
		public ArchivioReti() {
			super();
		}


		public ArrayList<AbstractRete> getArchivio() {
			if (reti == null) {
				reti = new ArrayList<AbstractRete>();
	        }
	        return this.reti;
		}
	
		
		public boolean isEqual(AbstractRete daConfrontare) {
			for(AbstractRete rete: getArchivio()) {
				if(rete.equals(rete)) 
					return true;
			}
			return false;
		}
		
		
		
}
