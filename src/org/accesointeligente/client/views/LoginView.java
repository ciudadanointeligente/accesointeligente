package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.LoginPresenter;
import org.accesointeligente.client.presenters.LoginPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements LoginPresenter.Display {
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}

	@UiField TextBox email;
	@UiField PasswordTextBox password;
	@UiField Button login;
	@UiField Button register;
	@UiField Label name;

	private LoginPresenterIface presenter;

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@UiHandler("login")
	void onLoginClick(ClickEvent event) {
		if (presenter != null) {
			presenter.login();
		}
	}

	@UiHandler("register")
	void onRegisterClick(ClickEvent event) {
		if (presenter != null) {
			presenter.register();
		}
	}

	@Override
	public void setPresenter(LoginPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public String getEmail() {
		return email.getText();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public void setName(String name) {
		this.name.setText("Hola " + name);
	}
}
