import java.net.URL;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
public class Main {
	 // Get the global logger to configure it
    private static Logger logger = Logger.getLogger(Main.class.getName());
 
	final static CommandLineParser parser = new BasicParser();

	public static void main(String... anArgs) throws Exception {
		
		QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(500);

        // Server
        Server server = new Server(threadPool); 

		ProtectionDomain domain = Main.class.getProtectionDomain();
		URL location = domain.getCodeSource().getLocation();
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");

		// create Options object
		Options options = new Options();

		options.addOption("x", false, "to execute with mvn exec:java ");
	
		CommandLine cmd = parser.parse(options, anArgs);
		logger.log(Level.FINE,  "ARGS");


		if (cmd.hasOption("x")) {
			// print the date and time
			webapp.setResourceBase("src/main/webapp");
		} else {

			webapp.setWar(location.toExternalForm());
		}
		server.setHandler(webapp);

		 // HTTP Configuration
        HttpConfiguration http_config = new HttpConfiguration();
   
		
		  // === jetty-http.xml ===
        ServerConnector http = new ServerConnector(server,new HttpConnectionFactory(http_config));
        http.setPort(8080);
        http.setIdleTimeout(30000);
        server.addConnector(http);
         
		
		
		
		
		server.start();
		server.join();

	}

}