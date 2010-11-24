package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.InstitutionType;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface InstitutionTypeServiceAsync {
	void getInstitutionTypes(AsyncCallback<List<InstitutionType>> callback);
}
