package org.accesointeligente.client.presenters;

import org.accesointeligente.client.AppController;
import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.Response;
import org.accesointeligente.shared.RequestStatus;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;
import java.util.List;

public class RequestResponsePresenter extends WidgetPresenter<RequestResponsePresenter.Display> implements RequestResponsePresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestResponsePresenterIface presenter);
		// Request
		void setStatus(RequestStatus status);
		void setRequestTitle(String title);
		void setRequestDate(Date date);
		void setResponseDate(Date date);
		void setInstitutionName(String name);
		void setRequestInfo(String info);
		void setRequestContext(String context);
		// Response
		void setResponseInfo(String info);
		void setResponseAttachments(Response response);
		void displayMessage(String string);
	}

	public RequestResponsePresenter(Display display, EventBus eventBus) {
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
					display.setStatus(result.getStatus());
					display.setRequestTitle(result.getTitle());
					display.setRequestDate(result.getDate());
					display.setInstitutionName(result.getInstitution().getName());
					display.setRequestInfo(result.getInformation());
					display.setRequestContext(result.getContext());
					if (result.getResponse() != null) {
						display.setResponseDate(result.getResponse().getDate());
						display.setResponseInfo(result.getResponse().getInformation());
					} else {
						display.setResponseInfo("Esperando Respuesta");
					}
				} else {
					display.displayMessage("No se puede cargar la solicitud");
				}
			}
		});
	}

	@Override
	public String getListLink() {
		String link = null;

		if (ClientSessionUtil.checkSession()) {
			link = "list?type=mylist";
		} else {
			link = "list?type=general";
		}

		return link;
	}
}
