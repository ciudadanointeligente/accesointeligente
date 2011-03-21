package org.accesointeligente.client.inject;

import org.accesointeligente.client.services.*;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(ServiceModule.class)
public interface ServiceInjector extends Ginjector {
	public ActivityServiceAsync getActivityService();
	public AgeServiceAsync getAgeService();
	public InstitutionServiceAsync getInstitutionService();
	public InstitutionTypeServiceAsync getInstitutionTypeService();
	public RegionServiceAsync getRegionService();
	public SessionServiceAsync getSessionService();
	public UserServiceAsync getUserService();
	public RequestServiceAsync getRequestService();
	public ContactServiceAsync getContactService();
}
