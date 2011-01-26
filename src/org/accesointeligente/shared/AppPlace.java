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
	ABOUTPROJECT,
	ASOCIATES,
	STATISTICS,
	CONTACT;

	public String getToken() {
		return this.toString().toLowerCase();
	}
}
