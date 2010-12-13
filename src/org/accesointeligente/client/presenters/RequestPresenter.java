package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.client.views.RequestView.State;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.RequestFormat;
import org.accesointeligente.shared.RequestMethod;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.HashSet;
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
		Boolean getFormatPaper();
		Boolean getFormatDigital();
		Boolean getFormatAny();
		Boolean getMethodEmail();
		Boolean getMethodMail();
		Boolean getMethodOffice();
		// Step 4
	}

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
				Boolean formatPaper = display.getFormatPaper();
				Boolean formatDigital = display.getFormatDigital();
				Boolean formatAny = display.getFormatAny();
				Boolean methodEmail = display.getMethodEmail();
				Boolean methodMail = display.getMethodMail();
				Boolean methodOffice = display.getMethodOffice();

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

				if(formatPaper == false && formatDigital == false && formatAny == false) {
					display.displayMessage("Por favor seleccione uno o más formatos para recibir la información");
					return;
				}

				if(methodEmail == false && methodMail == false && methodOffice == false) {
					display.displayMessage("Por favor seleccione una o más formas para recibir la información");
					return;
				}

				Request request = new Request();
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

				Set<RequestFormat> formats = new HashSet<RequestFormat>();
				if (formatPaper) {
					formats.add(RequestFormat.PAPER);
				}

				if (formatDigital) {
					formats.add(RequestFormat.DIGITAL);
				}

				if (formatAny) {
					formats.add(RequestFormat.ANY);
				}

				request.setFormats(formats);

				Set<RequestMethod> methods = new HashSet<RequestMethod>();
				if (methodEmail) {
					methods.add(RequestMethod.EMAIL);
				}

				if (methodMail) {
					methods.add(RequestMethod.MAIL);
				}

				if (methodOffice) {
					methods.add(RequestMethod.OFFICE);
				}

				request.setMethods(methods);

				RPC.getRequestService().makeRequest(request, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace(System.err);
						display.displayMessage("No se ha podido almacenar su solicitud, intente nuevamente");
					}

					@Override
					public void onSuccess(Void result) {
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
}
