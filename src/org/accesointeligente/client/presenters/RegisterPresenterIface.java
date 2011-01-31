package org.accesointeligente.client.presenters;

import org.accesointeligente.shared.NotificationEventType;

public interface RegisterPresenterIface {
	void getPersonActivities();
	void getInstitutionActivities();
	void getInstitutionTypes();
	void getPersonAges();
	void getRegions();
	void register();
	void showNotification(String message, NotificationEventType type);
}
