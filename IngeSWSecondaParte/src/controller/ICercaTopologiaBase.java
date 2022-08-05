package controller;
import model.*;

import java.util.ArrayList;

public interface ICercaTopologiaBase {
	
	public ArrayList<AbstractRelazioneDiFlusso> getTopologiaSottostante();

	public boolean controlloPerSalvataggioDaFile(AbstractRete rete);
}
