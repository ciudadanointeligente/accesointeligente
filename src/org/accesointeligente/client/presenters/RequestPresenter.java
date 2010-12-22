package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.client.views.RequestView.State;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestPresenter extends WidgetPresenter<RequestPresenter.Display> implements RequestPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestPresenterIface presenter);
		void displayMessage(String message);
		State getState();
		void setState(State state);
		void setInstitutions(Map<String, Institution> institutions);
		void cleanRequestCategories();
		void addRequestCategories(RequestCategory category);

		// Step 1
		Institution getInstitution();
		// Step 2
		String getRequestInfo();
		String getRequestContext();
		// Step 3
		String getRequestTitle();
		Set<RequestCategory> getRequestCategories();
		Boolean getAnotherInstitutionYes();
		Boolean getAnotherInstitutionNo();
		// Step 4
	}

	private Request request;

	public RequestPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		display.setState(State.INSTITUTION_SEARCH);
		getRequestCategories();
		getInstitutions();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	@Override
	public void getRequestCategories() {
		display.cleanRequestCategories();

		RPC.getRequestService().getCategories(new AsyncCallback<List<RequestCategory>>() {
			@Override
			public void onFailure(Throwable caught) {
				display.displayMessage("Error obteniendo actividades");
			}

			@Override
			public void onSuccess(List<RequestCategory> result) {
				for (RequestCategory category : result) {
					display.addRequestCategories(category);
				}
			}
		});
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
	public void nextStep() {
		final State state = display.getState();

		switch (state) {
			// Step 1
			case INSTITUTION_SEARCH:
				Institution institution = display.getInstitution();

				if (institution == null) {
					display.displayMessage("Por favor complete el campo de Institución");
					return;
				}

				display.setState(state.getNext());
				break;
			// Step 2
			case REQUEST_INFO:
				String requestInfo = display.getRequestInfo();
				String requestContext = display.getRequestContext();

				if (requestInfo == null || requestInfo.trim().length() == 0) {
					display.displayMessage("Por favor complete el campo de Información");
					return;
				}

				if (requestContext == null || requestContext.trim().length() == 0) {
					display.displayMessage("Por favor complete el campo de Contexto");
					return;
				}

				display.setState(state.getNext());
				break;
			// Step 3
			case REQUEST_DETAIL:
				String requestTitle = display.getRequestTitle();
				Set<RequestCategory> categories = display.getRequestCategories();
				Boolean anotherInstitutionYes = display.getAnotherInstitutionYes();
				Boolean anotherInstitutionNo = display.getAnotherInstitutionNo();

				if (requestTitle == null || requestTitle.trim().length() == 0) {
					display.displayMessage("Por favor complete el campo de Titulo de la solicitud");
					return;
				}

				if (categories.size() == 0) {
					display.displayMessage("Por favor seleccione al menos una categoria");
					return;
				}

				if(anotherInstitutionYes == false && anotherInstitutionNo == false) {
					display.displayMessage("Por favor seleccione si desea solicitar esta información a otro organismo");
					return;
				}

				request = new Request();

				//Step 1
				request.setInstitution(display.getInstitution());
				// Step 2
				request.setInformation(display.getRequestInfo());
				request.setContext(display.getRequestContext());
				// Step 3
				request.setTitle(display.getRequestTitle());
				request.setCategories(categories);
				request.setAnotherInstitution(display.getAnotherInstitutionYes());
				// User
				request.setUser(ClientSessionUtil.getUser());

				RPC.getRequestService().makeRequest(request, new AsyncCallback<Request>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace(System.err);
						display.displayMessage("No se ha podido almacenar su solicitud, intente nuevamente");
					}

					@Override
					public void onSuccess(Request result) {
						request = result;
						display.setState(state.getNext());
					}
				});

				break;
			// Step 4
			case SUCCESS:
				break;
			default:
		}
	}

	@Override
	public void previousStep() {
		try {
			display.setState(display.getState().getPrevious());
		} catch (IndexOutOfBoundsException ioobe) {
			display.displayMessage("No existe paso previo");
		}
	}

	@Override
	public void showRequest() {
		if (request != null) {
			History.newItem("status?requestId=" + request.getId());
		} else {
			display.displayMessage("No se ha podido cargar la solicitud");
		}
	}
}
