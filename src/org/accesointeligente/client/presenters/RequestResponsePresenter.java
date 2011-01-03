package org.accesointeligente.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class RequestResponsePresenter extends WidgetPresenter<RequestResponsePresenter.Display> implements RequestResponsePresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestResponsePresenterIface presenter);
	}

	public RequestResponsePresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}
}
