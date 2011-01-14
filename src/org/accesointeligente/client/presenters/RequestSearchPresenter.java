package org.accesointeligente.client.presenters;

import org.accesointeligente.client.services.RPC;
import org.accesointeligente.model.Institution;
import org.accesointeligente.shared.RequestSearchEvent;
import org.accesointeligente.shared.RequestSearchParams;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

public class RequestSearchPresenter extends WidgetPresenter<RequestSearchPresenter.Display> implements RequestSearchPresenterIface {
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

	public RequestSearchPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		getInstitutions();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	@Override
	public void getInstitutions() {
		RPC.getInstitutionService().getInstitutions(new AsyncCallback<List<Institution>>() {
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
