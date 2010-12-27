package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.presenters.MainPresenter.Display.DisplayMode;
import org.accesointeligente.client.services.RPC;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MainPresenter extends WidgetPresenter<MainPresenter.Display> implements MainPresenterIface {
	public interface Display extends WidgetDisplay {
		public enum DisplayMode {
			LoggedIn,
			LoggedOut,
			LoginPending
		}

		public void setDisplayMode(DisplayMode mode);
		void setPresenter(MainPresenterIface presenter);
	}

	public MainPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		tryCookieLogin();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	public void tryCookieLogin() {
		final String sessionId = Cookies.getCookie("sessionId");

		if (sessionId != null) {
			display.setDisplayMode(DisplayMode.LoginPending);

			RPC.getSessionService().getSessionData(new AsyncCallback<SessionData>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientSessionUtil.destroySession();
					display.setDisplayMode(DisplayMode.LoggedOut);
				}

				@Override
				public void onSuccess(SessionData result) {
					if (sessionId.equals(result.getData().get("sessionId"))) {
						ClientSessionUtil.createSession(result);
						display.setDisplayMode(DisplayMode.LoggedIn);
					} else {
						ClientSessionUtil.destroySession();
						display.setDisplayMode(DisplayMode.LoggedOut);
					}
				}
			});
		} else {
			display.setDisplayMode(DisplayMode.LoggedOut);
		}
	}
}
