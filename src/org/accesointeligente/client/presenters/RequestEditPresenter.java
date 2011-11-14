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
import org.accesointeligente.client.uihandlers.RequestEditUiHandlers;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

import javax.inject.Inject;

public class RequestEditPresenter extends Presenter<RequestEditPresenter.MyView, RequestEditPresenter.MyProxy> implements RequestEditUiHandlers {
	public interface MyView extends View, HasUiHandlers<RequestEditUiHandlers> {
		Institution getInstitution();
		void setInstitution(Institution institution);
		String getRequestInfo();
		void setRequestInfo(String info);
		String getRequestTitle();
		void setRequestContext(String context);
		String getRequestContext();
		void setRequestTitle(String title);
		void cleanRequestCategories();
		void addRequestCategories(RequestCategory category, Boolean checked);
		Set<RequestCategory> getRequestCategories();
		void setInstitutions(Map<String, Institution> institutions);
	}

	@ProxyCodeSplit
	@UseGatekeeper(UserGatekeeper.class)
	@NameToken(AppPlace.EDITREQUEST)
	public interface MyProxy extends ProxyPlace<RequestEditPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private InstitutionServiceAsync institutionService;

	@Inject
	private RequestServiceAsync requestService;

	private Integer requestId;
	private Request request;

	@Inject
	public RequestEditPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		getInstitutions();
		Window.setTitle("Editar solicitud - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		try {
			requestId = Integer.parseInt(request.getParameter("requestId", null));
		} catch (Exception ex) {
			requestId = null;
		}
	}

	@Override
	public void getRequestCategories(final Request request) {
		getView().cleanRequestCategories();

		requestService.getCategories(new AsyncCallback<List<RequestCategory>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<RequestCategory> result) {
				for (RequestCategory category : result) {
					if (request != null) {
						if (request.getCategories().contains(category)) {
							getView().addRequestCategories(category, true);
						} else {
							getView().addRequestCategories(category, false);
						}
					} else {
						getView().addRequestCategories(category, false);
					}
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
					institutions.put(institution.getName(), institution);
				}

				getView().setInstitutions(institutions);

				if (requestId != null) {
					showRequest(requestId);
				}
			}
		});
	}

	@Override
	public void submitRequest() {
		Institution institution = getView().getInstitution();

		if (institution == null) {
			showNotification("Por favor complete el campo de Institución", NotificationEventType.ERROR);
			return;
		}

		String requestInfo = getView().getRequestInfo();
		String requestContext = getView().getRequestContext();

		if (requestInfo == null || requestInfo.trim().length() == 0) {
			showNotification("Por favor complete el campo de Información", NotificationEventType.ERROR);
			return;
		}

		if (requestContext == null || requestContext.trim().length() == 0) {
			showNotification("Por favor complete el campo de Contexto", NotificationEventType.ERROR);
			return;
		}

		String requestTitle = getView().getRequestTitle();
		Set<RequestCategory> categories = getView().getRequestCategories();

		if (requestTitle == null || requestTitle.trim().length() == 0) {
			showNotification("Por favor complete el campo de Titulo de la solicitud", NotificationEventType.ERROR);
			return;
		}

		request.setInstitution(institution);
		request.setInformation(requestInfo);
		request.setContext(requestContext);
		request.setTitle(requestTitle);
		request.setCategories(categories);
		request.setUser(ClientSessionUtil.getUser());
		request.setStatus(RequestStatus.DRAFT);

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
			showNotification("No se ha podido cargar la solicitud", NotificationEventType.ERROR);
		}
	}

	@Override
	public void showRequest(Integer requestId) {
		requestService.getRequest(requestId, new AsyncCallback<Request>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar la solicitud", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Request result) {
				if (result != null) {
					request = result;
					getView().setInstitution(request.getInstitution());
					getView().setRequestContext(request.getContext());
					getView().setRequestInfo(request.getInformation());
					getView().setRequestTitle(request.getTitle());
					getRequestCategories(request);
				} else {
					showNotification("No se puede cargar la solicitud", NotificationEventType.ERROR);
				}
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
}
