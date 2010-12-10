package org.accesointeligente.client.services;

import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Set;

@RemoteServiceRelativePath("request")
public interface RequestService extends RemoteService {
	void makeRequest(Request request) throws ServiceException;
	Set<RequestCategory> getCategories() throws ServiceException;
}
