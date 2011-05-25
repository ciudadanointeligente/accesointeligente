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
package org.accesointeligente.client;

import org.accesointeligente.model.User;

import com.google.gwt.user.client.Cookies;

import java.util.Date;

public class ClientSessionUtil {
	private static SessionData sessionData;

	public static void createSession(SessionData sessionData) {
		ClientSessionUtil.sessionData = sessionData;
		Cookies.removeCookie("sessionId", "/");
		final long cookieDuration = 1000 * 60 * 24; // 1 day
		Date expires = new Date(System.currentTimeMillis() + cookieDuration);
		Cookies.setCookie("sessionId", (String) sessionData.getData().get("sessionId"), expires, null, "/", false);
	}

	public static void destroySession() {
		sessionData = null;
		Cookies.removeCookie("sessionId", "/");
	}

	public static boolean checkSession() {
		return sessionData != null &&
		       sessionData.getData() != null &&
		       sessionData.getData().containsKey("sessionId") &&
		       Cookies.getCookie("sessionId") != null &&
		       Cookies.getCookie("sessionId").equals(sessionData.getData().get("sessionId"));
	}

	public static User getUser() {
		try {
			return (User) sessionData.getData().get("user");
		} catch (Exception ex) {
			return null;
		}
	}

	public static void setUser(User user) {
		sessionData.getData().put("user", user);
	}
}
