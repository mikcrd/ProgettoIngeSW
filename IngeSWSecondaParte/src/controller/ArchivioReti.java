package controller;
import model.*;
import view.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "reti" })
@XmlRootElement(name = "ArchivioReti")
public class ArchivioReti {

	
	public static final File file = new File("C:\\data\\reti5_xml.xml");

	@XmlElementWrapper(name = "reti")
	@XmlElement(name = "rete", required = true)
	/* @spec_public@ */List<AbstractRete> reti;
	/*
	 * @invariant
	 * 
	 * @reti != null;
	 * 
	 * @
	 */

	public ArchivioReti(List<AbstractRete> arch) {
		this.reti = arch;
	}

	public ArchivioReti() {
		this.reti = new ArrayList<AbstractRete>();
	}

	public List<AbstractRete> getArchivio() {
		if (reti == null) {
			reti = new ArrayList<AbstractRete>();
		}
		return this.reti;
	}

	public AbstractRete trovaRete(String reteRichiesta) {
		for (AbstractRete elem : getArchivio()) {
			if (elem.getName().equalsIgnoreCase(reteRichiesta))
				return elem;
		}
		return null;
	}

	public AbstractRete cercaRete() {
		String net = InputOutput.leggiStringaNonVuota(Vista.ARCHIVIO_MESS_CERCA_RETE);
		return trovaRete(net);
	}

	/*
	 * @public normal_behavior
	 * 
	 * @requires reti.contains(elem);
	 * 
	 * @ensures !reti.contains(elem);
	 * 
	 * @ensures (\forall AbstractRete e; e != elem; reti.contains(e) <==>
	 * \old(reti.contains(e)));
	 * 
	 * @ensures reti.size() == \old(reti.size())-1;
	 * 
	 * @
	 * 
	 * @also
	 * 
	 * @
	 * 
	 * @public normal_behavior
	 * 
	 * @requires !reti.contains(elem);
	 * 
	 * @ensures (\forall AbstractRete e; reti.contains(e) <==>
	 * \old(reti.contains(e)));
	 * 
	 * @ensures reti.size() == \old(reti.size());
	 * 
	 * @assignable \nothing;
	 * 
	 * @
	 */
	public void eliminaRete(AbstractRete abs) {
		abs.visualizzaElencoParziale();
		if (!(abs.retiInArchivio())) {
		} else {
			String nome = InputOutput.leggiStringaNonVuota(Vista.ARCHIVIO_MESS_CERCA_RETE);
			try {
				AbstractRete elemento = trovaRete(nome);
				
				boolean procedi = InputOutput.yesOrNo(elemento.getName() + Vista.ARCHIVIO_MESS_RIMOZIONE);
				if (procedi)
				reti.remove(elemento);
				salvaLista();
				
			} catch (NullPointerException e) {
				InputOutput.mostraMessaggio(Vista.MESS_NON_TROVATA);
			}
		}
	}

	public void aggiungiRete (AbstractRete r) {
		try {
			r=r.creaRete();
			salvaRete(r);
		}catch(NullPointerException e) {
			
		}
		
		
	}

