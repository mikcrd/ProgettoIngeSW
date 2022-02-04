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
	
	public boolean trovaNome(String n) {
		for (Rete r : archivio) {
			if (n.equals(r.getName()))
				return true;
		}
		return false;
	}
	
	//visualizza archivio (da copiare)
	
	//visualizzaRete che prende in ingresso un nome e restituisce a video una rete (usa il metodo trovaRete)
	
	//aggiungiRete che è un gran mistero!

	public void eliminaRete(String n) {
		if (trovaNome(n)) {
			System.out.println("la rete è stata rimossa con successo");
		}
		else 
			System.out.println("la rete non è presente nell'archivio");
	}
}
