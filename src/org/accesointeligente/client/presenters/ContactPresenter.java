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
package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.services.ContactServiceAsync;
import org.accesointeligente.client.uihandlers.ContactUiHandlers;
import org.accesointeligente.model.Contact;
import org.accesointeligente.model.User;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.Date;

public class ContactPresenter extends Presenter<ContactPresenter.MyView, ContactPresenter.MyProxy> implements ContactUiHandlers {
	public interface MyView extends View, HasUiHandlers<ContactUiHandlers> {
		String getName();
		void setName(String name);
		String getEmail();
		void setEmail(String email);
		String getSubject();
		String getMessage();
		void setMessage(String message);
		void cleanContactForm();
		Boolean checkEmail();
		Boolean checkContactForm();
		Boolean checkSubject();
		void subjectAddOptions();
	}

	@ProxyCodeSplit
	@NameToken(AppPlace.CONTACT)
	public interface MyProxy extends ProxyPlace<ContactPresenter> {
	}

	@Inject
	private ContactServiceAsync contactService;

	@Inject
	public ContactPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		checkUserSession();
		getView().subjectAddOptions();
		Window.setTitle("Formulario de contacto - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void checkUserSession() {
		if (ClientSessionUtil.checkSession()) {
			User user = (User) ClientSessionUtil.getUser();
			getView().setName(user.getFirstName() + " " + user.getLastName());
			getView().setEmail(user.getEmail());
		}
	}

	@Override
	public void saveContactForm() {
		Contact contact = new Contact();
		contact.setName(getView().getName());
		contact.setEmail(getView().getEmail());
		contact.setSubject(getView().getSubject());
		contact.setMessage(getView().getMessage());
		contact.setDate(new Date());

		contactService.saveContact(contact, new AsyncCallback<Contact>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se ha podido enviar su mensaje, por favor intentelo nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Contact result) {
				showNotification("Se ha enviado su mensaje, muchas gracias por comunicarse con nosotros", NotificationEventType.SUCCESS);
				getView().cleanContactForm();
				checkUserSession();
			}
		});
	}

	@Override
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		params.setDuration(NotificationEventParams.DURATION_NORMAL);
		fireEvent(new NotificationEvent(params));
	}

	@Override
	public void subjectAddOptions() {
		getView().subjectAddOptions();
	}
}
