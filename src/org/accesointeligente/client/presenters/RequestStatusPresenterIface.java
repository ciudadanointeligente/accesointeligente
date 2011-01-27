package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;

public interface RequestStatusPresenterIface {
	void showRequest(Integer requestId);
	Request getRequest();
	void setRequest(Request request);
	Boolean requestIsEditable();
}
