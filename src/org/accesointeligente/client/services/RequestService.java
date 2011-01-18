package org.accesointeligente.client.services;

import org.accesointeligente.model.*;
import org.accesointeligente.shared.RequestSearchParams;
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("request")
public interface RequestService extends RemoteService {
	Request saveRequest(Request request) throws ServiceException;
	List<RequestCategory> getCategories() throws ServiceException;
	Request getRequest(Integer requestId) throws ServiceException;
	Request getRequest(String remoteIdentifier) throws ServiceException;
	List<Request> getUserRequestList(Integer offset, Integer limit) throws ServiceException;
	List<Request> getUserRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	List<Request> getUserFavoriteRequestList(Integer offset, Integer limit) throws ServiceException;
	List<Request> getUserFavoriteRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	List<Request> getRequestList(Integer offset, Integer limit) throws ServiceException;
	List<Request> getRequestList(Integer offset, Integer limit, RequestSearchParams params) throws ServiceException;
	List<Attachment> getResponseAttachmentList(Response response) throws ServiceException;
	UserFavoriteRequest getFavoriteRequest(Request request, User user) throws ServiceException;
	UserFavoriteRequest createFavoriteRequest(Request request, User user) throws ServiceException;
	void deleteFavoriteRequest(UserFavoriteRequest favorite) throws ServiceException;
	List<RequestComment> getRequestComments(Request request) throws ServiceException;
	RequestComment createRequestComment(RequestComment comment) throws ServiceException;
	void deleteRequestComment(RequestComment comment) throws ServiceException;
	Response saveResponse(Response response) throws ServiceException;
	Attachment saveAttachment(Attachment attachment) throws ServiceException;
}
