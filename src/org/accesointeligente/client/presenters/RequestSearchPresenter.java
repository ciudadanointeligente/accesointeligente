package org.accesointeligente.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class RequestSearchPresenter extends WidgetPresenter<RequestSearchPresenter.Display> implements RequestSearchPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestSearchPresenterIface presenter);
	}

	public RequestSearchPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
	}

	@Override
	protected void onUnbind() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRevealDisplay() {
		// TODO Auto-generated method stub

	}

}
