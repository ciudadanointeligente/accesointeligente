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
