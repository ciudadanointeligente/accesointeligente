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

import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.services.SessionServiceException;
import org.accesointeligente.model.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
	private static HttpSession session;

	public static SessionData getSessionData () throws SessionServiceException {
		if (session == null) {
			throw new SessionServiceException ();
		}

		String sessionId = (String) session.getAttribute ("sessionId");
		User user = (User) session.getAttribute ("user");

		if (sessionId == null || user == null) {
			throw new SessionServiceException ();
		} else {
			SessionData sessionData = new SessionData ();
			sessionData.getData ().put ("sessionId", sessionId);
			sessionData.getData ().put ("user", user);
			return sessionData;
		}
	}

	public static Object getAttribute (String name) {
		if (session != null) {
			return session.getAttribute (name);
		} else {
			return null;
		}
	}

	public static void setAttribute (String name, Object value) {
		if (session != null) {
			session.setAttribute (name, value);
		}
	}

	public static void setSession (HttpSession session) {
		SessionUtil.session = session;
	}

	public static HttpSession getSession () {
		return session;
	}

	public static User getUser () {
		return (User) session.getAttribute ("user");
	}
}
