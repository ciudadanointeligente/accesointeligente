package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.LoginPresenter;
import org.accesointeligente.client.presenters.LoginPresenterIface;
import org.accesointeligente.shared.AppPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

public class LoginView extends Composite implements LoginPresenter.Display {
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}

	@UiField FocusPanel loginPanel;
	@UiField HTMLPanel noticePanel;
	@UiField Label noticeClose;
	@UiField Label noticeLabel;
	@UiField TextBox email;
	@UiField PasswordTextBox password;
	@UiField Button login;
	@UiField Label register;
	@UiField Label passwordRecovery;
	@UiField Label close;

	private Timer notificationTimer;
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

	@UiHandler("noticeClose")
	void onNoticeCloseClick(ClickEvent event) {
		noticePanel.setVisible(false);
		notificationTimer.cancel();
	}

	@UiHandler("passwordRecovery")
	void onPasswordRecoveryClick(ClickEvent event) {
		History.newItem(AppPlace.PASSWORDRECOVERY.getToken());
	}

	@Override
	public void showNotice(String message) {
		noticeLabel.setText(message);
		noticePanel.setVisible(true);
		notificationTimer = new Timer() {

			@Override
			public void run() {
				noticePanel.setVisible(false);
			}
		};
		notificationTimer.schedule(15000);
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
