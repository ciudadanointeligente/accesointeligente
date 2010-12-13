package org.accesointeligente.client.services;

import org.accesointeligente.client.SessionData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath ("session")
public interface SessionService extends RemoteService {
	public SessionData getSessionData () throws SessionServiceException;
}
