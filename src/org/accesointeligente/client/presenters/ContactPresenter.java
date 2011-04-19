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
import org.accesointeligente.model.Contact;
import org.accesointeligente.model.User;
import org.accesointeligente.shared.NotificationEvent;
import org.accesointeligente.shared.NotificationEventParams;
import org.accesointeligente.shared.NotificationEventType;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.Date;

public class ContactPresenter extends CustomWidgetPresenter<ContactPresenter.Display> implements ContactPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(ContactPresenterIface presenter);
		String getName();
		void setName(String name);
		String getEmail();
		void setEmail(String email);
		String getSubject();
		void setSubject(String subject);
		String getMessage();
		void setMessage(String message);
		void cleanContactForm();
		Boolean checkEmail();
		Boolean checkContactForm();
	}

	@Inject
	public ContactPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	public void setup() {
		checkUserSession();
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
	public void checkUserSession() {
		if (ClientSessionUtil.checkSession()) {
			User user = (User) ClientSessionUtil.getUser();
			display.setName(user.getFirstName() + " " + user.getLastName());
			display.setEmail(user.getEmail());
		}
	}

	@Override
	public void saveContactForm() {
		Contact contact = new Contact();
		contact.setName(display.getName());
		contact.setEmail(display.getEmail());
		contact.setSubject(display.getSubject());
		contact.setMessage(display.getMessage());
		contact.setDate(new Date());

		serviceInjector.getContactService().saveContact(contact, new AsyncCallback<Contact>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se ha podido enviar su mensaje, por favor intentelo nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Contact result) {
				showNotification("Se ha enviado su mensaje, muchas gracias por comunicarse con nosotros", NotificationEventType.SUCCESS);
				display.cleanContactForm();
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
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
