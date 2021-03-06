package mainProgram2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelazionePN", propOrder = {
   
    "peso",
 //   "relazione"
})
public class RelazionePN extends AbstractRelazioneDiFlusso{
   
		private static final String DESCRIZIONE = "[%s, peso= %d]"; 
	
		int peso;
	//	private RelazioneDiFlusso relazione;
		
		//valori di default
		public RelazionePN() {
		//	relazione = null;
		//	posizione = 0;
		//	transizione = 0;
		//	inOut = true;
	
			peso = 1;
		}
	
		public RelazionePN(RelazioneDiFlusso relazione, int peso) {
//			super();
	//		this.relazione = relazione;
			this.posizione = relazione.getPosizione();
			this.transizione = relazione.getTransizione();
			this.inOut = relazione.isInOut();
			this.peso = peso;
		}

		
		public int getPeso() {
			return peso;
		}
		
		public void setPeso(int peso) {
			this.peso = peso;
		}
		
		public RelazionePN creaRelazione() {
			return null;
			
		}

		public String toString() {
			return String.format(DESCRIZIONE, super.toString(), getPeso());
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + peso;
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
			RelazionePN other = (RelazionePN) obj;
			if (peso != other.peso)
				return false;
			return true;
		}
		
				
/**
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + marcatura;
			result = prime * result + peso;
//			result = prime * result + ((relazione == null) ? 0 : relazione.hashCode());
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
			RelazionePN other = (RelazionePN) obj;
			if (marcatura != other.marcatura)
				return false;
			if (peso != other.peso)
				return false;
		if (relazione == null) {
				if (other.relazione != null)
					return false;
			} else if (!relazione.equals(other.relazione))
				return false; 
			return true; 
		}
**/
		
}