	/*
	 * @public normal_behavior
	 * 
	 * @requires r.isCorrect();
	 * 
	 * @requires !isEqual(r);
	 * 
	 * @ensures reti.contains(r);
	 * 
	 * @ensures (\forall AbstractRete e; e != r; reti.contains(e) <==>
	 * \old(reti.contains(e)));
	 * 
	 * @ensures reti.size() == \old(reti.size())+1;
	 * 
	 * @
	 * 
	 * @also
	 * 
	 * @
	 * 
	 * @public normal_behavior
	 * 
	 * @requires !r.isCorrect || isEqual(r);
	 * 
	 * @ensures (\forall AbstractRete e; reti.contains(e) <==>
	 * \old(reti.contains(e)));
	 * 
	 * @ensures reti.size() == \old(reti.size());
	 * 
	 * @assignable \nothing;
	 * 
	 * @
	 */
	public void salvaRete(AbstractRete r) {

		if (r.isCorrect() && !isEqual(r)) {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_DEBUGGING_SALVA_RETE);
			reti.add(r);
			salvaLista();
			r.stampaRete();
		}
	}

	
	public void salvaReteDaFile(String path) {
		File f =new File(path);
		List <AbstractRete> daFile = new ArrayList<AbstractRete>();
		daFile=GestioneFile.xmlToRete(f);
		boolean[] controllo = new boolean[daFile.size()];
		int i=0;
		
		if(!reti.isEmpty()) {
			for(AbstractRete rete : daFile) {
				for(AbstractRete arch : reti) {
					if(rete instanceof ICercaTopologiaBase && ((ICercaTopologiaBase) rete).controlloPerSalvataggioDaFile(arch)) {
						controllo[i]=true;
					}else if(rete instanceof Rete) {
						controllo[i]=true;
					}
					
				}
				i++;
			}
		}else {
			for (AbstractRete rete : daFile) {
				if(!(rete instanceof ICercaTopologiaBase))
					controllo[i]=true;
				i++;
			}
		}
		
		i=0;
		for(AbstractRete r:daFile) {
			//System.out.println(controllo[i]);
			if(controllo[i]==true)
				salvaRete(r);
			else
				System.out.println("non � stato possibile salvare la rete desiderata");
			i++;
		}
		
	}
	
	

	public void salvaLista() {

		GestioneFile.objToXml(this, file);
	}

	public void visualizzaRete(AbstractRete abs) {
		abs.visualizzaElencoParziale();
		if (abs.retiInArchivio()) {
			String nome = InputOutput.leggiStringaNonVuota(Vista.ARCHIVIO_NOME_RETE_VISUALIZZA);
			AbstractRete daVisualizzare = this.trovaRete(nome);
			if (daVisualizzare == null) {
				InputOutput.mostraMessaggio(Vista.MESS_NON_TROVATA);
			} else {
				daVisualizzare.stampaRete();
			}
		}
	}

	
	public boolean noRetiInArchivio() {
		for (AbstractRete rete : getArchivio()) {
			if (rete instanceof Rete)
				return false;
		}
		return true;
	}

	public boolean noRetiPNInArchivio() {
		for (AbstractRete rete : getArchivio()) {
			if (rete.getClass().getSimpleName().equals(Vista.RETEP))
				return false;
		}
		return true;
	}

	public boolean noRetiPNPInArchivio() {
		for (AbstractRete rete : getArchivio()) {
			if (rete.getClass().getSimpleName().equals(Vista.RETEPP))
				return false;
		}
		return true;
	}


	// visualizza i nomi di tutte e sole le reti presenti nell'archivio
	public void visualizzaNomeReti() {
		if (reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_NOMI_RETI_PRESENTI);
			for (AbstractRete elem : reti) {
				if (elem instanceof Rete) {
					InputOutput.mostraMessaggio(elem.getName());
				}
			}
		} else {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_ERRORE_ARCHIVIO_VUOTO);
		}
	}

	// visualizza il nome di tutte e sole le PN nell'archivio
	public void visualizzaNomeRetiPN() {
		if (reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_NOMI_RETIP_PRESENTI);
			for (AbstractRete elem : reti) {
				if (elem instanceof RetePetri && !(elem instanceof RetePetriP)) {
					InputOutput.mostraMessaggio(elem.getName());
				}
			}
		} else {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_ERRORE_ARCHIVIO_VUOTO);
		}
	}

	public void visualizzaNomeRetiPNP() {
		if (reti != null && !(reti.isEmpty()) && !noRetiPNPInArchivio()) {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_NOMI_RETIPP_PRESENTI);
			for (AbstractRete elem : reti) {
				if (elem instanceof RetePetriP) {
					InputOutput.mostraMessaggio(elem.getName());
				}
			}
		} else {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_ERRORE_ARCHIVIO_VUOTO);
		}
	}

/*	public void visualizzaSoloRetiArchivio() {
		if (reti != null && !(reti.isEmpty()) && !noRetiInArchivio()) {
			for (AbstractRete elem : reti) {
				if (elem instanceof Rete) {
					elem.stampaRete();
				}
			}
		} else {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_ERRORE_ARCHIVIO_VUOTO);
		}
	}

	public void visualizzaSoloRetiPNArchivio() {
		if (reti != null && !(reti.isEmpty()) && !noRetiPNInArchivio()) {
			for (AbstractRete elem : reti) {
				if (elem instanceof RetePetri && !(elem instanceof RetePetriP)) {
					elem.stampaRete();
				}
			}
		} else {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_ERRORE_ARCHIVIO_VUOTO);
		}
	}

	public void visualizzaSoloRetiPNPArchivio() {
		if (reti != null && !(reti.isEmpty()) && !noRetiPNPInArchivio()) {
			for (AbstractRete elem : reti) {
				if (elem instanceof RetePetriP) {
					elem.stampaRete();
				}
			}
		} else {
			InputOutput.mostraMessaggio(Vista.ARCHIVIO_ERRORE_ARCHIVIO_VUOTO);
		}
	}*/

	public boolean isEqual(AbstractRete daConfrontare) {
		for (AbstractRete rete : this.getArchivio()) {
			 if ((rete.stessaTopologia(daConfrontare))) {
				InputOutput.mostraMessaggio(Vista.ARCHIVIO_ISEQUAL_MESS_STESSA_TOPOLOGIA);
				InputOutput.mostraMessaggio(rete.getName());
				return true;
			}
			else if ((rete.getName().equals(daConfrontare.getName()))) {
				daConfrontare.setName(changeName());
				return false;
			} 
		}
		return false;
	}

	public String changeName() {
		String nuovoNome;
		boolean flag;
		do {
			nuovoNome = InputOutput.leggiStringaNonVuota(Vista.ARCHIVIO_ISEQUAL_MESS_NOME_GIA_PRESENTE);
			flag = true;

			for (AbstractRete retexNomi : getArchivio()) {
				if (nuovoNome.equals(retexNomi.getName())) {
					flag = false;
				}
			}
		} while (!flag);

		return nuovoNome;
	}

}
