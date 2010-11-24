package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.Activity;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("activity")
public interface ActivityService extends RemoteService {
	List<Activity> getActivities(Boolean isPerson) throws ServiceException;
}
