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

import org.accesointeligente.client.events.LoginRequiredEvent;
import org.accesointeligente.client.events.LoginSuccessfulEvent;
import org.accesointeligente.client.inject.AppInjector;

import com.gwtplatform.mvp.client.DelayedBindRegistry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;

public class AccesoInteligente implements EntryPoint {
	public final AppInjector appInjector = GWT.create(AppInjector.class);

	@Override
	public void onModuleLoad() {
		DelayedBindRegistry.bind(appInjector);
		EventBus eventBus = appInjector.getEventBus();
		UserGatekeeper userGatekeeper = appInjector.getUserGatekeeper();
		eventBus.addHandler(LoginSuccessfulEvent.TYPE, userGatekeeper);
		eventBus.addHandler(LoginRequiredEvent.TYPE, userGatekeeper);
		appInjector.getPlaceManager().revealCurrentPlace();
	}
}
