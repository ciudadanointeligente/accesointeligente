package org.accesointeligente.client.presenters;

import org.accesointeligente.model.User;
import org.accesointeligente.shared.NotificationEventType;

public interface UserProfileEditPresenterIface {
	User getUser();
	Boolean getPasswordOk();
	void setPasswordOk(Boolean ok);
	Boolean getUpdatePassword();
	void setUpdatePassword(Boolean update);
	void getPersonActivities();
	void getInstitutionActivities();
	void getInstitutionTypes();
	void getPersonAges();
	void getRegions();
	void checkPassword(String password);
	void showUser();
	void saveChanges();
	void showNotification(String message, NotificationEventType type);
}
