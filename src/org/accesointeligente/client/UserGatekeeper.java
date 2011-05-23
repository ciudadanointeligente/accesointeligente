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

import org.accesointeligente.client.events.*;

import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class UserGatekeeper implements Gatekeeper, LoginSuccessfulEventHandler, LoginRequiredEventHandler {
	private Boolean loggedIn = false;

	@Override
	public boolean canReveal() {
		return loggedIn;
	}

	@Override
	public void loginSuccessful(LoginSuccessfulEvent event) {
		loggedIn = true;
	}

	@Override
	public void loginRequired(LoginRequiredEvent event) {
		loggedIn = false;
	}
}
