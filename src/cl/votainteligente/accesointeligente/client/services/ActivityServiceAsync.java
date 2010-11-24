package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.Activity;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ActivityServiceAsync {
	void getActivities(Boolean isPerson, AsyncCallback<List<Activity>> callback);
}
