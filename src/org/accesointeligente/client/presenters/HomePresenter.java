package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class HomePresenter extends WidgetPresenter<HomePresenter.Display> implements HomePresenterIface {//, LoginRequiredEventHandler, LoginSuccessfulEventHandler {
	public interface Display extends WidgetDisplay {
		void setPresenter(HomePresenterIface presenter);
		void setAuthenticated(Boolean authenticated);
	}

	public HomePresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		display.setAuthenticated(ClientSessionUtil.checkSession());
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}
}
