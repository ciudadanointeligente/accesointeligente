package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import org.accesointeligente.model.Age;

public interface AgeServiceAsync {
	void getAges(AsyncCallback<List<Age>> callback);
}
