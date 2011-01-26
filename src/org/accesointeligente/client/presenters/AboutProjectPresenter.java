package org.accesointeligente.client.presenters;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class AboutProjectPresenter extends WidgetPresenter<AboutProjectPresenter.Display> implements AboutProjectPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(AboutProjectPresenterIface presenter);
	}

	public AboutProjectPresenter(Display display, EventBus eventBus) {
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
