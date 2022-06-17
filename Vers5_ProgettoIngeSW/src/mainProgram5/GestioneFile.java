package mainProgram5;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class GestioneFile {
    private static char FS = File.separatorChar;
	
	private static JAXBContext contextObj;
//	private static File file = new File("C:\\Users\\michela\\workspace\\ProvaXML\\prova_xml.xml");
//	private static File file = new File("src\\data\\reti_xml.xml");
	
/**	
	public static void salvataggioSuFile(ArchivioReti arch, File file) {
		objToXml(arch);
	}
**/	
	
	
	public  static void objToXml(ArchivioReti arch, File file) {
		
		try {
			contextObj = JAXBContext.newInstance(ArchivioReti.class);
			 Marshaller marshallerObj = contextObj.createMarshaller();  
			 marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			 
			 marshallerObj.marshal(arch, file);
			 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
		  
	}
	
	
     public static ArchivioReti xmlToObj(File file) {
		
		ArchivioReti a = null;
		
		try {
			 contextObj = JAXBContext.newInstance(ArchivioReti.class, AbstractRete.class, Rete.class, RetePetri.class, RetePetriP.class, RelazioneDiFlusso.class, RelazionePetri.class);
			 Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();  
		     a = (ArchivioReti) jaxbUnmarshaller.unmarshal(file);  
		     
 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	
		return a;
	}
     
     public static ArrayList <AbstractRete> xmlToRete(File file) {
   		
    	 ArchivioReti a = null;
    	 
  		try {
  			 contextObj = JAXBContext.newInstance(ArchivioReti.class, Rete.class, RetePetri.class, RetePetriP.class, RelazioneDiFlusso.class, RelazionePetri.class);
  			 Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();  
  		     a = (ArchivioReti) jaxbUnmarshaller.unmarshal(file);  
  		     
   
  		} catch (JAXBException e) {
  			e.printStackTrace();
  		}  
  	
  		return a.getArchivio();
  	
  	}
     
}