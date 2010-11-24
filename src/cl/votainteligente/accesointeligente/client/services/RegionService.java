package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.Region;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("region")
public interface RegionService extends RemoteService {
	List<Region> getRegions() throws ServiceException;
}
