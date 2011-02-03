package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.events.*;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.client.views.MainView;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;

public class MainPresenter extends WidgetPresenter<MainPresenter.Display> implements MainPresenterIface, LoginRequiredEventHandler, LoginSuccessfulEventHandler, NotificationEventHandler {
	public interface Display extends WidgetDisplay {
		void setPresenter(MainPresenterIface presenter);
		void setDisplayMode(MainView.DisplayMode mode);
		FlowPanel getLayout();
		void setWelcomeMessage(String message);
		void setNotificationMessage(NotificationEventParams params);
	}

	public MainPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		eventBus.addHandler(NotificationEvent.TYPE, this);
		tryCookieLogin();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	@Override
	public void loginRequired(LoginRequiredEvent event) {
		display.setDisplayMode(MainView.DisplayMode.LoggedOut);
		display.setWelcomeMessage("");
	}

	@Override
	public void loginSuccessful(LoginSuccessfulEvent event) {
		display.setDisplayMode(MainView.DisplayMode.LoggedIn);
		display.setWelcomeMessage("Bienvenido: " + ClientSessionUtil.getUser().getFirstName());
	}

	public void tryCookieLogin() {
		final String sessionId = Cookies.getCookie("sessionId");

		if (sessionId != null) {
			display.setDisplayMode(MainView.DisplayMode.LoginPending);

			RPC.getSessionService().getSessionData(new AsyncCallback<SessionData>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientSessionUtil.destroySession();
					eventBus.fireEvent(new LoginRequiredEvent());
				}

				@Override
				public void onSuccess(SessionData result) {
					if (sessionId.equals(result.getData().get("sessionId"))) {
						ClientSessionUtil.createSession(result);
						eventBus.fireEvent(new LoginSuccessfulEvent());
					} else {
						ClientSessionUtil.destroySession();
						eventBus.fireEvent(new LoginRequiredEvent());
					}
				}
			});
		} else {
			eventBus.fireEvent(new LoginRequiredEvent());
		}
	}

	@Override
	public void onNotification(NotificationEvent notificationEvent) {
		if (notificationEvent.getParams() != null) {
			display.setNotificationMessage(notificationEvent.getParams());
		}
	}
}
