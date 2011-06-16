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
import org.accesointeligente.client.services.SessionServiceAsync;
import org.accesointeligente.client.uihandlers.MainUiHandlers;
import org.accesointeligente.client.views.MainView;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainPresenter.MyView, MainPresenter.MyProxy> implements MainUiHandlers, LoginRequiredEventHandler, LoginSuccessfulEventHandler, NotificationEventHandler {
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_MAIN_CONTENT = new Type<RevealContentHandler<?>>();

	public interface MyView extends View, HasUiHandlers<MainUiHandlers> {
		void setDisplayMode(MainView.DisplayMode mode);
		void setWelcomeMessage(String message);
		void setNotificationMessage(NotificationEventParams params);
		void clearNotifications();
	}

	@ProxyStandard
	public interface MyProxy extends Proxy<MainPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private SessionServiceAsync sessionService;

	@Inject
	public MainPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		addHandler(NotificationEvent.TYPE, this);
		addHandler(LoginRequiredEvent.TYPE, this);
		addHandler(LoginSuccessfulEvent.TYPE, this);
	}

	@Override
	public void onReveal() {
		tryCookieLogin();
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealRootContentEvent(this));
	}

	@Override
	public void loginRequired(LoginRequiredEvent event) {
		getView().setDisplayMode(MainView.DisplayMode.LoggedOut);
		getView().setWelcomeMessage("");
	}

	@Override
	public void loginSuccessful(LoginSuccessfulEvent event) {
		getView().setDisplayMode(MainView.DisplayMode.LoggedIn);
		getView().setWelcomeMessage("Bienvenido: " + ClientSessionUtil.getUser().getFirstName());
	}

	public void tryCookieLogin() {
		final String sessionId = Cookies.getCookie("sessionId");

		if (sessionId != null) {
			getView().setDisplayMode(MainView.DisplayMode.LoginPending);

			sessionService.getSessionData(new AsyncCallback<SessionData>() {
				@Override
				public void onFailure(Throwable caught) {
					fireEvent(new LoginRequiredEvent());
				}

				@Override
				public void onSuccess(SessionData result) {
					if (sessionId.equals(result.getData().get("sessionId"))) {
						ClientSessionUtil.createSession(result);
						fireEvent(new LoginSuccessfulEvent());
					} else {
						fireEvent(new LoginRequiredEvent());
					}
				}
			});
		} else {
			getView().setDisplayMode(MainView.DisplayMode.LoggedOut);
		}
	}

	@Override
	public void onNotification(NotificationEvent notificationEvent) {
		if (notificationEvent.getParams() != null) {
			getView().setNotificationMessage(notificationEvent.getParams());
		}
	}

	@Override
	public void clearNotifications() {
		getView().clearNotifications();
	}

	@Override
	public void gotoMyRequests() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LIST).with("type", RequestListType.MYREQUESTS.getType()));
	}

	@Override
	public void gotoDrafts() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LIST).with("type", RequestListType.DRAFTS.getType()));
	}

	@Override
	public void gotoFavorites() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LIST).with("type", RequestListType.FAVORITES.getType()));
	}

	@Override
	public void gotoProfile() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.USERPROFILE));
	}

	@Override
	public void gotoLogout() {
		fireEvent(new LoginRequiredEvent());
	}

	@Override
	public void gotoStatistics() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.STATISTICS));
	}

	@Override
	public void gotoAboutProject() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.ABOUTPROJECT));
	}

	@Override
	public void gotoContact() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.CONTACT));
	}

	@Override
	public void gotoLogin() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LOGIN));
	}

	@Override
	public void gotoHome() {
		placeManager.revealDefaultPlace();
	}
}
