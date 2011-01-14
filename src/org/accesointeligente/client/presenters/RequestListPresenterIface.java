package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;

public interface RequestListPresenterIface {
	void loadRequests(Integer offset, Integer limit, String type);
	void requestToggleFavorite(Request request);
	void showRequest(Integer requestId);
}
