package main;

import java.util.ArrayList;


public class ArchivioRetiNew {
	private static final String MES1="l'archivio contiene le seguenti reti:";
	private static final String MES2="l'archivio non contiene alcuna rete";

	ArrayList<Rete> archivio; 
	
	
	public ArchivioRetiNew() {
		archivio = new ArrayList<Rete>();
	}
	
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
	public void visualizzaArchivio() {
		if(!archivio.isEmpty()) {
			for(Rete r: archivio) {
				System.out.println(MES1);
				r.stampaRete();
			}
		}else {
			System.out.println(MES2);
		}
			
	}
	
	//visualizzaRete che prende in ingresso un nome e restituisce a video una rete (usa il metodo trovaRete)
	public void visualizzaRete(String n) {
		this.visualizzaArchivio();
		for(Rete r : archivio) {
			if (r.getName().equals(n)) {
				r.stampaRete();
			}
		}
		System.out.println("la rete cercata non è presente nell'archivio");
	}
	
	
	//aggiungiRete che è un gran mistero!
	public void aggiungiRete(Rete r) {
		archivio.add(r);
	}
	
	
	//metodo che elimina la rete passata per nome
	public void eliminaRete(String n) {
		if (trovaNome(n)) {
			System.out.println("la rete è stata rimossa con successo");
		}
		else 
			System.out.println("la rete non è presente nell'archivio");
	}
}
