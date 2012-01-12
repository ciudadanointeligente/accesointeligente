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

import org.accesointeligente.client.services.UserServiceAsync;
import org.accesointeligente.client.uihandlers.PasswordRecoveryUiHandlers;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import javax.inject.Inject;

public class PasswordRecoveryPresenter extends Presenter<PasswordRecoveryPresenter.MyView, PasswordRecoveryPresenter.MyProxy> implements PasswordRecoveryUiHandlers {
	public interface MyView extends View, HasUiHandlers<PasswordRecoveryUiHandlers> {
		String getEmail();
	}

	@ProxyCodeSplit
	@NameToken(AppPlace.PASSWORDRECOVERY)
	public interface MyProxy extends ProxyPlace<PasswordRecoveryPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private UserServiceAsync userService;

	@Inject
	public PasswordRecoveryPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
		Window.setTitle("Recuperación de contraseña - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void recoverPassword() {
		userService.checkEmailExistence(getView().getEmail(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					showNotification("La dirección de correo ingresada no existe en nuestra base de datos", NotificationEventType.NOTICE);
				} else {
					userService.resetPassword(getView().getEmail(), new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
						}

						@Override
						public void onSuccess(Void result) {
							showNotification("Se ha actualizado su contraseña. Dentro de la brevedad le será enviado un correo con su nueva contraseña", NotificationEventType.SUCCESS);
							placeManager.revealDefaultPlace();
						}
					});
				}
			}
		});
	}

	@Override
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		params.setDuration(NotificationEventParams.DURATION_NORMAL);
		fireEvent(new NotificationEvent(params));
	}
}
