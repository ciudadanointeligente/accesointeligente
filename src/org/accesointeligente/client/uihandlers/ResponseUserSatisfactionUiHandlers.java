package org.accesointeligente.client.uihandlers;

import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.UiHandlers;

public interface ResponseUserSatisfactionUiHandlers extends UiHandlers {
	void updateResponse(ResponseType responseType, UserSatisfaction userSatisfaction);
	RequestStatus getRequestStatus();
	void showNotification(String message, NotificationEventType type);
}
