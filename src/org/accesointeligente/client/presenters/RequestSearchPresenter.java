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

import org.accesointeligente.client.services.InstitutionServiceAsync;
import org.accesointeligente.client.uihandlers.RequestSearchUiHandlers;
import org.accesointeligente.model.Institution;
import org.accesointeligente.shared.RequestSearchEvent;
import org.accesointeligente.shared.RequestSearchParams;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.*;

import javax.inject.Inject;

public class RequestSearchPresenter extends PresenterWidget<RequestSearchPresenter.MyView> implements RequestSearchUiHandlers {
	public interface MyView extends View, HasUiHandlers<RequestSearchUiHandlers> {
		void displayMessage(String message);
		void setInstitutions(Map<String, Institution> institutions);
		String getKeyWord();
		Institution getInstitution();
		Date getMinDate();
		Date getMaxDate();
		Boolean getStatusPending();
		Boolean getStatusClosed();
		Boolean getStatusExpired();
		void resetFilters();
	}

	@Inject
	private InstitutionServiceAsync institutionService;

	@Inject
	public RequestSearchPresenter(EventBus eventBus, MyView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		getView().resetFilters();
		getInstitutions();
		Window.setTitle("Listado de solicitudes - Acceso Inteligente");
	}

	@Override
	public void getInstitutions() {
		institutionService.getInstitutions(new AsyncCallback<List<Institution>>() {
			@Override
			public void onFailure(Throwable caught) {
				getView().displayMessage("No es posible recuperar las instituciones");
			}

			@Override
			public void onSuccess(List<Institution> result) {
				Map<String, Institution> institutions = new HashMap<String, Institution>();

				for (Institution institution: result) {
					institutions.put(institution.getName(), institution);
				}

				getView().setInstitutions(institutions);
			}
		});
	}

	@Override
	public void requestSearch() {
		RequestSearchParams params = new RequestSearchParams();
		params.setInstitution(getView().getInstitution());
		params.setKeyWord(getView().getKeyWord());
		params.setMinDate(getView().getMinDate());
		params.setMaxDate(getView().getMaxDate());
		params.setStatusPending(getView().getStatusPending());
		params.setStatusClosed(getView().getStatusClosed());
		params.setStatusExpired(getView().getStatusExpired());
		fireEvent(new RequestSearchEvent(params));
	}

	@Override
	public void resetSearch() {
		fireEvent(new RequestSearchEvent(null));
	}
}
