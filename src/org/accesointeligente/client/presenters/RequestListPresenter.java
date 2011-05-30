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
import org.accesointeligente.client.events.*;
import org.accesointeligente.client.services.RequestServiceAsync;
import org.accesointeligente.client.uihandlers.RequestListUiHandlers;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.User;
import org.accesointeligente.model.UserFavoriteRequest;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

import javax.inject.Inject;

public class RequestListPresenter extends Presenter<RequestListPresenter.MyView, RequestListPresenter.MyProxy> implements RequestListUiHandlers, RequestSearchEventHandler, LoginSuccessfulEventHandler, LoginRequiredEventHandler {
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SEARCH_WIDGET = new Type<RevealContentHandler<?>>();

	public interface MyView extends View, HasUiHandlers<RequestListUiHandlers> {
		void setListTitle(String title);
		void setListTitleStyle(String style);
		void initTable(AbstractDataProvider<Request> data);
		void initTableColumns();
		void initTableFavColumn();
		void initTableReceiptColumn();
		void removeColumns();
		void setRequests(AbstractDataProvider<Request> data);
		void setSearchButtonVisible(Boolean visible);
		void setSearchHandleVisible(Boolean visible);
		void setSearchToolTipVisible(Boolean visible);
	}

	@ProxyCodeSplit
	@NameToken(AppPlace.LIST)
	public interface MyProxy extends ProxyPlace<RequestListPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private RequestSearchPresenter requestSearchPresenter;

	@Inject
	private RequestServiceAsync requestService;

	private String listType;
	private String oldListType;
	private AbstractDataProvider<Request> requestData;

