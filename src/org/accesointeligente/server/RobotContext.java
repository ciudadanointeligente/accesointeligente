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

import org.accesointeligente.server.robots.Robot;
import org.accesointeligente.shared.InstitutionClass;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RobotContext implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(RobotContext.class);
	private static ApplicationContext context;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Context destroyed");
		context = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			context = new FileSystemXmlApplicationContext(event.getServletContext().getResource("/WEB-INF/robots.xml").toString());
			logger.info("Context initialized");
		} catch (Exception ex) {
			logger.error("Failed to initialize context", ex);
		}
	}

	public static Robot getRobot(InstitutionClass institutionClass) {
		if (context != null && context.containsBean(institutionClass.name())) {
			return context.getBean(institutionClass.name(), Robot.class);
		} else {
			return null;
		}
	}
}
