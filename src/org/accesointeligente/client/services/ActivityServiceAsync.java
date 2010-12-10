package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import org.accesointeligente.model.Activity;

public interface ActivityServiceAsync {
	void getActivities(Boolean isPerson, AsyncCallback<List<Activity>> callback);
}
