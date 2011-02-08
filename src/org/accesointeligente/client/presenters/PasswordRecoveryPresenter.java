package org.accesointeligente.client.presenters;

import org.accesointeligente.client.services.RPC;
import org.accesointeligente.shared.NotificationEvent;
import org.accesointeligente.shared.NotificationEventParams;
import org.accesointeligente.shared.NotificationEventType;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class PasswordRecoveryPresenter extends WidgetPresenter<PasswordRecoveryPresenter.Display> implements PasswordRecoveryPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(PasswordRecoveryPresenterIface presenter);
		String getEmail();
	}

	public PasswordRecoveryPresenter(Display display, EventBus eventBus) {
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

	@Override
	public void recoverPassword() {
		RPC.getUserService().checkEmailExistence(display.getEmail(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					showNotification("La dirección de correo ingresada no existe en nuestra base de datos", NotificationEventType.NOTICE);
				} else {
					RPC.getUserService().resetPassword(display.getEmail(), new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
						}

						@Override
						public void onSuccess(Void result) {
							showNotification("Se ha actualizado su contraseña. Dentro de la brevedad le será enviado un correo con su nueva contraseña",
									NotificationEventType.SUCCESS);
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
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
