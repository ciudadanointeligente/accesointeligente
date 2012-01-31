/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.client.services;

import org.accesointeligente.model.*;
import org.accesointeligente.shared.Page;
import org.accesointeligente.shared.RequestSearchParams;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface RequestServiceAsync {
	void saveRequest(Request request, AsyncCallback<Request> callback);
	void deleteRequest(Request request, AsyncCallback<Void> callback);
	void getCategories(AsyncCallback<List<RequestCategory>> callback);
	void getRequest(Integer requestId, AsyncCallback<Request> callback);
	void getRequest(String remoteIdentifier, AsyncCallback<Request> callback);
	void getUserRequestList(Integer offset, Integer limit, AsyncCallback<Page<Request>> callback);
	void getUserRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<Page<Request>> callback);
	void getUserFavoriteRequestList(Integer offset, Integer limit, AsyncCallback<Page<Request>> callback);
	void getUserFavoriteRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<Page<Request>> callback);
	void getUserDraftList(Integer offset, Integer limit, AsyncCallback<Page<Request>> callback);
	void getRequestList(Integer offset, Integer limit, AsyncCallback<Page<Request>> callback);
	void getRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<Page<Request>> callback);
	void getSolrRequestList(Integer offset, Integer limit, RequestSearchParams params, AsyncCallback<Page<Request>> callback);
	void getResponseAttachmentList(Response response, AsyncCallback<List<Attachment>> callback);
	void getFavoriteRequest(Request request, User user, AsyncCallback<UserFavoriteRequest> callback);
	void createFavoriteRequest(Request request, User user, AsyncCallback<UserFavoriteRequest> callback);
	void deleteFavoriteRequest(UserFavoriteRequest favorite, AsyncCallback<Void> callback);
	void getRequestComments(Request request, AsyncCallback<List<RequestComment>> callback);
	void createRequestComment(RequestComment comment, AsyncCallback<RequestComment> callback);
	void deleteRequestComment(RequestComment comment, AsyncCallback<Void> callback);
	void saveResponse(Response response, AsyncCallback<Response> callback);
	void deleteResponse(Response response, AsyncCallback<Void> callback);
	void saveAttachment(Attachment attachment, AsyncCallback<Attachment> callback);
	void deleteAttachment(Attachment attachment, AsyncCallback<Void> callback);
	void saveUserRequestQualification(UserRequestQualification qualification, AsyncCallback<UserRequestQualification> callback);
	void updateRequestQualification(Request request, AsyncCallback<Request> callback);
	void saveUserResponse(UserResponse userResponse, AsyncCallback<UserResponse> callback);
	void getUserResponse(Response response, AsyncCallback<UserResponse> callback);
	void getBestVotedRequests(AsyncCallback<List<Request>> callback);
	void getLastResponseRequests(AsyncCallback<List<Request>> callback);
	void setRequestUserSatisfaction(Request request, AsyncCallback<Request> callback);
	void getResponse(Integer responseId, String responseKey, AsyncCallback<Response> callback);
	void getRequestByResponseId(Integer responseId, AsyncCallback<Request> callback);
}
