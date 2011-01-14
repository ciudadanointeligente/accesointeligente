package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.client.views.RequestSearchView;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.User;
import org.accesointeligente.model.UserFavoriteRequest;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

public class RequestListPresenter extends WidgetPresenter<RequestListPresenter.Display> implements RequestListPresenterIface, RequestSearchEventHandler {
	public interface Display extends WidgetDisplay {
		void setPresenter(RequestListPresenterIface presenter);
		void displayMessage(String message);
		void setListTitle(String title);
		void setSearchWidget(Widget widget);
		void initTable();
		void initTableColumns();
		void initTableFavColumn();
		void setRequests(ListDataProvider<Request> data);
	}

	public RequestListPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		eventBus.addHandler(RequestSearchEvent.TYPE, this);

		RequestSearchPresenter presenter = new RequestSearchPresenter(new RequestSearchView(), eventBus);
		presenter.bind();
		display.setSearchWidget(presenter.getDisplay().asWidget());

		display.initTable();

		if (ClientSessionUtil.checkSession()) {
			display.initTableFavColumn();
		}
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	@Override
	public void loadRequests(Integer offset, Integer limit, String type) {

		if (type.equals(RequestListType.MYREQUESTS.getName())) {
			display.setListTitle("Mis solicitudes");

			if (ClientSessionUtil.checkSession()) {
				RPC.getRequestService().getUserRequestList(offset, limit, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						display.displayMessage("No es posible recuperar el listado solicitado");
						History.newItem(AppPlaces.HOME.getName());
					}

					@Override
					public void onSuccess(List<Request> results) {
						ListDataProvider<Request> data = new ListDataProvider<Request>(results);
						display.setRequests(data);
					}
				});
			} else {
				display.displayMessage("Necesita acceder para poder ver esta lista");
				History.newItem(AppPlaces.HOME.getName());
			}

		} else if (type.equals(RequestListType.FAVORITES.getName())) {
			display.setListTitle("Mis favoritas");

			if (ClientSessionUtil.checkSession()) {
				RPC.getRequestService().getUserFavoriteRequestList(offset, limit, new AsyncCallback<List<Request>>() {

					@Override
					public void onFailure(Throwable caught) {
						display.displayMessage("No es posible recuperar el listado solicitado");
						History.newItem(AppPlaces.HOME.getName());
					}

					@Override
					public void onSuccess(List<Request> results) {
						ListDataProvider<Request> data = new ListDataProvider<Request>(results);
						display.setRequests(data);
					}
				});
			} else {
				display.displayMessage("Necesita acceder para poder ver esta lista");
				History.newItem(AppPlaces.HOME.getName());
			}

		} else if (type.equals(RequestListType.GENERAL.getName())) {
			display.setListTitle("Listado de solicitudes");

			RPC.getRequestService().getRequestList(offset, limit, new AsyncCallback<List<Request>>() {

				@Override
				public void onFailure(Throwable caught) {
					display.displayMessage("No es posible recuperar el listado solicitado");
					History.newItem(AppPlaces.HOME.getName());
				}

				@Override
				public void onSuccess(List<Request> results) {
					ListDataProvider<Request> data = new ListDataProvider<Request>(results);
					display.setRequests(data);
				}
			});
		} else {
			display.displayMessage("No existe el tipo de lista solicitado: " + type);
			History.newItem(AppPlaces.HOME.getName());
		}
	}

	@Override
	public void requestToggleFavorite(final Request request) {
		final User user = ClientSessionUtil.getUser();
		RPC.getRequestService().getFavoriteRequest(request, user, new AsyncCallback<UserFavoriteRequest>() {

			@Override
			public void onFailure(Throwable caught) {
				display.displayMessage("No es posible recuperar los favoritos");
			}

			@Override
			public void onSuccess(UserFavoriteRequest result) {
				if (result == null) {
					RPC.getRequestService().createFavoriteRequest(request, user, new AsyncCallback<UserFavoriteRequest>() {

						@Override
						public void onFailure(Throwable caught) {
							display.displayMessage("No es posible almacenar el favorito");
						}

						@Override
						public void onSuccess(UserFavoriteRequest result) {
							loadRequests(0, 100, RequestListType.MYREQUESTS.getName());
						}
					});
				} else {
					UserFavoriteRequest favorite = new UserFavoriteRequest();
					favorite.setRequest(request);
					favorite.setUser(user);

					RPC.getRequestService().deleteFavoriteRequest(favorite, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							display.displayMessage("No es posible eliminar el favorito");
						}

						@Override
						public void onSuccess(Void result) {
							loadRequests(0, 100, RequestListType.MYREQUESTS.getName());
						}
					});
				}
			}
		});
	}

	@Override
	public void showRequest(Integer requestId) {
		History.newItem(AppPlaces.RESPONSE.getName() + "?requestId=" + requestId.toString());
	}

	@Override
	public void onSearch(RequestSearchEvent event) {
		display.displayMessage("Se ha recibido el evento de busqueda");
	}
}
