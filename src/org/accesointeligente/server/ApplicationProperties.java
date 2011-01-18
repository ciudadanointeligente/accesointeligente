package org.accesointeligente.server;

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationProperties implements ServletContextListener {
	private static Properties properties;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		properties = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(event.getServletContext().getRealPath("/WEB-INF/accesointeligente.properties")));
		} catch (Throwable ex) {
			ex.printStackTrace(System.err);
		}
	}

	public static String getProperty(String key) {
		String property = properties.getProperty(key);

		if (property != null && property.length() == 0) {
			property = null;
		}

		return property;
	}
}
