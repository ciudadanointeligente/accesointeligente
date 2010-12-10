package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

import org.accesointeligente.model.Region;
import org.accesointeligente.shared.ServiceException;

@RemoteServiceRelativePath("region")
public interface RegionService extends RemoteService {
	List<Region> getRegions() throws ServiceException;
}
