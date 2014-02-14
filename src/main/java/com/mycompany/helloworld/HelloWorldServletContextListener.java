package com.mycompany.helloworld;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import com.axaih.axwt.AxWTModule;
import com.axaih.configuration.ConfigurationModule;
import com.axaih.ops.OpsServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.mycompany.helloworld.hello.HelloServiceServlet;

public class HelloWorldServletContextListener extends
		GuiceServletContextListener {
	private static WebAppContext createWebAppContext() {
		final WebAppContext context = new WebAppContext();

		ProtectionDomain domain = HelloWorldServletContextListener.class
				.getProtectionDomain();
		
		URL location = domain.getCodeSource().getLocation();
		context.setContextPath("/");

		// context.setWar(location.toExternalForm());

		try {
			context.setBaseResource(Resource.newResource("src/main/webapp"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return context;
	}

	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		this.servletContext = servletContextEvent.getServletContext();

		this.servletContext.setAttribute(
				"org.mortbay.jetty.servlet.Default.dirAllowed", "false");

		super.contextInitialized(servletContextEvent);

	}

	@Override
	protected Injector getInjector() {
		/*
		 * after :
		 */
		LogManager logManager = LogManager.getLogManager();

		Logger rootLogger = logManager.getLogger("");
		rootLogger.setLevel(Level.FINEST);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.FINEST);
		try {
			FileHandler fileHandler = new FileHandler(
					"/var/log/mycompany/helloworld/helloworld-log."
							+ DateFormatUtils
									.format(new Date(),
											DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT
													.getPattern()) + ".txt");

			fileHandler.setLevel(Level.FINEST);
			fileHandler.setFormatter(new Formatter() {
				public String format(LogRecord rec) {
					StringBuffer buf = new StringBuffer(1000);

					buf.append(rec.getSourceClassName());
					buf.append(' ');

					buf.append(new java.util.Date());
					buf.append(' ');
					buf.append(rec.getLevel());
					buf.append(' ');
					buf.append(formatMessage(rec));
					buf.append(System.getProperty("line.separator"));
					return buf.toString();
				}
			});
			rootLogger.addHandler(fileHandler);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rootLogger.addHandler(consoleHandler);

		Injector injector = Guice.createInjector(new AxWTModule(),
				new ConfigurationModule(), new ServletModule() {
					@Override
					public void configureServlets() {

						// filter
						Map<String, String> gzipInitParameters = new HashMap<String, String>();
						gzipInitParameters.put("excludePaths", "/si/*");
						gzipInitParameters.put("minGzipSize", "3072");
						bind(GzipFilter.class).in(Singleton.class);
						filter("/*").through(GzipFilter.class,
								gzipInitParameters);

						// servlet
						bind(DefaultServlet.class).in(Singleton.class);
						serve("/si/hello").with(HelloServiceServlet.class);
						serve("/ops", "/ops/*").with(OpsServlet.class);
						serve("/resources/*").with(DefaultServlet.class);
						serve("/*").with(PagesServlet.class);

					}

				});

		return injector;
	}

}