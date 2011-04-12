package org.accesointeligente.client.presenters;

import org.accesointeligente.client.AppController;
import org.accesointeligente.client.inject.PresenterInjector;
import org.accesointeligente.client.inject.ServiceInjector;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public abstract class CustomWidgetPresenter<D extends WidgetDisplay> extends WidgetPresenter<D> {
	PresenterInjector presenterInjector;
	ServiceInjector serviceInjector;

	public CustomWidgetPresenter(D display, EventBus eventBus) {
		super(display, eventBus);
		serviceInjector = AppController.getServiceInjector();
		presenterInjector = AppController.getPresenterInjector();
	}

	public abstract void setup();

	@Override
	public D getDisplay() {
		return display;
	}
}
