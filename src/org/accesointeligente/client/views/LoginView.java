package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.LoginPresenter;
import org.accesointeligente.client.presenters.LoginPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class LoginView extends Composite implements LoginPresenter.Display {
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}

	@UiField HTMLPanel loginForm;
	@UiField TextBox email;
	@UiField PasswordTextBox password;
	@UiField Button login;
	@UiField Label register;
	@UiField Label loginPending;

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
	public void setDisplayMode(DisplayMode mode) {
		switch (mode) {
			case LoginForm:
				loginForm.setVisible(true);
				loginPending.setVisible(false);
				email.setFocus(true);
				break;
			case LoginPending:
				loginForm.setVisible(false);
				loginPending.setVisible(true);
				break;
		}
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
