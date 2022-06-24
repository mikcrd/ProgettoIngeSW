package mainProgram3;


import utility.*;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;

import utility.LeggiInput;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reti"
})
@XmlRootElement(name = "ArchivioReti")
public class ArchivioReti {
	
	private final static String MESS_DOPPIONE = "Attenzione: non si puo' inserire una rete  gia' esistente!";
	private final static String MESS_NOME_GIA_PRESENTE = "Nell'archivio è già presente una "
			+ "rete con questo nome. Inserire un altro nome: ";
	private final static String MESS_STESSA_TOPOLOGIA = "Attenzione: nell'archivio è già presente una rete con la stessa topologia: ";
	private final static String MESS_CERCA_RETE = "Inserisci il nome della rete: ";
	private final static String MESS_RIMOZIONE = " : confermi la rimozione di questa rete?";
	private final static String MESS_NON_TROVATA = "Rete richiesta non trovata";
	private static final String NOME_RETE_VISUALIZZA = "Inserisci il nome della rete da visualizzare: ";
	private static final String ERRORE_ARCHIVIO_VUOTO = "Attenzione archivio vuoto";

	
		@XmlElementWrapper(name= "reti")
		@XmlElement(name="rete", required = true)
		ArrayList <AbstractRete> reti;
		
		public ArchivioReti(ArrayList <AbstractRete> arch) {
			this.reti = arch;
		}
		
		public ArchivioReti() {
			
		}


		public ArrayList<AbstractRete> getArchivio() {
			if (reti == null) {
				reti = new ArrayList<AbstractRete>();
	        }
	        return this.reti;
		}
		
		public AbstractRete trovaRete (String reteRichiesta)
		{ 
			for(AbstractRete elem : reti){	  
				if(elem.getName().equalsIgnoreCase(reteRichiesta))
					return elem;
			} 
			return null;  
		}	
			
		public AbstractRete cercaRete()
		{
			String net = LeggiInput.leggiStringaNonVuota(MESS_CERCA_RETE);
			return trovaRete(net);
		}
		
		public void eliminaRete()
		{
			String nome=LeggiInput.leggiStringaNonVuota(MESS_CERCA_RETE);
			try {
				AbstractRete elemento = trovaRete(nome);
				if (elemento!= null)
				{
					boolean procedi = LeggiInput.yesOrNo(elemento.getName() + MESS_RIMOZIONE);
						if (procedi)
							reti.remove(elemento);
						    salvaLista();
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(MESS_NON_TROVATA);
			}
		
		}
		
		
		public void aggiungiRete(AbstractRete r) {
			r=r.creaRete();       
			if(r!=null)
				salvaRete(r);
		}
	
		//mi serve per il testing
		public void salvaRete(AbstractRete r) {
			
			if(r.isCorrect() && !isEqual(r)) {
				System.out.println("Sto salvando la rete ...");
				reti.add(r);
				salvaLista();
				r.stampaRete();
			}
		}
		
		public void salvaLista() {
			GestioneFile.objToXml(this);
		}
		
		public boolean noRetiInArchivio() {
			for(AbstractRete rete: getArchivio()) {
				if(rete instanceof Rete) return false;
			}
			return true;
		}
		
		public boolean noRetiPNInArchivio() {
			for(AbstractRete rete: getArchivio()) {
				if(rete instanceof RetePetri) return false;
			}
			return true;
		}
		
		public void visualizzaRete() {
			this.visualizzaNomeReti();
			 if(reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) { 
					String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
					AbstractRete daVisualizzare = this.trovaRete(nome);
					if(daVisualizzare == null) {
						System.out.println(MESS_NON_TROVATA);
					}
					else {
						daVisualizzare.stampaRete();}	
			 }
		}
		
		public void visualizzaRetePetri() {
			this.visualizzaNomeRetiPN();
			 if(reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) { 
					String nome = LeggiInput.leggiStringaNonVuota(NOME_RETE_VISUALIZZA);
					AbstractRete daVisualizzare = this.trovaRete(nome);
					if(daVisualizzare == null) {
						System.out.println(MESS_NON_TROVATA);
					}
					else {
						daVisualizzare.stampaRete();}	
			 }
		}

		//visualizza i nomi di tutte e sole le reti presenti nell'archivio
		public void visualizzaNomeReti() {
			if(reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) {
				System.out.println("Nomi delle reti presenti: \n");
				for(AbstractRete elem : reti) {
					if(elem instanceof Rete) {
						System.out.println(elem.getName());
					}
				}
			} else { 
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		 
		//visualizza il nome di tutte e sole le PN nell'archivio
		public void visualizzaNomeRetiPN() {
			if(reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) {
				System.out.println("Nomi delle reti di Petri presenti: \n");
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePetri) {
						System.out.println(elem.getName());
					}
				}
			} else { 
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		
		public void visualizzaSoloRetiArchivio() {
			if(reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) {
				for(AbstractRete elem : reti) {
					if(elem instanceof Rete) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		
		public void visualizzaSoloRetiPNArchivio() {
			if(reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) {
				for(AbstractRete elem : reti) {
					if(elem instanceof RetePetri) {
						elem.stampaRete();
					}
				}
			} else {
				System.out.println(ERRORE_ARCHIVIO_VUOTO);
			}
		}
		
		
		public boolean isEqual(AbstractRete daConfrontare) {
			for(AbstractRete rete: getArchivio()) {
				if(rete.equals(daConfrontare)) {
					System.out.println(MESS_DOPPIONE);
					return true; }
				else if(!(rete.getRelazioni().containsAll(daConfrontare.getRelazioni())) 
						&& (rete.getName().equals(daConfrontare.getName()))) {
					String nuovoNome;
					boolean flag;
					do {
							nuovoNome = LeggiInput.leggiStringaNonVuota(MESS_NOME_GIA_PRESENTE);
							flag = true;
							
							for(AbstractRete retexNomi: getArchivio()) {
									if(nuovoNome.equals(retexNomi.getName())) {
									flag = false;	
									}
						    }
					}while(!flag);

					daConfrontare.setName(nuovoNome);
					return false;
				}
				else if((rete.getRelazioni().containsAll(daConfrontare.getRelazioni()))
						&& rete instanceof Rete && daConfrontare instanceof Rete) {
					System.out.print(MESS_STESSA_TOPOLOGIA);
					System.out.println(rete.getName());
					return true;
				}
			}
			return false;
		}

		
}
