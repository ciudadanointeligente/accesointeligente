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

import org.accesointeligente.client.inject.ServiceInjector;
import org.accesointeligente.model.Institution;
import org.accesointeligente.shared.RequestSearchEvent;
import org.accesointeligente.shared.RequestSearchParams;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.*;

public class RequestSearchPresenter extends CustomWidgetPresenter<RequestSearchPresenter.Display> implements RequestSearchPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestSearchPresenterIface presenter);
		void displayMessage(String message);
		void setInstitutions(Map<String, Institution> institutions);
		String getKeyWord();
		Institution getInstitution();
		Date getMinDate();
		Date getMaxDate();
		Boolean getStatusPending();
		Boolean getStatusClosed();
		Boolean getStatusExpired();
		Boolean getStatusDerived();
	}

	private static final ServiceInjector serviceInjector = GWT.create(ServiceInjector.class);

	@Inject
	public RequestSearchPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
		bind();
	}

	@Override
	public void setup() {
		getInstitutions();
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
	public void getInstitutions() {
		serviceInjector.getInstitutionService().getInstitutions(new AsyncCallback<List<Institution>>() {
			@Override
			public void onFailure(Throwable caught) {
				display.displayMessage("No es posible recuperar las instituciones");
			}

			@Override
			public void onSuccess(List<Institution> result) {
				Map<String, Institution> institutions = new HashMap<String, Institution>();

				for (Institution institution: result) {
					institutions.put(institution.getName(), institution);
				}

				display.setInstitutions(institutions);
			}
		});
	}

	@Override
	public void requestSearch() {
		RequestSearchParams params = new RequestSearchParams();
		params.setInstitution(display.getInstitution());
		params.setKeyWord(display.getKeyWord());
		params.setMinDate(display.getMinDate());
		params.setMaxDate(display.getMaxDate());
		params.setStatusPending(display.getStatusPending());
		params.setStatusClosed(display.getStatusClosed());
		params.setStatusExpired(display.getStatusExpired());
		params.setStatusDerived(display.getStatusDerived());
		eventBus.fireEvent(new RequestSearchEvent(params));
	}
}
