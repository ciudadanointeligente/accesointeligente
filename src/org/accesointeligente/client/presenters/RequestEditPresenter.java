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
import org.accesointeligente.client.inject.ServiceInjector;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import java.util.*;

public class RequestEditPresenter extends CustomWidgetPresenter<RequestEditPresenter.Display> implements RequestEditPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestEditPresenterIface presenter);
		Institution getInstitution();
		void setInstitution(Institution institution);
		String getRequestInfo();
		void setRequestInfo(String info);
		String getRequestTitle();
		void setRequestContext(String context);
		String getRequestContext();
		void setRequestTitle(String title);
		Boolean getAnotherInstitutionYes();
		Boolean getAnotherInstitutionNo();
		void setAnotherInstitution(Boolean another);
		void cleanRequestCategories();
		void addRequestCategories(RequestCategory category, Boolean checked);
		Set<RequestCategory> getRequestCategories();
		void setInstitutions(Map<String, Institution> institutions);
	}

	private static final ServiceInjector serviceInjector = GWT.create(ServiceInjector.class);
	private Request request;

	@Inject
	public RequestEditPresenter(Display display, EventBus eventBus) {
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
	public void getRequestCategories(final Request request) {
		display.cleanRequestCategories();

		serviceInjector.getRequestService().getCategories(new AsyncCallback<List<RequestCategory>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<RequestCategory> result) {
				for (RequestCategory category : result) {
					if (request != null) {
						if (request.getCategories().contains(category)) {
							display.addRequestCategories(category, true);
						} else {
							display.addRequestCategories(category, false);
						}
					} else {
						display.addRequestCategories(category, false);
					}
				}
			}
		});
	}

	@Override
	public void getInstitutions() {
		serviceInjector.getInstitutionService().getInstitutions(new AsyncCallback<List<Institution>>() {
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

				display.setInstitutions(institutions);
			}
		});
	}

	@Override
	public void submitRequest() {
		Institution institution = display.getInstitution();

		if (institution == null) {
			showNotification("Por favor complete el campo de Institución", NotificationEventType.ERROR);
			return;
		}

		String requestInfo = display.getRequestInfo();
		String requestContext = display.getRequestContext();

		if (requestInfo == null || requestInfo.trim().length() == 0) {
			showNotification("Por favor complete el campo de Información", NotificationEventType.ERROR);
			return;
		}

		if (requestContext == null || requestContext.trim().length() == 0) {
			showNotification("Por favor complete el campo de Contexto", NotificationEventType.ERROR);
			return;
		}

		String requestTitle = display.getRequestTitle();
		Set<RequestCategory> categories = display.getRequestCategories();
		Boolean anotherInstitutionYes = display.getAnotherInstitutionYes();
		Boolean anotherInstitutionNo = display.getAnotherInstitutionNo();

		if (requestTitle == null || requestTitle.trim().length() == 0) {
			showNotification("Por favor complete el campo de Titulo de la solicitud", NotificationEventType.ERROR);
			return;
		}

		if(anotherInstitutionYes == false && anotherInstitutionNo == false) {
			showNotification("Por favor seleccione si desea solicitar esta información a otro organismo", NotificationEventType.ERROR);
			return;
		}

		request.setInstitution(institution);
		request.setInformation(requestInfo);
		request.setContext(requestContext);
		request.setTitle(requestTitle);
		request.setCategories(categories);
		request.setAnotherInstitution(anotherInstitutionYes);
		request.setUser(ClientSessionUtil.getUser());
		request.setStatus(RequestStatus.DRAFT);

		serviceInjector.getRequestService().saveRequest(request, new AsyncCallback<Request>() {
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
			History.newItem(AppPlace.REQUESTSTATUS.getToken() + "?requestId=" + request.getId());
		} else {
			showNotification("No se ha podido cargar la solicitud", NotificationEventType.ERROR);
		}
	}

	@Override
	public void showRequest(Integer requestId) {
		serviceInjector.getRequestService().getRequest(requestId, new AsyncCallback<Request>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar la solicitud", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Request result) {
				if (result != null) {
					request = result;
					display.setInstitution(request.getInstitution());
					display.setRequestContext(request.getContext());
					display.setRequestInfo(request.getInformation());
					display.setRequestTitle(request.getTitle());
					display.setAnotherInstitution(request.getAnotherInstitution());
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
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
