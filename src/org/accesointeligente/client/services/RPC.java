package org.accesointeligente.client.services;

import com.google.gwt.core.client.GWT;

public class RPC {
	private static ActivityServiceAsync activityService = null;
	private static AgeServiceAsync ageService = null;
	private static InstitutionTypeServiceAsync institutionTypeService = null;
	private static RegionServiceAsync regionService = null;
	private static UserServiceAsync userService = null;
	private static RequestServiceAsync requestService = null;

	protected RPC() {
	}

	public static ActivityServiceAsync getActivityService() {
		if (activityService == null) {
			activityService = GWT.create(ActivityService.class);
		}

		return activityService;
	}

	public static AgeServiceAsync getAgeService() {
		if (ageService == null) {
			ageService = GWT.create(AgeService.class);
		}

		return ageService;
	}

	public static InstitutionTypeServiceAsync getInstitutionTypeService() {
		if (institutionTypeService == null) {
			institutionTypeService = GWT.create(InstitutionTypeService.class);
		}

		return institutionTypeService;
	}

	public static RegionServiceAsync getRegionService() {
		if (regionService == null) {
			regionService = GWT.create(RegionService.class);
		}

		return regionService;
	}

	public static UserServiceAsync getUserService() {
		if (userService == null) {
			userService = GWT.create(UserService.class);
		}

		return userService;
	}

	public static RequestServiceAsync getRequestService() {
		if (requestService == null) {
			requestService = GWT.create(RequestService.class);
		}

		return requestService;
	}
}
