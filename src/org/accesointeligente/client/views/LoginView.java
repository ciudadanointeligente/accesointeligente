package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.LoginPresenter;
import org.accesointeligente.client.presenters.LoginPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class LoginView extends Composite implements LoginPresenter.Display {
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}

	@UiField FocusPanel loginPanel;
	@UiField TextBox email;
	@UiField PasswordTextBox password;
	@UiField Button login;
	@UiField Label register;
	@UiField Label close;

	private LoginPresenterIface presenter;

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@UiHandler("password")
	void onPasswordKeyDown(KeyDownEvent event) {
		if (presenter != null && (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)) {
			presenter.login();
		}
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

	@UiHandler("loginPanel")
	void onLoginPanelKeyDown(KeyDownEvent key) {
		if(key.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			presenter.close();
		}
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent click) {
		presenter.close();
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
}
