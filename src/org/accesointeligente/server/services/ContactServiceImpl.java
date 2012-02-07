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
package org.accesointeligente.server.services;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.accesointeligente.client.services.ContactService;
import org.accesointeligente.model.Contact;
import org.accesointeligente.model.Notification;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.shared.NotificationType;
import org.accesointeligente.shared.ServiceException;

import org.hibernate.Session;

public class ContactServiceImpl extends PersistentRemoteService implements ContactService {
	private PersistentBeanManager persistentBeanManager;

	public ContactServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@Override
	public Contact saveContact(Contact contact) throws ServiceException {
		Session hibernate = null;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(contact);
			hibernate.getTransaction().commit();

			Notification notification = new Notification();
			notification.setEmail(contact.getEmail());
			notification.setSubject(String.format(ApplicationProperties.getProperty("email.contact.subject"), contact.getSubject()));
			notification.setMessage(String.format(ApplicationProperties.getProperty("email.contact.body"), contact.getName(), contact.getMessage()) + ApplicationProperties.getProperty("email.signature"));
			notification.setType(NotificationType.CONTACTFORM);
			notification.setDate(contact.getDate());

			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			hibernate.saveOrUpdate(notification);
			hibernate.getTransaction().commit();

			return contact;
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
