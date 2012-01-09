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
import org.accesointeligente.client.PlaceHistory;
import org.accesointeligente.client.services.RequestServiceAsync;
import org.accesointeligente.client.uihandlers.RequestResponseUiHandlers;
import org.accesointeligente.client.widgets.ResponseWidget;
import org.accesointeligente.model.*;
import org.accesointeligente.shared.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.ListDataProvider;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class RequestResponsePresenter extends Presenter<RequestResponsePresenter.MyView, RequestResponsePresenter.MyProxy> implements RequestResponseUiHandlers {
	public static final String REQUEST_TITLE_CONTINUE = "...";
	public static final Integer REQUEST_TITLE_INIT = 0;
	public static final Integer REQUEST_TITLE_SIZE = 40;

	public interface MyView extends View, HasUiHandlers<RequestResponseUiHandlers> {
		// Request
		void setStatus(RequestStatus status);
		void setRequestTitle(String title);
		void setRequestDate(Date date);
		void setInstitutionName(String name);
		void setRequestInfo(String info);
		void setRequestContext(String context);
		// Response
		void setResponses(List<Response> responses);
		void setComments(List<RequestComment> comments);
		void userSatisfaction(Response response, ResponseWidget widget);
		void showNewCommentPanel(Boolean show);
		void cleanNewCommentText();
		void setRatingValue(Integer rate);
		void clearResponseWidget(ResponseWidget widget);
		void setUserResponse(UserResponse userResponse, ResponseWidget widget);
		void newUserResponse(Response response, ResponseWidget widget);
		void initTable();
		void removeColumns();
		void setRequests(ListDataProvider<Request> data);
		void setShare(String href);
	}

	@ProxyCodeSplit
	@NameToken(AppPlace.RESPONSE)
	public interface MyProxy extends ProxyPlace<RequestResponsePresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private PlaceHistory placeHistory;

	@Inject
	private RequestServiceAsync requestService;

	private Integer requestId;
	private Request request;
	private List<Response> responses;

	@Inject
	public RequestResponsePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		getView().removeColumns();
		getView().initTable();
		loadBestVotedRequests();

		if (requestId != null) {
			showRequest(requestId);
		}
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
					getView().setStatus(request.getStatus());
					getView().setRequestTitle(request.getTitle());
					getView().setRequestDate(request.getConfirmationDate());
					getView().setInstitutionName(request.getInstitution().getName());
					getView().setRequestInfo(request.getInformation());
					getView().setRequestContext(request.getContext());
					if (request.getResponses() != null && request.getResponses().size() > 0) {
						responses = new ArrayList<Response>(request.getResponses());
					} else {
						responses = new ArrayList<Response>();
						Response response = new Response();
						response.setInformation("Esperando Respuesta");
						responses.add(response);
					}
					getView().setResponses(responses);

					loadComments(request);
					if (request.getQualification() != null) {
						getView().setRatingValue(request.getQualification().intValue());
					}

					Boolean loggedIn = ClientSessionUtil.checkSession();
					getView().showNewCommentPanel(loggedIn);
					getView().setShare(Window.Location.getHref());
					if (request.getTitle().length() > REQUEST_TITLE_SIZE) {
						Window.setTitle(request.getTitle().substring(REQUEST_TITLE_INIT, REQUEST_TITLE_SIZE) + REQUEST_TITLE_CONTINUE + " - Acceso Inteligente");
					} else {
						Window.setTitle(request.getTitle() + " - Acceso Inteligente");
					}
				} else {
					showNotification("No se puede cargar la solicitud", NotificationEventType.ERROR);
				}
			}
		});
	}

	@Override
	public void loadComments(Request request) {
		requestService.getRequestComments(request, new AsyncCallback<List<RequestComment>>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar los archivos adjuntos", NotificationEventType.ERROR);
				placeManager.navigateBack();
			}

			@Override
			public void onSuccess(List<RequestComment> comments) {
				getView().setComments(comments);
			}
		});
	}

	@Override
	public void saveComment(String commentContent) {
		RequestComment comment = new RequestComment();
		comment.setDate(new Date());
		comment.setText(commentContent);
		comment.setUser(ClientSessionUtil.getUser());
		comment.setRequest(request);

		requestService.createRequestComment(comment, new AsyncCallback<RequestComment>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible publicar su comentario", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(RequestComment comment) {
				showNotification("Se ha publicado su comentario", NotificationEventType.SUCCESS);
				getView().cleanNewCommentText();
				loadComments(comment.getRequest());
			}
		});

	}

	@Override
	public void loadAttachments(Response response, final ResponseWidget widget) {
		requestService.getResponseAttachmentList(response, new AsyncCallback<List<Attachment>>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar los archivos adjuntos", NotificationEventType.ERROR);
				placeManager.navigateBack();
			}

			@Override
			public void onSuccess(List<Attachment> attachments) {
				if (attachments != null && attachments.size() > 0) {
					widget.initTableColumns();
					ListDataProvider<Attachment> data = new ListDataProvider<Attachment>(attachments);
					widget.setResponseAttachments(data);
				}
			}
		});
	}

	@Override
	public void saveQualification(Integer rate) {
		UserRequestQualification qualification = new UserRequestQualification();
		qualification.setQualification(rate);
		qualification.setRequest(this.request);
		qualification.setUser(ClientSessionUtil.getUser());

		requestService.saveUserRequestQualification(qualification, new AsyncCallback<UserRequestQualification>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se puede almacenar su calificacion", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(UserRequestQualification result) {
				getView().setRatingValue(result.getRequest().getQualification().intValue());
			}
		});
	}

	@Override
	public void getUserResponse(final Response response, final ResponseWidget widget) {
		requestService.getUserResponse(response, new AsyncCallback<UserResponse>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se puede cargar la respuesta del usuario", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(UserResponse result) {
				if (result != null) {
					getView().setUserResponse(result, widget);
				} else {
					if (ClientSessionUtil.checkSession() && response.getRequest().getUser() != null && response.getRequest().getUser().equals(ClientSessionUtil.getUser())) {
						getView().newUserResponse(response, widget);
					}
				}
			}
		});
	}

	@Override
	public void saveUserResponse(String information, Response response, final ResponseWidget widget) {
		UserResponse userResponse = new UserResponse();
		userResponse.setResponse(response);
		userResponse.setInformation(information);
		userResponse.setDate(new Date());

		requestService.saveUserResponse(userResponse, new AsyncCallback<UserResponse>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se pudo guardar su respuesta, por favor intente nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(UserResponse result) {
				showNotification("Se ha guardado su respuesta", NotificationEventType.SUCCESS);
				getView().clearResponseWidget(widget);
				getView().setUserResponse(result, widget);
			}
		});
	}

	@Override
	public void loadBestVotedRequests() {
		requestService.getBestVotedRequests(new AsyncCallback<List<Request>>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
				placeManager.revealDefaultPlace();
			}

			@Override
			public void onSuccess(List<Request> results) {
				ListDataProvider<Request> data = new ListDataProvider<Request>(results);
				getView().setRequests(data);
			}
		});
	}

	@Override
	public void updateResponse(final Response response, final FlowPanel userSatisfactionPanel, final FlowPanel requestStatusPanel) {
		if (!ClientSessionUtil.getUser().equals(response.getRequest().getUser())) {
			showNotification("Usted no es el propietario de esta solucitud.", NotificationEventType.ERROR);
			return;
		}

		requestService.saveResponse(response, new AsyncCallback<Response>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No fue posible realizar esta acción, por favor intente nuevamente", NotificationEventType.ERROR);
				userSatisfactionPanel.setVisible(true);
			}

			@Override
			public void onSuccess(Response result) {
				Request request = result.getRequest();
				requestService.setRequestUserSatisfaction(request, new AsyncCallback<Request>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No fue posible realizar esta acción, por favor intente nuevamente", NotificationEventType.ERROR);
						userSatisfactionPanel.setVisible(true);
					}

					@Override
					public void onSuccess(Request result) {
						showNotification("Hemos guardado su respuesta", NotificationEventType.SUCCESS);
						userSatisfactionPanel.setVisible(false);
						requestStatusPanel.setVisible(false);
					}
				});
			}
		});
	}

	@Override
	public void goBack() {
		for (PlaceRequest placeRequest : placeHistory.getHistory()) {
			if (AppPlace.LIST.equals(placeRequest.getNameToken())) {
				placeManager.revealPlace(placeRequest);
				return;
			}
		}

		placeManager.revealPlace(new PlaceRequest(AppPlace.LIST).with("type", RequestListType.GENERAL.getType()));
	}

	@Override
	public void gotoLogin() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LOGIN));
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
