/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2012 Fundación Ciudadano Inteligente
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

import org.accesointeligente.model.Notification;
import org.accesointeligente.shared.ServiceException;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class NotificationUtil {
	private static final Logger logger = Logger.getLogger(Emailer.class);

	public static Notification saveNotification(Notification notification) throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			hibernate.saveOrUpdate(notification);
			hibernate.getTransaction().commit();

			return notification;
		} catch (Throwable ex) {
			logger.error("No se ha podido almacenar la notificación", ex);
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
