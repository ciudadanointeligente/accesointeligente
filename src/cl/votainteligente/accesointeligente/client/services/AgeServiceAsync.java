package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.Age;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface AgeServiceAsync {
	void getAges(AsyncCallback<List<Age>> callback);
}
