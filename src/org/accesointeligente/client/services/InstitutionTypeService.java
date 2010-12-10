package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

import org.accesointeligente.model.InstitutionType;
import org.accesointeligente.shared.ServiceException;

@RemoteServiceRelativePath("institutionType")
public interface InstitutionTypeService extends RemoteService {
	List<InstitutionType> getInstitutionTypes() throws ServiceException;
}
