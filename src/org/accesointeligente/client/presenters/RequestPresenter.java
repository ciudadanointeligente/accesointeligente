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
import org.accesointeligente.client.UserGatekeeper;
import org.accesointeligente.client.services.InstitutionServiceAsync;
import org.accesointeligente.client.services.RequestServiceAsync;
import org.accesointeligente.client.uihandlers.RequestUiHandlers;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.*;

import java.util.*;

import javax.inject.Inject;

public class RequestPresenter extends Presenter<RequestPresenter.MyView, RequestPresenter.MyProxy> implements RequestUiHandlers {
	public interface MyView extends View, HasUiHandlers<RequestUiHandlers> {
		void resetForm();
		void setInstitutions(Map<String, Institution> institutions);
		void cleanRequestCategories();
		void addRequestCategories(RequestCategory category);
		Institution getInstitution();
		String getRequestInfo();
		String getRequestContext();
		String getRequestTitle();
		Set<RequestCategory> getRequestCategories();
	}

	@ProxyCodeSplit
	@UseGatekeeper(UserGatekeeper.class)
	@NameToken(AppPlace.REQUEST)
	public interface MyProxy extends ProxyPlace<RequestPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private InstitutionServiceAsync institutionService;

	@Inject
	private RequestServiceAsync requestService;

	private Request request;

	@Inject
	public RequestPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		getView().resetForm();
		getRequestCategories();
		getInstitutions();
		Window.setTitle("Solicitud de acceso a la información - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void getRequestCategories() {
		getView().cleanRequestCategories();

		requestService.getCategories(new AsyncCallback<List<RequestCategory>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<RequestCategory> result) {
				for (RequestCategory category : result) {
					getView().addRequestCategories(category);
				}
			}
		});
	}

	@Override
	public void getInstitutions() {
		institutionService.getInstitutions(new AsyncCallback<List<Institution>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar las instituciones", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Institution> result) {
				Map<String, Institution> institutions = new HashMap<String, Institution>();

				for (Institution institution: result) {
					if (institution.getEnabled() && institution.getMasterEnabled()) {
						institutions.put(institution.getName(), institution);
					}
				}

				getView().setInstitutions(institutions);
			}
		});
	}

	@Override
	public void submitRequest() {
		Institution institution = getView().getInstitution();

		if (institution == null) {
			showNotification("Por favor complete el campo de Institución", NotificationEventType.NOTICE);
			return;
		}

		String requestInfo = getView().getRequestInfo();
		String requestContext = getView().getRequestContext();

		if (requestInfo == null || requestInfo.trim().length() == 0) {
			showNotification("Por favor complete el campo de Información", NotificationEventType.NOTICE);
			return;
		}

		if (requestContext == null || requestContext.trim().length() == 0) {
			showNotification("Por favor complete el campo de Contexto", NotificationEventType.NOTICE);
			return;
		}

		String requestTitle = getView().getRequestTitle();
		Set<RequestCategory> categories = getView().getRequestCategories();

		if (requestTitle == null || requestTitle.trim().length() == 0) {
			showNotification("Por favor complete el campo de Titulo de la solicitud", NotificationEventType.NOTICE);
			return;
		}

		request = new Request();
		request.setInstitution(institution);
		request.setInformation(requestInfo);
		request.setContext(requestContext);
		request.setTitle(requestTitle);
		request.setCategories(categories);
		request.setUser(ClientSessionUtil.getUser());
		request.setStatus(RequestStatus.DRAFT);
		request.setUserSatisfaction(UserSatisfaction.NOANSWER);

		requestService.saveRequest(request, new AsyncCallback<Request>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace(System.err);
				showNotification("No se ha podido almacenar su solicitud, intente nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Request result) {
				request = result;
				showRequest();
			}
		});
	}

	@Override
	public void showRequest() {
		if (request != null) {
			placeManager.revealPlace(new PlaceRequest(AppPlace.REQUESTSTATUS).with("requestId", request.getId().toString()));
		} else {
			showNotification("No se ha podido cargar la solicitud", NotificationEventType.NOTICE);
		}
	}

	@Override
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		params.setDuration(NotificationEventParams.DURATION_NORMAL);
		fireEvent(new NotificationEvent(params));
	}

}