	@Inject
	public RequestListPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		addHandler(RequestSearchEvent.TYPE, this);
		addHandler(LoginSuccessfulEvent.TYPE, this);
		addHandler(LoginRequiredEvent.TYPE, this);
	}

	@Override
	public void onReset() {
		if (listType != null && !listType.equals(oldListType)) {
			clearSlot(SLOT_SEARCH_WIDGET);
			getView().setSearchButtonVisible(false);
			getView().setSearchHandleVisible(false);
			getView().setSearchToolTipVisible(false);
			getView().removeColumns();
			requestData = new ListDataProvider<Request>();
			getView().initTable(requestData);
			loadColumns(listType);
			// FIXME: this only loads a maximum of 300 request
			loadRequests(0, 300, listType);
			oldListType = listType;
		}
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		listType = request.getParameter("type", null);
	}

	public AbstractDataProvider<Request> getRequestData() {
		return requestData;
	}

	public void setRequestData(AbstractDataProvider<Request> requestData) {
		this.requestData = requestData;
	}

	@Override
	public void loadColumns(String type) {
		if (type.equals(RequestListType.MYREQUESTS.getType())) {
			if (ClientSessionUtil.checkSession() == true) {
				getView().initTableReceiptColumn();
			}
		} else if (type.equals(RequestListType.FAVORITES.getType())) {
			if (ClientSessionUtil.checkSession() == true) {
				getView().initTableFavColumn();
			}
		} else if (type.equals(RequestListType.GENERAL.getType())) {
			if (ClientSessionUtil.checkSession() == true) {
				getView().initTableFavColumn();
			}
		}
	}

	@Override
	public void loadRequests(Integer offset, Integer limit, String type) {
		listType = type;
		requestData = new ListDataProvider<Request>();
		getView().setRequests(requestData);

		if (type.equals(RequestListType.MYREQUESTS.getType())) {
			getView().setListTitle("Mis solicitudes");
			getView().setListTitleStyle(RequestListType.MYREQUESTS.getType());

			if (ClientSessionUtil.checkSession()) {
				requestService.getUserRequestList(offset, limit, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
						placeManager.revealDefaultPlace();
					}

					@Override
					public void onSuccess(List<Request> results) {
						requestData = new ListDataProvider<Request>(results);
						getView().setRequests(requestData);
					}
				});
			} else {
				showNotification("Necesita acceder para poder ver esta lista", NotificationEventType.NOTICE);
				placeManager.revealDefaultPlace();
			}

		} else if (type.equals(RequestListType.FAVORITES.getType())) {
			getView().setListTitle("Mis favoritas");
			getView().setListTitleStyle(RequestListType.FAVORITES.getType());

			if (ClientSessionUtil.checkSession()) {
				requestService.getUserFavoriteRequestList(offset, limit, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
						placeManager.revealDefaultPlace();

					}

					@Override
					public void onSuccess(List<Request> results) {
						requestData = new ListDataProvider<Request>(results);
						getView().setRequests(requestData);
					}
				});
			} else {
				showNotification("Necesita acceder para poder ver esta lista", NotificationEventType.NOTICE);
				placeManager.revealDefaultPlace();

			}

		} else if (type.equals(RequestListType.DRAFTS.getType())) {
			getView().setListTitle("Mis borradores");
			getView().setListTitleStyle(RequestListType.DRAFTS.getType());

			if (ClientSessionUtil.checkSession()) {
				requestService.getUserDraftList(offset, limit, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
						placeManager.revealDefaultPlace();

					}

					@Override
					public void onSuccess(List<Request> results) {
						requestData = new ListDataProvider<Request>(results);
						getView().setRequests(requestData);
					}
				});
			} else {
				showNotification("Necesita acceder para poder ver esta lista", NotificationEventType.NOTICE);
				placeManager.revealDefaultPlace();

			}

		} else if (type.equals(RequestListType.GENERAL.getType())) {
			getView().setListTitle("Listado de solicitudes");
			getView().setListTitleStyle(RequestListType.GENERAL.getType());
			setInSlot(SLOT_SEARCH_WIDGET, requestSearchPresenter);
			getView().setSearchButtonVisible(true);
			getView().setSearchHandleVisible(true);

			requestService.getRequestList(offset, limit, new AsyncCallback<List<Request>>() {

				@Override
				public void onFailure(Throwable caught) {
					showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
					placeManager.revealDefaultPlace();

				}

				@Override
				public void onSuccess(List<Request> results) {
					// TODO: implement style for tooltip
					if (results.size() < 1) {
						getView().setSearchToolTipVisible(true);
					}
					requestData = new ListDataProvider<Request>(results);
					getView().setRequests(requestData);
				}
			});
		} else {
			showNotification("No existe el tipo de lista solicitado: " + type, NotificationEventType.ERROR);
			placeManager.revealDefaultPlace();
		}
	}

	@Override
	public void loadRequests(Integer offset, Integer limit, String type, RequestSearchParams params) {

		if (type.equals(RequestListType.MYREQUESTS.getType())) {
			getView().setListTitle("Mis solicitudes");
			getView().setListTitleStyle(RequestListType.MYREQUESTS.getType());

			if (ClientSessionUtil.checkSession()) {
				requestService.getUserRequestList(offset, limit, params, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
						placeManager.revealDefaultPlace();

					}

					@Override
					public void onSuccess(List<Request> results) {
						requestData = new ListDataProvider<Request>(results);
						getView().setRequests(requestData);
					}
				});
			} else {
				showNotification("Necesita acceder para poder ver esta lista", NotificationEventType.NOTICE);
				placeManager.revealDefaultPlace();

			}

		} else if (type.equals(RequestListType.FAVORITES.getType())) {
			getView().setListTitle("Mis favoritas");
			getView().setListTitleStyle(RequestListType.FAVORITES.getType());

			if (ClientSessionUtil.checkSession()) {
				requestService.getUserFavoriteRequestList(offset, limit, params, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
						placeManager.revealDefaultPlace();

					}

					@Override
					public void onSuccess(List<Request> results) {
						requestData = new ListDataProvider<Request>(results);
						getView().setRequests(requestData);
					}
				});
			} else {
				showNotification("Necesita acceder para poder ver esta lista", NotificationEventType.NOTICE);
				placeManager.revealDefaultPlace();

			}

		} else if (type.equals(RequestListType.DRAFTS.getType())) {
			getView().setListTitle("Mis borradores");
			getView().setListTitleStyle(RequestListType.DRAFTS.getType());

			if (ClientSessionUtil.checkSession()) {
				requestService.getUserDraftList(offset, limit, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
						placeManager.revealDefaultPlace();

					}

					@Override
					public void onSuccess(List<Request> results) {
						requestData = new ListDataProvider<Request>(results);
						getView().setRequests(requestData);
					}
				});
			} else {
				showNotification("Necesita acceder para poder ver esta lista", NotificationEventType.NOTICE);
				placeManager.revealDefaultPlace();

			}

		} else if (type.equals(RequestListType.GENERAL.getType())) {
			getView().setListTitle("Listado de solicitudes");
			getView().setListTitleStyle(RequestListType.GENERAL.getType());

			requestService.getRequestList(offset, limit, params, new AsyncCallback<List<Request>>() {

				@Override
				public void onFailure(Throwable caught) {
					showNotification("No es posible recuperar el listado solicitado", NotificationEventType.ERROR);
					placeManager.revealDefaultPlace();

				}

				@Override
				public void onSuccess(List<Request> results) {
					// TODO: implement style for tooltip
					if (results.size() < 1) {
						getView().setSearchToolTipVisible(true);
					}
					requestData = new ListDataProvider<Request>(results);
					getView().setRequests(requestData);
				}
			});
		} else {
			showNotification("No existe el tipo de lista solicitado: " + type, NotificationEventType.ERROR);
			placeManager.revealDefaultPlace();
		}
	}

	@Override
	public void requestToggleFavorite(final Request request) {
		final User user = ClientSessionUtil.getUser();
		requestService.getFavoriteRequest(request, user, new AsyncCallback<UserFavoriteRequest>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No es posible recuperar los favoritos", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(UserFavoriteRequest result) {
				if (result == null) {
					requestService.createFavoriteRequest(request, user, new AsyncCallback<UserFavoriteRequest>() {

						@Override
						public void onFailure(Throwable caught) {
							showNotification("No es posible almacenar el favorito", NotificationEventType.ERROR);
						}

						@Override
						public void onSuccess(UserFavoriteRequest result) {
							loadRequests(0, 300, listType);
							showNotification("Se ha agregado el favorito", NotificationEventType.SUCCESS);
						}
					});
				} else {
					UserFavoriteRequest favorite = new UserFavoriteRequest();
					favorite.setRequest(request);
					favorite.setUser(user);

					requestService.deleteFavoriteRequest(favorite, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							showNotification("No es posible eliminar el favorito", NotificationEventType.ERROR);
						}

						@Override
						public void onSuccess(Void result) {
							loadRequests(0, 300, listType);
							showNotification("Se ha eliminado el favorito", NotificationEventType.SUCCESS);
						}
					});
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

	@Override
	public void showRequest(Integer requestId) {
		if (listType.equals(RequestListType.DRAFTS.getType())) {
			placeManager.revealPlace(new PlaceRequest(AppPlace.REQUESTSTATUS).with("requestId", requestId.toString()));
		} else {
			placeManager.revealPlace(new PlaceRequest(AppPlace.RESPONSE).with("requestId", requestId.toString()));
		}
	}

	@Override
	public void onSearch(RequestSearchEvent event) {
		loadRequests(0, 100, listType, event.getParams());
	}

	// FIXME: use PlaceRequest instead lf URL redirections
	@Override
	public String getRequestBaseUrlPlace() {
		String baseUrl;

		if (listType.equals(RequestListType.DRAFTS.getType())) {
			baseUrl = "#" + AppPlace.REQUESTSTATUS + ";requestId=";
		} else {
			baseUrl = "#" + AppPlace.RESPONSE + ";requestId=";
		}

		return baseUrl;
	}

	@Override
	public void gotoRequest() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.REQUEST));
	}

	@Override
	public void loginSuccessful(LoginSuccessfulEvent event) {
		oldListType = null;
	}

	@Override
	public void loginRequired(LoginRequiredEvent event) {
		oldListType = null;
	}
}
