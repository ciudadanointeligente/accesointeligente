package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;
import org.accesointeligente.shared.NotificationEventType;
import org.accesointeligente.shared.RequestSearchParams;

public interface RequestListPresenterIface {
	void loadRequests(Integer offset, Integer limit, String type);
	void loadRequests(Integer offset, Integer limit, String type, RequestSearchParams params);
	void requestToggleFavorite(Request request);
	void showNotification(String message, NotificationEventType type);
	void showRequest(Integer requestId);
}
