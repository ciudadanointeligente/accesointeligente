package cl.votainteligente.accesointeligente.client.presenters;

import cl.votainteligente.accesointeligente.client.services.RPC;
import cl.votainteligente.accesointeligente.model.User;
import cl.votainteligente.accesointeligente.shared.LoginException;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

public class LoginPresenter extends WidgetPresenter<LoginPresenter.Display> implements LoginPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(LoginPresenterIface presenter);
		String getUsername();
		String getPassword();
		void setName(String name);
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
		String username = display.getUsername();
		String password = display.getPassword();

		if (username.length() == 0) {
			Window.alert("Debe ingresar nombre de usuario");
			return;
		}

		if (password.length() == 0) {
			Window.alert("Debe ingresar contraseña");
			return;
		}

		RPC.getUserService().login(username, password, new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof ServiceException) {
					Window.alert("Fallo la conexion");
				} else if (caught instanceof LoginException) {
					Window.alert("Nombre de usuario y/o contraseña incorrecta");
				}
			}

			@Override
			public void onSuccess(User user) {
				display.setName(user.getFirstName());
			}
		});
	}

	@Override
	public void register() {
		History.newItem("register");
	}
}
