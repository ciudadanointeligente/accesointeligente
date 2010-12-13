package org.accesointeligente.client;

import org.accesointeligente.model.User;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

public class ClientSessionUtil {
	private static SessionData sessionData;

	public static void createSession (SessionData sessionData) {
		ClientSessionUtil.sessionData = sessionData;
		Cookies.removeCookie ("sessionId", "/");
		final long cookieDuration = 1000 * 60 * 24; // 1 day
		Date expires = new Date (System.currentTimeMillis () + cookieDuration);
		Cookies.setCookie ("sessionId", (String) sessionData.getData ().get ("sessionId"), expires, null, "/", false);
	}

	public static void destroySession () {
		sessionData = null;
		Cookies.removeCookie ("sessionId", "/");
	}

	public static boolean checkSession () {
		return sessionData != null &&
		       sessionData.getData () != null &&
		       sessionData.getData ().containsKey ("sessionId") &&
		       Cookies.getCookie ("sessionId") != null &&
		       Cookies.getCookie ("sessionId").equals (sessionData.getData ().get ("sessionId"));
	}

	public static User getUser () {
		return (User) sessionData.getData ().get ("user");
	}
}
