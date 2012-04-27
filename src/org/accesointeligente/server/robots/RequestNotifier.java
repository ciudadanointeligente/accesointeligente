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
package org.accesointeligente.server.robots;

import org.accesointeligente.model.Notification;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.User;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.server.HolidayCalendar;
import org.accesointeligente.shared.NotificationType;
import org.accesointeligente.shared.RequestExpireType;
import org.accesointeligente.shared.RequestStatus;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

public class RequestNotifier {
	private static final Logger logger = Logger.getLogger(RequestNotifier.class);

	public void notifyExpiredRequests() {
		Session hibernate = null;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Request.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("expired", RequestExpireType.EXPIRESSOON));
			criteria.add(Restrictions.ne("status", RequestStatus.NEW));
			criteria.add(Restrictions.ne("status", RequestStatus.DRAFT));
			criteria.add(Restrictions.isNotNull("processDate"));
			List<Request> requests = criteria.list();
			hibernate.getTransaction().commit();

			for (Request request : requests) {
				if (!HolidayCalendar.isOnTime(request.getProcessDate(), 20)) {
					logger.info("requestId = " + request.getId());
					createExpiredNotification(request);
				}
			}
		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error(ex.getMessage(), ex);
		}

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Request.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("expired", RequestExpireType.ONTIME));
			criteria.add(Restrictions.ne("status", RequestStatus.NEW));
			criteria.add(Restrictions.ne("status", RequestStatus.DRAFT));
			criteria.add(Restrictions.ne("status", RequestStatus.ERROR));
			criteria.add(Restrictions.isNotNull("processDate"));
			List<Request> requests = criteria.list();
			hibernate.getTransaction().commit();

			for (Request request : requests) {
				Hibernate.initialize(request);
				if (!HolidayCalendar.isOnTime(request.getProcessDate(), 15)) {
					logger.info("requestId = " + request.getId());
					createExpiresSoonNotification(request);
				}
			}
		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error(ex.getMessage(), ex);
		}
	}

	public void createExpiredNotification(Request request) {
		Session hibernate = null;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Hibernate.initialize(request.getUser());
			User user = request.getUser();
			Notification notification = new Notification();
			notification.setEmail(user.getEmail());
			notification.setSubject(ApplicationProperties.getProperty("email.request.expired.subject"));
			notification.setMessage(String.format(ApplicationProperties.getProperty("email.request.expired.body"), user.getFirstName(), request.getInstitution().getName(), ApplicationProperties.getProperty("request.baseurl"), request.getId(), request.getTitle()) + ApplicationProperties.getProperty("email.signature"));
			notification.setType(NotificationType.REQUESTEXPIRED);
			notification.setDate(new Date());
			request.setExpired(RequestExpireType.EXPIRED);

			hibernate.saveOrUpdate(request);
			hibernate.saveOrUpdate(notification);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error("Couldn't create Request ExpiredNotification", ex);
		}
	}

	public void createExpiresSoonNotification(Request request) {
		Session hibernate = null;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Hibernate.initialize(request.getUser());
			User user = request.getUser();
			Notification notification = new Notification();
			notification.setEmail(user.getEmail());
			notification.setSubject(ApplicationProperties.getProperty("email.request.expiressoon.subject"));
			notification.setMessage(String.format(ApplicationProperties.getProperty("email.request.expiressoon.body"), user.getFirstName(), request.getInstitution().getName(), ApplicationProperties.getProperty("request.baseurl"), request.getId(), request.getTitle(), request.getConfirmationDate()) + ApplicationProperties.getProperty("email.signature"));
			notification.setType(NotificationType.REQUESTEXPIRESSOON);
			notification.setDate(new Date());
			request.setExpired(RequestExpireType.EXPIRESSOON);

			hibernate.saveOrUpdate(request);
			hibernate.saveOrUpdate(notification);
			hibernate.getTransaction().commit();
		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error("Couldn't create Request ExpiresSoonNotification", ex);
		}
	}
}
