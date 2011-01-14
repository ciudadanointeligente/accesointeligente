package org.accesointeligente.shared;

public enum AppPlace {
	HOME,
	REGISTER,
	LOGIN,
	LOGOUT,
	REQUEST,
	RESPONSE,
	REQUESTSTATUS,
	LIST,
	ABOUT,
	CONTACT;

	public String getToken() {
		return this.toString().toLowerCase();
	}
}
