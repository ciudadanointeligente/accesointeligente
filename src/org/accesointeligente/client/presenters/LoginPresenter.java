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

import org.accesointeligente.client.AnonymousGatekeeper;
import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.events.LoginSuccessfulEvent;
import org.accesointeligente.client.services.SessionServiceAsync;
import org.accesointeligente.client.services.UserServiceAsync;
import org.accesointeligente.client.uihandlers.LoginUiHandlers;
import org.accesointeligente.shared.AppPlace;
import org.accesointeligente.shared.LoginException;
import org.accesointeligente.shared.ServiceException;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import javax.inject.Inject;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> implements LoginUiHandlers {
	public interface MyView extends PopupView, HasUiHandlers<LoginUiHandlers> {
		void clearForm();
		void showNotice(String message);
		String getEmail();
		String getPassword();
		void setEmailFocus();
	}

	@ProxyCodeSplit
	@UseGatekeeper(AnonymousGatekeeper.class)
	@NameToken(AppPlace.LOGIN)
	public interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private SessionServiceAsync sessionService;

	@Inject
	private UserServiceAsync userService;

	@Inject
	public LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReveal() {
		getView().clearForm();
		getView().setEmailFocus();
		Window.setTitle("Auntentificación - Acceso Inteligente");
	}

	@Override
	public void revealInParent() {
		fireEvent(new RevealRootPopupContentEvent(this));
	}

	@Override
	public void login() {
		String email = getView().getEmail();
		String password = getView().getPassword();

		if (email.length() == 0) {
			getView().showNotice("Debe ingresar email");
			return;
		}

		if (password.length() == 0) {
			getView().showNotice("Debe ingresar contraseña");
			return;
		}

		userService.login(email, password, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof ServiceException) {
					getView().showNotice("Fallo la conexion");
				} else if (caught instanceof LoginException) {
					getView().showNotice("Email y/o contraseña incorrecta");
				}
			}

			@Override
			public void onSuccess(Void result) {
				sessionService.getSessionData (new AsyncCallback<SessionData> () {
					@Override
					public void onFailure (Throwable caught) {
						getView().showNotice ("Error creando sesión");
					}

					@Override
					public void onSuccess (SessionData result) {
						ClientSessionUtil.createSession (result);
						fireEvent (new LoginSuccessfulEvent ());
					}
				});
			}
		});
	}

	@Override
	public void register() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.REGISTER));
	}

	@Override
	public void close() {
		placeManager.navigateBack();
	}

	@Override
	public void gotoPasswordRecovery() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.PASSWORDRECOVERY));
	}
}
