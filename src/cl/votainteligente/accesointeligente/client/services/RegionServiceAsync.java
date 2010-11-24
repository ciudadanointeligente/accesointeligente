package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.Region;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface RegionServiceAsync {
	void getRegions(AsyncCallback<List<Region>> callback);
}
