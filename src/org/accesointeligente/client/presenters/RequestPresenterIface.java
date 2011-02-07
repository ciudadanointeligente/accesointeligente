package org.accesointeligente.client.presenters;

import org.accesointeligente.shared.NotificationEventType;

public interface RequestPresenterIface {
	void getRequestCategories();
	void getInstitutions();
	void submitRequest();
	void showRequest();
	void showNotification(String message, NotificationEventType type);
}
