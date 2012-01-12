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

import org.accesointeligente.client.services.RequestServiceAsync;
import org.accesointeligente.client.uihandlers.HomeUiHandlers;
import org.accesointeligente.model.Request;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

import javax.inject.Inject;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers {
	public interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		void initTable();
		void removeColumns();
		void setRequests(ListDataProvider<Request> data);
		void setShare(String href);
	}

	@Inject
	private RequestServiceAsync requestService;

	@ProxyCodeSplit
	@NameToken(AppPlace.HOME)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void onReset() {
		getView().removeColumns();
		getView().initTable();
		loadLastResponseRequests();
		Window.setTitle("Es tu derecho, es fácil - Acceso Inteligente");
	}

	@Override
	protected void onReveal() {
		getView().setShare(Window.Location.getHref());
	}

	@Override
	public void gotoRequest() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.REQUEST));
	}

	@Override
	public void gotoList() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LIST).with("type", RequestListType.GENERAL.getType()));
	}

	@Override
	public void loadLastResponseRequests() {
		requestService.getLastResponseRequests(new AsyncCallback<List<Request>>() {

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
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		params.setDuration(NotificationEventParams.DURATION_NORMAL);
		fireEvent(new NotificationEvent(params));
	}
}
