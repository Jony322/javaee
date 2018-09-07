package mx.isban.multivendor.ejemplos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Demo ejecucion de comando a nivel de sistema operativo con la libreria JSCH
 * @author jmedince
 *
 */
public class ExecCommandMain {

	private static Logger LOG = Logger.getLogger(ExecCommandMain.class);
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		Session sesion = null;
		ChannelExec exec = null;
		InputStream channelStream = null;
		BufferedReader reader = null;
		try {
			
			LOG.info("Generando el comando");
			String[] commandLine =  {"curl", "-u", "user:pass", "-F", "\"file=@/tmp/prueba.xml\"", "-F", "\"errorHandling=SKIP\"",
					"http://localhost:9081/upload/file", "tee out.log"};
			
			LOG.info("Imprimiendo en consola el comando");
			String command = "";
			for (String item : commandLine) {
				command = command.concat(item + " ");
			}
			
			LOG.info("cURL que se genero: " + command);	
			
			JSch jsch = new JSch();
			String Host = "localhost";
			
			LOG.info("Usuario: [jmedince] " + "Host: ["+ Host +"]");
			
			sesion = jsch.getSession("jmedince", Host, 22);
			sesion.setPassword("X18_****#");
			
			LOG.info("Usuario Auntenticado...");
			Properties prop = new Properties();
			prop.put("StrictHostKeyChecking", "no");
			
			sesion.setConfig(prop);
			sesion.setTimeout(3000);
			
			LOG.info("Conexión de la sesión");
			sesion.connect();
			
			LOG.info("Conexión de la sesión exitosa");
			
			LOG.info("Abriendo conexión exec");	
			
			exec = (ChannelExec) sesion.openChannel("exec");
			
			exec.setCommand(command.substring(0, command.length() - 1));
			exec.setInputStream(null);
			exec.setErrStream(System.err); /* No es obligatorio */
			
			channelStream = exec.getInputStream();
			exec.connect();
			
			reader = new BufferedReader(new InputStreamReader(channelStream));

			String line = "";
			StringBuilder concat = new StringBuilder();
						
			while ((line = reader.readLine()) != null) {
				concat.append(line);		
			}
			
			String responseDN = concat.toString();
			
			LOG.info("Respuesta DN: " + responseDN);
			
		} catch (Exception e) {
			LOG.error("==================== ERROR FATAL ====================");
			LOG.error(e.getMessage());
		} finally {
			LOG.info("\n");
			LOG.info("==================== CERRANDO CANALES ====================");
			try {
				if (channelStream != null) {
					channelStream.close();
				}	
				
				if (exec != null && exec.isConnected()) {
					exec.disconnect();
				}
				
				if (sesion != null && sesion.isConnected()) {
					sesion.disconnect();
				}
				
			} catch (IOException e) {
				LOG.error("==================== ERROR FATAL IO ====================");
				LOG.error(e.getMessage());
			}			
		}
	}
}
