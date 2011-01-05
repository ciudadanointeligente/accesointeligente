package org.accesointeligente.client.services;

import org.accesointeligente.model.*;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface RequestServiceAsync {
	void makeRequest(Request request, AsyncCallback<Request> callback);
	void getCategories(AsyncCallback<List<RequestCategory>> callback);
	void getRequest(Integer requestId, AsyncCallback<Request> callback);
	void getUserRequestList(Integer offset, Integer limit, AsyncCallback<List<Request>> callback);
	void getUserFavoriteRequestList(Integer offset, Integer limit, AsyncCallback<List<Request>> callback);
	void getRequestList(Integer offset, Integer limit, AsyncCallback<List<Request>> callback);
	void getResponseAttachmentList(Response response, AsyncCallback<List<Attachment>> callback);
}
