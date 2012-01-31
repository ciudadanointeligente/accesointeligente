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
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("request")
public interface RequestService extends RemoteService {
	Request saveRequest(Request request) throws ServiceException;
	void deleteRequest(Request request) throws ServiceException;
	List<RequestCategory> getCategories() throws ServiceException;
	Request getRequest(Integer requestId) throws ServiceException;
	Request getRequest(String remoteIdentifier) throws ServiceException;
	Page<Request> getUserRequestList(Integer offset, Integer limit) throws ServiceException;
	Page<Request> getUserRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	Page<Request> getUserFavoriteRequestList(Integer offset, Integer limit) throws ServiceException;
	Page<Request> getUserFavoriteRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	Page<Request> getUserDraftList(Integer offset, Integer limit) throws ServiceException;
	Page<Request> getRequestList(Integer offset, Integer limit) throws ServiceException;
	Page<Request> getRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	Page<Request> getSolrRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	List<Attachment> getResponseAttachmentList(Response response) throws ServiceException;
	UserFavoriteRequest getFavoriteRequest(Request request, User user) throws ServiceException;
	UserFavoriteRequest createFavoriteRequest(Request request, User user) throws ServiceException;
	void deleteFavoriteRequest(UserFavoriteRequest favorite) throws ServiceException;
	List<RequestComment> getRequestComments(Request request) throws ServiceException;
	RequestComment createRequestComment(RequestComment comment) throws ServiceException;
	void deleteRequestComment(RequestComment comment) throws ServiceException;
	Response saveResponse(Response response) throws ServiceException;
	void deleteResponse(Response response) throws ServiceException;
	Attachment saveAttachment(Attachment attachment) throws ServiceException;
	void deleteAttachment(Attachment attachment) throws ServiceException;
	UserRequestQualification saveUserRequestQualification(UserRequestQualification qualification) throws ServiceException;
	Request updateRequestQualification(Request request) throws ServiceException;
	UserResponse saveUserResponse(UserResponse userResponse) throws ServiceException;
	UserResponse getUserResponse(Response response) throws ServiceException;
	List<Request> getBestVotedRequests() throws ServiceException;
	List<Request> getLastResponseRequests() throws ServiceException;
	Request setRequestUserSatisfaction(Request request) throws ServiceException;
	Response getResponse(Integer responseId, String responseKey) throws ServiceException;
	Request getRequestByResponseId(Integer responseId) throws ServiceException;
}
