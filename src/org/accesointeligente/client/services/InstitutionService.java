package org.accesointeligente.client.services;

import org.accesointeligente.model.Institution;
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("institution")
public interface InstitutionService extends RemoteService {
	List<Institution> getInstitutions() throws ServiceException;
}
