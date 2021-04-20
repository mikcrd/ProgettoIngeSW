package MainProgram;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reti"
})
@XmlRootElement(name = "archivioReti")
public class ArchivioReti {
	
	@XmlElementWrapper(name= "reti")
	@XmlElement(name="rete", required = true)
	ArrayList <Rete> reti;
		
		public ArchivioReti(ArrayList <Rete> arch) {
			this.reti = arch;
		}
		
		public ArchivioReti() {
			super();
		}


		public ArrayList<Rete> getArchivio() {
			if (reti == null) {
				reti = new ArrayList<Rete>();
	        }
	        return this.reti;
		}
		
}
