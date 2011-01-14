package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.events.LoginSuccessfulEvent;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.shared.AppPlace;
import org.accesointeligente.shared.LoginException;
import org.accesointeligente.shared.ServiceException;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter extends WidgetPresenter<LoginPresenter.Display> implements LoginPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(LoginPresenterIface presenter);
		String getEmail();
		String getPassword();
	}

	public LoginPresenter(Display display, EventBus eventBus) {
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
	public void login() {
		String email = display.getEmail();
		String password = display.getPassword();

		if (email.length() == 0) {
			Window.alert("Debe ingresar email");
			return;
		}

		if (password.length() == 0) {
			Window.alert("Debe ingresar contraseña");
			return;
		}

		RPC.getUserService().login(email, password, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof ServiceException) {
					Window.alert("Fallo la conexion");
				} else if (caught instanceof LoginException) {
					Window.alert("Email y/o contraseña incorrecta");
				}
			}

			@Override
			public void onSuccess(Void result) {
				RPC.getSessionService ().getSessionData (new AsyncCallback<SessionData> () {
					@Override
					public void onFailure (Throwable caught) {
						Window.alert ("Error creando sesión");
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
}
