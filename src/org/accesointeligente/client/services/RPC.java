/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.client.services;

import com.google.gwt.core.client.GWT;

public class RPC {
	private static ActivityServiceAsync activityService = null;
	private static AgeServiceAsync ageService = null;
	private static InstitutionServiceAsync institutionService = null;
	private static InstitutionTypeServiceAsync institutionTypeService = null;
	private static RegionServiceAsync regionService = null;
	private static SessionServiceAsync sessionService = null;
	private static UserServiceAsync userService = null;
	private static RequestServiceAsync requestService = null;
	private static ContactServiceAsync contactService = null;

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

	public static InstitutionServiceAsync getInstitutionService() {
		if (institutionService == null) {
			institutionService = GWT.create(InstitutionService.class);
		}

		return institutionService;
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

	public static SessionServiceAsync getSessionService() {
		if (sessionService == null) {
			sessionService = GWT.create(SessionService.class);
		}

		return sessionService;
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

	public static ContactServiceAsync getContactService() {
		if (contactService == null) {
			contactService = GWT.create(ContactService.class);
		}

		return contactService;
	}
}
