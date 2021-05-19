package MainProgram2;

import java.util.ArrayList;

import utility.LeggiInput;

public abstract class AbstractRete {

		private final static String MESS_NOME_GIA_PRESENTE = "Nell'archivio è già presente una "
				+ "rete con questo nome. Inserire un altro nome: ";
	
	
		String name;
		ArrayList<IRelazioneDiFlusso> relazione;
		
		public AbstractRete() {
			name=null;
			relazione = new ArrayList<IRelazioneDiFlusso>();
		}

		public AbstractRete(String name, ArrayList<IRelazioneDiFlusso> relazioni) {
			super();
			this.name = name;
			this.relazione = relazioni;
		}

	
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		
		abstract public boolean isCorrect();
		
		public void stampaRete() {
			System.out.println();
			System.out.println(this.name);
			for (IRelazioneDiFlusso r : relazione) {
				System.out.println(r.toString());
			}
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((relazione == null) ? 0 : relazione.hashCode());
			return result;
		}
	
		// reti, reti PN e reti PNP devono tutte avere nomi diversi??
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			
			AbstractRete other = (AbstractRete) obj;
			
			if(relazione.equals(other.relazione) && !name.equals(other.name)) 
				return true;
			else if(!relazione.equals(other.relazione) && name.equals(other.name)) {
				String nuovoNome = LeggiInput.leggiStringaNonVuota(MESS_NOME_GIA_PRESENTE);
				other.setName(nuovoNome);
				return false;
			}
			return false;
		}

		
}
