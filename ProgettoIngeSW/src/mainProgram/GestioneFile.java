package mainProgram;

import java.io.File;

import javax.xml.bind.*;

public class GestioneFile {
private static char FS = File.separatorChar;
	
	private static JAXBContext contextObj;
	private static File file = new File("C:\\data\\reti_xml.xml");
	
	public  static void objToXml(ArchivioReti arch) {
		
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
			 contextObj = JAXBContext.newInstance(ArchivioReti.class);
			 Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();  
		     a = (ArchivioReti) jaxbUnmarshaller.unmarshal(file);  
		     
 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	
		return a;
	
	}
	
}
