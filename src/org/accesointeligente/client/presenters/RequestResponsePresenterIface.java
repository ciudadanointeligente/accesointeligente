package org.accesointeligente.client.presenters;

import org.accesointeligente.client.widgets.ResponseWidget;
import org.accesointeligente.client.widgets.UserResponseWidget;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.Response;
import org.accesointeligente.shared.NotificationEventType;

public interface RequestResponsePresenterIface {
	void showRequest(Integer requestId);
	void loadComments(Request request);
	void saveComment(String commentContent);
	void loadAttachments(Response response, ResponseWidget widget);
	void saveQualification(Integer rate);
	void getUserResponse(Response response, ResponseWidget widget);
	void saveUserResponse(String information, Response response, ResponseWidget widget);
	String getListLink();
	void showNotification(String message, NotificationEventType type);
}
