package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;
import org.accesointeligente.shared.NotificationEventType;

public interface RequestStatusPresenterIface {
	void showRequest(Integer requestId);
	void deleteRequest();
	Request getRequest();
	void setRequest(Request request);
	Boolean requestIsEditable();
	void showNotification(String message, NotificationEventType type);
}
