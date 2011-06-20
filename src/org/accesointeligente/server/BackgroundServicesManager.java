/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.server;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BackgroundServicesManager implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(BackgroundServicesManager.class);
	private Scheduler scheduler;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Context destroyed");
		scheduler = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Context initialized");
		scheduler = Scheduler.getInstance();
		scheduler.run();
	}
}
