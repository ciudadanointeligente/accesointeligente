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
package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.LoginPresenter;
import org.accesointeligente.client.uihandlers.LoginUiHandlers;

import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

import javax.inject.Inject;

public class LoginView extends PopupViewWithUiHandlers<LoginUiHandlers> implements LoginPresenter.MyView {
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);
	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {}
	private final Widget widget;

	@UiField FocusPanel loginPanel;
	@UiField FormPanel loginForm;
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

	@Inject
	protected LoginView(EventBus eventBus) {
		super(eventBus);
		widget = uiBinder.createAndBindUi(this);
		setAutoHideOnNavigationEventEnabled(true);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void clearForm() {
		loginForm.reset();
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
	public String getEmail() {
		return email.getText();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public void setEmailFocus() {
		email.setFocus(true);
	}

	@UiHandler("password")
	void onPasswordKeyDown(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			getUiHandlers().login();
		}
	}

	@UiHandler("login")
	void onLoginClick(ClickEvent event) {
		getUiHandlers().login();
	}

	@UiHandler("register")
	void onRegisterClick(ClickEvent event) {
		getUiHandlers().register();
	}

	@UiHandler("loginPanel")
	void onLoginPanelKeyDown(KeyDownEvent key) {
		if (key.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			getUiHandlers().close();
		}
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent click) {
		getUiHandlers().close();
	}

	@UiHandler("noticeClose")
	void onNoticeCloseClick(ClickEvent event) {
		noticePanel.setVisible(false);
		notificationTimer.cancel();
	}

	@UiHandler("passwordRecovery")
	void onPasswordRecoveryClick(ClickEvent event) {
		getUiHandlers().gotoPasswordRecovery();
	}
}
