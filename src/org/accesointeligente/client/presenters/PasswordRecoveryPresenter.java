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

import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class PasswordRecoveryPresenter extends CustomWidgetPresenter<PasswordRecoveryPresenter.Display> implements PasswordRecoveryPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(PasswordRecoveryPresenterIface presenter);
		String getEmail();
	}

	@Inject
	public PasswordRecoveryPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	public void setup() {
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
	public void recoverPassword() {
		serviceInjector.getUserService().checkEmailExistence(display.getEmail(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					showNotification("La dirección de correo ingresada no existe en nuestra base de datos", NotificationEventType.NOTICE);
				} else {
					serviceInjector.getUserService().resetPassword(display.getEmail(), new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
						}

						@Override
						public void onSuccess(Void result) {
							showNotification("Se ha actualizado su contraseña. Dentro de la brevedad le será enviado un correo con su nueva contraseña",
									NotificationEventType.SUCCESS);
							History.newItem(AppPlace.HOME.getToken());
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
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
