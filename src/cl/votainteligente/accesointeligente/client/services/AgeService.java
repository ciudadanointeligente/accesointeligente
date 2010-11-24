package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.Age;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("age")
public interface AgeService extends RemoteService {
	public List<Age> getAges() throws ServiceException;
}
