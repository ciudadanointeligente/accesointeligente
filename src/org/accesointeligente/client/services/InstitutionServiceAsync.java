package org.accesointeligente.client.services;

import org.accesointeligente.model.Institution;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface InstitutionServiceAsync {
	void getInstitutions(AsyncCallback<List<Institution>> callback);
}
