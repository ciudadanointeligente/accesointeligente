package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.PasswordRecoveryPresenter;
import org.accesointeligente.client.presenters.PasswordRecoveryPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class PasswordRecoveryView extends Composite implements PasswordRecoveryPresenter.Display {
	private static PasswordRecoveryViewUiBinder uiBinder = GWT.create(PasswordRecoveryViewUiBinder.class);
	interface PasswordRecoveryViewUiBinder extends UiBinder<Widget, PasswordRecoveryView> {}

	@UiField TextBox email;
	@UiField Button sendMail;

	PasswordRecoveryPresenterIface presenter;

	public PasswordRecoveryView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(PasswordRecoveryPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public String getEmail() {
		return email.getText();
	}

	@UiHandler("sendMail")
	public void onSendMailClick(ClickEvent event) {
		if (presenter != null) {
			presenter.recoverPassword();
		}
	}
}
