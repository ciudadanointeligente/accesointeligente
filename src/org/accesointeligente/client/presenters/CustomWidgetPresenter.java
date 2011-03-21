package org.accesointeligente.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public abstract class CustomWidgetPresenter<D extends WidgetDisplay> extends WidgetPresenter<D> {
	public CustomWidgetPresenter(D display, EventBus eventBus) {
		super(display, eventBus);
	}

	public abstract void setup();

	@Override
	public D getDisplay() {
		return display;
	}
}
