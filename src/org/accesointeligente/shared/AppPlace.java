package org.accesointeligente.shared;

public enum AppPlace {
	HOME,
	REGISTER,
	LOGIN,
	LOGOUT,
	PASSWORDRECOVERY,
	REQUEST,
	EDITREQUEST,
	RESPONSE,
	REQUESTSTATUS,
	LIST,
	ABOUTPROJECT,
	ASOCIATES,
	STATISTICS,
	CONTACT,
	USERPROFILE;

	public String getToken() {
		return this.toString().toLowerCase();
	}
}
