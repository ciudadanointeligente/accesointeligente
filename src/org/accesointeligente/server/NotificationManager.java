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

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class NotificationManager {
	private static final Logger logger = Logger.getLogger(Emailer.class);

	public void sendNotifications() {
		Session hibernate = null;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Notification.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("dispatched", Boolean.FALSE));
			List<Notification> notifications = criteria.list();
			hibernate.getTransaction().commit();

			for (Notification notification : notifications) {
				logger.info("notificationId = " + notification.getId());

				try {
					Emailer email = new Emailer();
					email.setRecipient(notification.getEmail());
					email.setSubject(notification.getSubject());
					email.setBody(notification.getMessage());
					email.connectAndSend();
					notification.setDispatched(true);

					hibernate = HibernateUtil.getSession();
					hibernate.beginTransaction();
					hibernate.update(notification);
					hibernate.getTransaction().commit();
				} catch (Exception ex) {
					if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
						hibernate.getTransaction().rollback();
					}

					logger.error(ex.getMessage(), ex);
				}
			}
		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error(ex.getMessage(), ex);
		}
	}
}
