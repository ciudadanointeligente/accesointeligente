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
