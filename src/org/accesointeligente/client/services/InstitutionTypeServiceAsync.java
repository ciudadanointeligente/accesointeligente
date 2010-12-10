package org.accesointeligente.client.services;


import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

import org.accesointeligente.model.InstitutionType;

public interface InstitutionTypeServiceAsync {
	void getInstitutionTypes(AsyncCallback<List<InstitutionType>> callback);
}
