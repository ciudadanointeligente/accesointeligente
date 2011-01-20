package org.accesointeligente.shared;

public class NotificationEventParams {

	private NotificationEventType type;
	private String message;

	public NotificationEventType getType() {
		return type;
	}

	public void setType(NotificationEventType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
