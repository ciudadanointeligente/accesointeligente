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
package org.accesointeligente.client.inject;

import org.accesointeligente.client.services.*;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class ServiceModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(ActivityServiceAsync.class).in(Singleton.class);
		bind(AgeServiceAsync.class).in(Singleton.class);
		bind(InstitutionServiceAsync.class).in(Singleton.class);
		bind(InstitutionTypeServiceAsync.class).in(Singleton.class);
		bind(RegionServiceAsync.class).in(Singleton.class);
		bind(SessionServiceAsync.class).in(Singleton.class);
		bind(UserServiceAsync.class).in(Singleton.class);
		bind(RequestServiceAsync.class).in(Singleton.class);
		bind(ContactServiceAsync.class).in(Singleton.class);
	}
}
