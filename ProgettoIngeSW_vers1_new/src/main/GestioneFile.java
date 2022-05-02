package main;

import java.io.File;

import javax.xml.bind.*;

public class GestioneFile {
private static char FS = File.separatorChar;
	
	private static JAXBContext contextObj;
	private static File file = new File("src\\data\\prova_xml.xml");
	
	public  static void objToXml(ArchivioRetiNew arch) {
		
		try {
			contextObj = JAXBContext.newInstance(ArchivioRetiNew.class);
			 Marshaller marshallerObj = contextObj.createMarshaller();  
			 marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			 
			 marshallerObj.marshal(arch, file);
			 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
		  
	}
	
	
	public static ArchivioRetiNew xmlToObj(File file) {
		
		ArchivioRetiNew a = null;
		
		try {
			 contextObj = JAXBContext.newInstance(ArchivioRetiNew.class);
			 Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();  
		     a = (ArchivioRetiNew) jaxbUnmarshaller.unmarshal(file);  
		     
 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	
		return a;
	
	}
	
}
