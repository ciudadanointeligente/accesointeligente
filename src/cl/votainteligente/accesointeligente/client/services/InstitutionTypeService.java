package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.InstitutionType;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("institutionType")
public interface InstitutionTypeService extends RemoteService {
	List<InstitutionType> getInstitutionTypes() throws ServiceException;
}
