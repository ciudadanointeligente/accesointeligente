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
		void displayMessage(String string);
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
				display.displayMessage("No es posible recuperar la solicitud");
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
					display.setDate(result.getDate());
					display.editOptions(requestIsEditable());
				} else {
					display.displayMessage("No se puede cargar la solicitud");
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
		if (request.getStatus() == RequestStatus.NEW) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
