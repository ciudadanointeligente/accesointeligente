package org.accesointeligente.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class RequestPresenter extends WidgetPresenter<RequestPresenter.Display> implements RequestPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestPresenterIface presenter);
	}

	public RequestPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}
}
