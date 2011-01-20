package org.accesointeligente.shared;

public enum NotificationEventType {
	NOTICE,
	ERROR,
	SUCCESS;

	public String getType() {
		return this.toString().toLowerCase();
	}
}
