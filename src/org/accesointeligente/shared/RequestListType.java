package org.accesointeligente.shared;

public enum RequestListType {
	MYREQUESTS,
	DRAFTS,
	FAVORITES,
	GENERAL;

	public String getType() {
		return this.toString().toLowerCase();
	}
}
