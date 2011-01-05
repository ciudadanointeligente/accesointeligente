package org.accesointeligente.client.services;

import org.accesointeligente.model.*;
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("request")
public interface RequestService extends RemoteService {
	Request makeRequest(Request request) throws ServiceException;
	List<RequestCategory> getCategories() throws ServiceException;
	Request getRequest(Integer requestId) throws ServiceException;
	List<Request> getUserRequestList(Integer offset, Integer limit) throws ServiceException;
	List<Request> getUserFavoriteRequestList(Integer offset, Integer limit) throws ServiceException;
	List<Request> getRequestList(Integer offset, Integer limit) throws ServiceException;
	List<Attachment> getResponseAttachmentList(Response response) throws ServiceException;
}
