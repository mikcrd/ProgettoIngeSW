package model;

import java.util.*;

public interface ICercaTopologiaBase {
	
	public List<AbstractRelazioneDiFlusso> getTopologiaSottostante();

	public boolean controlloPerSalvataggioDaFile(AbstractRete rete);
}
