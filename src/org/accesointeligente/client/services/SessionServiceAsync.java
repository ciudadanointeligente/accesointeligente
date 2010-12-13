package org.accesointeligente.client.services;

import org.accesointeligente.client.SessionData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionServiceAsync {
	void getSessionData (AsyncCallback<SessionData> callback);
}
