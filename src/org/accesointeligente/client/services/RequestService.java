package org.accesointeligente.client.services;

import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("request")
public interface RequestService extends RemoteService {
	void makeRequest(Request request) throws ServiceException;
	List<RequestCategory> getCategories() throws ServiceException;
}
