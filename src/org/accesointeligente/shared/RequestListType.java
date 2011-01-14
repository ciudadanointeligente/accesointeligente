package org.accesointeligente.shared;

public enum RequestListType {
	MYREQUESTS,
	FAVORITES,
	GENERAL;

	public String getType() {
		return this.toString().toLowerCase();
	}
}
