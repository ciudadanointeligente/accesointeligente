package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Response;

public interface RequestResponsePresenterIface {
	void loadAttachments(Response response);
	void showRequest(Integer requestId);
	String getListLink();
}
