package org.accesointeligente.client.uihandlers;

import org.accesointeligente.shared.NotificationEventType;
import org.accesointeligente.shared.RequestStatus;
import org.accesointeligente.shared.UserSatisfaction;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ResponseUserSatisfactionUiHandlers extends UiHandlers {
	void setResponseUserSatisfaction(UserSatisfaction userSatisfaction);
	void setRequestStatus(RequestStatus requestStatus);
	RequestStatus getRequestStatus();
	void showNotification(String message, NotificationEventType type);
}
