package mx.isban.multivendor.ejemplos.rest;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
/**
 * Demo de ejecucion de un comando curl con restTemplate
 * @author jmedince
 *
 */
public class CommandRestTemplateMain {

	private static Logger LOG = Logger.getLogger(CommandRestTemplateMain.class);
	
	private static HttpHeaders headers;
	private static RestTemplate restTemplate;
	private static HttpEntity<MultiValueMap<String, Object>> mapValue;
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		if (headers == null) {
			headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			restTemplate = new RestTemplate();
		}
		
		try {
			LOG.info("==================== Creando header de Autorización ====================");
			
			/* Autenticacion basica */
			String userOAuth = "usuario:password";
			byte[] plainUserOAuth = userOAuth.getBytes();
			byte[] baseUserOAuth = Base64.encodeBase64(plainUserOAuth);
			String baseNewOAuth = new String(baseUserOAuth);
			
			LOG.info("Usuario en base: " + baseNewOAuth);
			
			headers.add("Authorization", "Basic " + baseNewOAuth);
			
			LOG.info("==================== Inicio de carga de datos ====================");
			
					
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("file", getResourse()); /* Archivo */
			map.add("errorHandling", "SKIP"); 
			
			mapValue = new HttpEntity<MultiValueMap<String,Object>>(map, headers);
			
			LOG.info("==================== Llamado a Url ====================");
			ResponseEntity<String> response = restTemplate.exchange("http://localhost:9081/upload/file", HttpMethod.POST, mapValue, String.class);
			
			if (response == null || response.getBody().isEmpty()) {
				LOG.info("==================== SIN INFORMACION ====================");
				return;
			}
			
			/* La respuesta del servicio puede regresa un bean, para mi ejemplo se regresa un simple String */
			/* Ejemplo  ResponseEntity<CustomerBean> response */
			
			
			LOG.info("==================== RESPUESTA TM INICIO ====================");
			LOG.info("[" + response.getBody() + "]");
			LOG.info("==================== RESPUESTA TM FIN ====================");
			
		} catch (RestClientException e) {
			LOG.error("==================== ERROR RestClientException ====================");
			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error("==================== ERROR Exception ====================");
			LOG.error(e.getMessage());
		}
	}	
	
	static Resource getResourse() {
		return new FileSystemResource(new File("C:\\Users\\Desktop\\Especificacion\\Userspecification\\MasterData\\prueba.xml"));
	}
}
