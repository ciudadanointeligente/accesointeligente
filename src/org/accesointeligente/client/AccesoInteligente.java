package org.accesointeligente.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;

public class AccesoInteligente implements EntryPoint {
	@Override
	public void onModuleLoad() {
		EventBus eventBus = new DefaultEventBus();
		AppController appController = new AppController(eventBus);
		RootPanel.get().add(appController.getLayout());
	}
}
