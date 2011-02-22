/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
		void clearNotifications();
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

	@Override
	public void clearNotifications() {
		display.clearNotifications();
	}
}
