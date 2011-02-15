package org.accesointeligente.client.presenters;

import org.accesointeligente.client.widgets.ResponseWidget;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.Response;
import org.accesointeligente.shared.NotificationEventType;

public interface RequestResponsePresenterIface {
	void showRequest(Integer requestId);
	void loadComments(Request request);
	void saveComment(String commentContent);
	void loadAttachments(Response response, ResponseWidget widget);
	void saveQualification(Integer rate);
	String getListLink();
	void showNotification(String message, NotificationEventType type);
}
