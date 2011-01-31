package org.accesointeligente.client.services;

import org.accesointeligente.model.*;
import org.accesointeligente.shared.RequestSearchParams;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface RequestServiceAsync {
	void saveRequest(Request request, AsyncCallback<Request> callback);
	void deleteRequest(Request request, AsyncCallback<Void> callback);
	void getCategories(AsyncCallback<List<RequestCategory>> callback);
	void getRequest(Integer requestId, AsyncCallback<Request> callback);
	void getRequest(String remoteIdentifier, AsyncCallback<Request> callback);
	void getUserRequestList(Integer offset, Integer limit, AsyncCallback<List<Request>> callback);
	void getUserRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<List<Request>> callback);
	void getUserFavoriteRequestList(Integer offset, Integer limit, AsyncCallback<List<Request>> callback);
	void getUserFavoriteRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<List<Request>> callback);
	void getRequestList(Integer offset, Integer limit, AsyncCallback<List<Request>> callback);
	void getRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<List<Request>> callback);
	void getResponseAttachmentList(Response response, AsyncCallback<List<Attachment>> callback);
	void getFavoriteRequest(Request request, User user, AsyncCallback<UserFavoriteRequest> callback);
	void createFavoriteRequest(Request request, User user, AsyncCallback<UserFavoriteRequest> callback);
	void deleteFavoriteRequest(UserFavoriteRequest favorite, AsyncCallback<Void> callback);
	void getRequestComments(Request request, AsyncCallback<List<RequestComment>> callback);
	void createRequestComment(RequestComment comment, AsyncCallback<RequestComment> callback);
	void deleteRequestComment(RequestComment comment, AsyncCallback<Void> callback);
	void saveResponse(Response response, AsyncCallback<Response> callback);
	void saveAttachment(Attachment attachment, AsyncCallback<Attachment> callback);
}
