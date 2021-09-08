package MainProgram2;

import java.io.File;

public class Main {
	
	private static File file = new File("src\\data\\reti_xml.xml");

	public static void main(String[] args) {
		
		ArchivioReti arch = riempiArchivio(file);
		Menu menu = new Menu(arch);
		menu.cicloApplicazione();

	}
	
	
	public static ArchivioReti riempiArchivio(File f) {
		
		ArchivioReti archivio = new ArchivioReti();
  		if(f.length() != 0L) {
  			archivio = GestioneFile.xmlToObj(f);
  		}
  		return archivio;
	}

}

