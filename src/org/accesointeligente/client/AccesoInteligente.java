package org.accesointeligente.client;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.core.client.EntryPoint;

public class AccesoInteligente implements EntryPoint {
	@Override
	public void onModuleLoad() {
		EventBus eventBus = new DefaultEventBus();
		AppController appController = new AppController(eventBus);
		appController.setup();
	}
}
