package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;
import org.accesointeligente.shared.NotificationEventType;

public interface RequestEditPresenterIface {
	void getRequestCategories(Request request);
	void getInstitutions();
	void submitRequest();
	void showRequest();
	void showRequest(Integer requestId);
	void showNotification(String message, NotificationEventType type);
}
