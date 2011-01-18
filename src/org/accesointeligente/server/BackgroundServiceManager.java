package org.accesointeligente.server;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BackgroundServiceManager implements ServletContextListener {
	private Timer timer;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer();
	}
}
