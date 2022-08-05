package controller;
import model.*;


import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.AbstractRete;

import javax.xml.bind.*;


public class GestioneFile {
    private static char FS = File.separatorChar;
	
	private static JAXBContext contextObj;

	
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
		
		ArchivioReti a = new ArchivioReti();
		
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
   		
    	 ArchivioReti a = new ArchivioReti();
    	 
  		try {
  			 contextObj = JAXBContext.newInstance(ArchivioReti.class, AbstractRete.class, Rete.class, RetePetri.class, RetePetriP.class, RelazioneDiFlusso.class, RelazionePetri.class);
  			 Unmarshaller jaxbUnmarshaller = contextObj.createUnmarshaller();  
  		//	 a = (ArchivioReti) ((JAXBElement) jaxbUnmarshaller.unmarshal(file)).getValue();
  			a = (ArchivioReti) jaxbUnmarshaller.unmarshal(file);  
   
  		} catch (JAXBException e) {
  			e.printStackTrace();
  		}  
  	
  		return a.getArchivio();
  	
  	}
     
}