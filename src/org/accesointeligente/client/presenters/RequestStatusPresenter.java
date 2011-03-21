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

import org.accesointeligente.client.services.RPC;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;
import java.util.Set;

public class RequestStatusPresenter extends WidgetPresenter<RequestStatusPresenter.Display> implements RequestStatusPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestStatusPresenterIface presenter);
		void setDate(Date date);
		void setStatus(RequestStatus status);
		void setInstitutionName(String name);
		void setRequestInfo(String info);
		void setRequestContext(String context);
		void setRequestTitle(String title);
		void addRequestCategories(RequestCategory category);
		void setRequestCategories(Set<RequestCategory> categories);
		void setAnotherInstitution(Boolean anotherInstitution);
		void editOptions(Boolean allowEdit);
	}

	private Request request;

	public RequestStatusPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
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
	public void showRequest(Integer requestId) {
		RPC.getRequestService().getRequest(requestId, new AsyncCallback<Request>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar la solicitud", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Request result) {
				if (result != null) {
					setRequest(result);
					display.setStatus(result.getStatus());
					display.setInstitutionName(result.getInstitution().getName());
					display.setRequestInfo(result.getInformation());
					display.setRequestContext(result.getContext());
					display.setRequestTitle(result.getTitle());
					display.setRequestCategories(result.getCategories());
					display.setAnotherInstitution(result.getAnotherInstitution());
					display.setDate(result.getConfirmationDate());
					display.editOptions(requestIsEditable());
				} else {
					showNotification("No se puede cargar la solicitud", NotificationEventType.ERROR);
				}
			}
		});
	}

	@Override
	public void deleteRequest() {
		RPC.getRequestService().deleteRequest(getRequest(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se ha podido eliminar la solicitud", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Void result) {
				showNotification("Se ha eliminado la solicitud", NotificationEventType.SUCCESS);
				History.newItem(AppPlace.LIST.getToken() + "?type=" + RequestListType.MYREQUESTS.getType());
			}
		});
	}

	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public Boolean requestIsEditable() {
		if (request.getStatus() == RequestStatus.DRAFT) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void confirmRequest() {
		request.setStatus(RequestStatus.NEW);
		request.setConfirmationDate(new Date());
		RPC.getRequestService().saveRequest(request, new AsyncCallback<Request>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible confirmar su borrador de solicitud, por favor intentelo nuevamente", NotificationEventType.NOTICE);
			}

			@Override
			public void onSuccess(Request result) {
				showNotification("Ha confirmado su borrador de solicitud. Su solicitud será procesada a la brevedad", NotificationEventType.SUCCESS);
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
