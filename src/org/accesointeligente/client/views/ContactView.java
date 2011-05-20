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

import org.accesointeligente.client.presenters.ContactPresenter;
import org.accesointeligente.client.presenters.ContactPresenter.Display;
import org.accesointeligente.client.presenters.ContactPresenterIface;
import org.accesointeligente.shared.NotificationEventType;
import org.accesointeligente.shared.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class ContactView extends Composite implements ContactPresenter.Display{
	private static ContactViewUiBinder uiBinder = GWT.create(ContactViewUiBinder.class);
	interface ContactViewUiBinder extends UiBinder<Widget, ContactView> {}

	@UiField FormPanel contactFormPanel;
	@UiField TextBox contactName;
	@UiField TextBox contactEmail;
	@UiField ListBox contactSubject;
	@UiField TextArea contactMessage;
	@UiField Button send;

	private ContactPresenterIface presenter;

	public ContactView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(ContactPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public String getName() {
		return contactName.getText();
	}

	@Override
	public void setName(String name) {
		contactName.setText(name);
	}

	@Override
	public String getEmail() {
		return contactEmail.getText();
	}

	@Override
	public void setEmail(String email) {
		contactEmail.setText(email);
	}

	@Override
	public String getSubject() {
		return contactSubject.getItemText(contactSubject.getSelectedIndex());
	}

	@Override
	public void subjectAddOptions() {
		contactSubject.addItem("Selecciona tu asunto", "0");
		contactSubject.addItem("Reclamo", "1");
		contactSubject.addItem("Sugerencia", "2");
		contactSubject.addItem("Consulta sobre uso del sitio", "3");
		contactSubject.addItem("Consulta sobre el funcionamiento de la ley de transparencia", "4");
	}

	@Override
	public String getMessage() {
		return contactMessage.getText();
	}

	@Override
	public void setMessage(String message) {
		contactMessage.setText(message);
	}

	@Override
	public void cleanContactForm() {
		contactFormPanel.reset();
	}

	@Override
	public Boolean checkEmail() {
		if (!Validator.validateEmail(getEmail())) {
			if (presenter != null) {
				presenter.showNotification("Dirección de correo no valida", NotificationEventType.ERROR);
			}
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkContactForm() {
		if (getName() == null || getEmail() == null || checkEmail() || !checkSubject() || getMessage() == null) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean checkSubject() {
		if (contactSubject.getSelectedIndex() < 1) {
			if (presenter != null) {
				presenter.showNotification("Asunto no válido, seleccione uno según el contexto de su mensaje", NotificationEventType.ERROR);
			}
			return false;
		}
		return true;
	}

	@UiHandler("contactEmail")
	public void onEmailBlur(BlurEvent event) {
		checkEmail();
	}

	@UiHandler("send")
	public void onSendClick(ClickEvent event) {
		if(presenter != null || checkContactForm()) {
			presenter.saveContactForm();
		}
	}

	@UiHandler("send")
	public void onKeyDown(KeyDownEvent event) {
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER || presenter != null || checkContactForm()) {
			presenter.saveContactForm();
		}
	}

	@UiHandler("contactSubject")
	public void onSubjectBlur(BlurEvent event) {
		checkSubject();
	}
}
