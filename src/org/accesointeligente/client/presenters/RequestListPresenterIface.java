package org.accesointeligente.client.presenters;

public interface RequestListPresenterIface {
	void loadRequests(Integer offset, Integer limit, String type);
	void showRequest(Integer requestId);
}
