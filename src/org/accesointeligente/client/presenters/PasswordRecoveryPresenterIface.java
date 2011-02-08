package org.accesointeligente.client.presenters;

import org.accesointeligente.shared.NotificationEventType;

public interface PasswordRecoveryPresenterIface {
	void recoverPassword();
	void showNotification(String message, NotificationEventType type);
}
