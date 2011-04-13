package org.accesointeligente.client.presenters;

import org.accesointeligente.client.AppController;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;

public class UserGuideVideoPresenter extends CustomWidgetPresenter<UserGuideVideoPresenter.Display> implements UserGuideVideoPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(UserGuideVideoPresenterIface presenter);
		void setVideo(String url);
	}

	private static final String USERGUIDEVIDEO = "video/userguide.mp4";

	@Inject
	public UserGuideVideoPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	public void setup() {
		display.setVideo(USERGUIDEVIDEO);
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

	@Override
	public void close() {
		History.newItem(AppController.getPreviousHistoryToken());
	}
}
