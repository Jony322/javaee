package mx.isban.multivendor.ejemplos;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Validacion de un archivo XML apartir de un archivo xsd
 * 
 * @author jmedince
 */
public class ValidShemaFromXML {

	private static Logger LOG = Logger.getLogger(ValidShemaFromXML.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		String pathXSD = "C:\\Users\\jmedince\\Desktop\\Multivendor\\pcemasterdata\\pcemasterdata.xsd";
		String pathXML = "C:\\Users\\jmedince\\Desktop\\Multivendor\\Especificacion\\Userspecification\\MasterData\\masterdata331.xml";
		
		try {
//			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//			Schema schema = factory.newSchema(new File(pathXSD));
//			
//			Validator validator = schema.newValidator();
//			validator.validate(new StreamSource(new File(pathXML)));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
			LOG.info("Fecha :" + format.format(new Date()));
			
			
//		} catch (SAXException | IOException e) {
//			LOG.error("========== ERROR FATAL ==========");
//			if (e instanceof SAXException) {
//				LOG.error("========== ERROR SAXException ==========");
//			} else {
//				LOG.error("========== ERROR IOException ==========");
//			}
//			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error("========== ERROR FATAL ==========");
			LOG.error(e.getMessage());
		}
	}	
	
}
