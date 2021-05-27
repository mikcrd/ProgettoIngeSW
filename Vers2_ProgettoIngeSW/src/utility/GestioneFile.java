package utility;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class GestioneFile {
    private static char FS = File.separatorChar;
	
	private static JAXBContext contextObj;
//	private static File file = new File("C:\\Users\\michela\\workspace\\ProvaXML\\prova_xml.xml");
	private static File file = new File("src\\data\\prova_xml.xml");
	
	
	public static void salvataggioSuFile(Object obj, File file) {
		objToXml(obj);
	}
	
	
	
	public  static void objToXml(Object obj) {
		
		try {
			contextObj = JAXBContext.newInstance(Object.class);
			 Marshaller marshallerObj = contextObj.createMarshaller();  
			 marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			 
			 marshallerObj.marshal(obj, file);
			 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
		  
	}
	
	
	public static Object xmlToObj(File file) {
		
		Object a = null;
		
		try {
			 contextObj = JAXBContext.newInstance(Object.class);
			 Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();  
		     a = (Object) jaxbUnmarshaller.unmarshal(file);  
		     
 
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	
		return a;
	
	}
}
