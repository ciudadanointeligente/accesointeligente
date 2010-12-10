package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

import org.accesointeligente.model.Activity;
import org.accesointeligente.shared.ServiceException;

@RemoteServiceRelativePath("activity")
public interface ActivityService extends RemoteService {
	List<Activity> getActivities(Boolean isPerson) throws ServiceException;
}
