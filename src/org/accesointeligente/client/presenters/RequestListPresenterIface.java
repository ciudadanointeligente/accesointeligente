package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;
import org.accesointeligente.model.UserFavoriteRequest;

public interface RequestListPresenterIface {
	void loadRequests(Integer offset, Integer limit, String type);
	void requestToggleFavorite(Request request);
	void showRequest(Integer requestId);
}
