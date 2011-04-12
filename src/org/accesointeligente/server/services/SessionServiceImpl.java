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

import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.services.SessionService;
import org.accesointeligente.client.services.SessionServiceException;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.server.SessionUtil;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

public class SessionServiceImpl extends PersistentRemoteService implements SessionService {
	private static final long serialVersionUID = 2965134634476060984L;
	private PersistentBeanManager persistentBeanManager;

	public SessionServiceImpl () {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager ();
		setBeanManager(persistentBeanManager);
	}

	@Override
	public SessionData getSessionData () throws SessionServiceException {
		return (SessionData) persistentBeanManager.clone (SessionUtil.getSessionData (getThreadLocalRequest().getSession()));
	}
}
