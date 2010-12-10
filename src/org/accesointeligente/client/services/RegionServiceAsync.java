package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import org.accesointeligente.model.Region;

public interface RegionServiceAsync {
	void getRegions(AsyncCallback<List<Region>> callback);
}
