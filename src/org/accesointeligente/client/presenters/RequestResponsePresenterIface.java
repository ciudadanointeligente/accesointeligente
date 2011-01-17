package org.accesointeligente.client.presenters;

import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestComment;
import org.accesointeligente.model.Response;

public interface RequestResponsePresenterIface {
	void showRequest(Integer requestId);
	void loadComments(Request request);
	void saveComment(String commentContent);
	void loadAttachments(Response response);
	String getListLink();
}
