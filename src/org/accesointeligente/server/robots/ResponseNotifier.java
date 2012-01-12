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
package org.accesointeligente.server.robots;

import org.accesointeligente.model.Response;
import org.accesointeligente.model.User;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.Emailer;
import org.accesointeligente.server.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ResponseNotifier {
	private static final Logger logger = Logger.getLogger(ResponseNotifier.class);

	public void notifyResponses() {
		Session hibernate = null;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Response.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("request", FetchMode.JOIN);
			criteria.setFetchMode("request.user", FetchMode.JOIN);
			criteria.add(Restrictions.eq("notified", false));
			List<Response> responses = criteria.list();
			for (Response response : responses) {
				Hibernate.initialize(response.getRequest());
			}
			hibernate.getTransaction().commit();

			for (Response response : responses) {
				logger.info("responseId = " + response.getId());

				try {
					User user = response.getRequest().getUser();
					Emailer emailer = new Emailer();
					emailer.setRecipient(user.getEmail());
					emailer.setSubject(ApplicationProperties.getProperty("email.response.arrived.subject"));
					emailer.setBody(String.format(ApplicationProperties.getProperty("email.response.arrived.body"), user.getFirstName(), ApplicationProperties.getProperty("request.baseurl"), response.getRequest().getId(), response.getRequest().getTitle()) + ApplicationProperties.getProperty("email.signature"));
					emailer.connectAndSend();
					response.setNotified(true);

					hibernate = HibernateUtil.getSession();
					hibernate.beginTransaction();
					hibernate.update(response);
					hibernate.getTransaction().commit();
				} catch (Exception ex) {
					if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
						hibernate.getTransaction().rollback();
					}

					logger.error("responseId = " + response.getId(), ex);
				}
			}
		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error("Failure", ex);
		}
	}
}
