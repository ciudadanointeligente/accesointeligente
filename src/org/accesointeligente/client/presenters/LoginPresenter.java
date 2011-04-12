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

import org.accesointeligente.client.AppController;
import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.events.LoginSuccessfulEvent;
import org.accesointeligente.shared.AppPlace;
import org.accesointeligente.shared.LoginException;
import org.accesointeligente.shared.ServiceException;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LoginPresenter extends CustomWidgetPresenter<LoginPresenter.Display> implements LoginPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(LoginPresenterIface presenter);
		void clearForm();
		void showNotice(String message);
		String getEmail();
		String getPassword();
	}

	@Inject
	public LoginPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	public void setup() {
		display.clearForm();
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
	public void login() {
		String email = display.getEmail();
		String password = display.getPassword();

		if (email.length() == 0) {
			display.showNotice("Debe ingresar email");
			return;
		}

		if (password.length() == 0) {
			display.showNotice("Debe ingresar contraseña");
			return;
		}

		serviceInjector.getUserService().login(email, password, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof ServiceException) {
					display.showNotice("Fallo la conexion");
				} else if (caught instanceof LoginException) {
					display.showNotice("Email y/o contraseña incorrecta");
				}
			}

			@Override
			public void onSuccess(Void result) {
				serviceInjector.getSessionService ().getSessionData (new AsyncCallback<SessionData> () {
					@Override
					public void onFailure (Throwable caught) {
						display.showNotice ("Error creando sesión");
					}

					@Override
					public void onSuccess (SessionData result) {
						ClientSessionUtil.createSession (result);
						eventBus.fireEvent (new LoginSuccessfulEvent ());
					}
				});
			}
		});
	}

	@Override
	public void register() {
		History.newItem(AppPlace.REGISTER.getToken());
	}

	@Override
	public void close() {
		History.newItem(AppController.getPreviousHistoryToken());
	}
}
