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
package org.accesointeligente.client;

import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;

import java.util.*;

import javax.inject.Inject;

public class PlaceHistory implements NavigationHandler {
	private EventBus eventBus;
	private PlaceManager placeManager;
	private List<PlaceRequest> history;

	@Inject
	public PlaceHistory(EventBus eventBus, PlaceManager placeManager) {
		this.eventBus = eventBus;
		this.placeManager = placeManager;
		history = new ArrayList<PlaceRequest>();
		eventBus.addHandler(NavigationEvent.getType(), this);
	}

	@Override
	public void onNavigation(NavigationEvent navigationEvent) {
		PlaceRequest placeRequest = navigationEvent.getRequest();
		history.add(0, placeRequest);
		String token = placeManager.buildHistoryToken(placeRequest);
		Set<String> parameterNames = new HashSet<String>();
		String value = null;

		if (token != null && token.length() > 0) {
			parameterNames = placeRequest.getParameterNames();
			for (String paramenterName : parameterNames) {
				value = placeRequest.getParameter(paramenterName, null);
				if (value != null) {
					token += ";" + paramenterName + "=" + value;
				}
			}
			trackHit(token);
		}
	}

	public List<PlaceRequest> getHistory() {
		return history;
	}

	public native void trackHit(String pageName) /*-{
		$wnd._gaq.push(['_trackPageview', pageName]);
	}-*/;
}
