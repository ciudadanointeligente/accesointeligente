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

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.GwtConfigurationHelper;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final Logger logger = Logger.getLogger(HibernateUtil.class);
	private static SessionFactory sessionFactory = null;
	private static PersistentBeanManager persistentBeanManager = null;

	public static Session getSession() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable ex) {
				logger.error("Couldn't get Hibernate session", ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory.getCurrentSession();
	}

	public static PersistentBeanManager getPersistentBeanManager() {
		if (sessionFactory == null) {
			getSession();
		}

		if (persistentBeanManager == null) {
			persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(new net.sf.gilead.core.hibernate.HibernateUtil(sessionFactory));
		}

		return persistentBeanManager;
	}
}
