package main;

import java.util.ArrayList;

public class ArchivioRetiNew {
	ArrayList<Rete> archivio =new ArrayList<Rete>();
	
	
	public boolean presente(Rete r) {
		if (archivio.contains(r))
			return true;
		else 
			return false;			
	}
	
	public boolean stessoNome(String n) {
		for (Rete r : archivio) {
			if (n.equals(r.getName()))
				return true;
		}
		return false;
	}
	
	

	public void eliminaRete(String n) {
		if (stessoNome(n)) {
			System.out.println("la rete è stata rimossa con successo");
		}
		else 
			System.out.println("la rete non è presente nell'archivio");
	}
}
