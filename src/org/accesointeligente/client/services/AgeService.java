package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

import org.accesointeligente.model.Age;
import org.accesointeligente.shared.ServiceException;

@RemoteServiceRelativePath("age")
public interface AgeService extends RemoteService {
	public List<Age> getAges() throws ServiceException;
}
