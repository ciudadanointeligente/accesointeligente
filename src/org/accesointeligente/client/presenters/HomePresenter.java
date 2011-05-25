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

import org.accesointeligente.client.uihandlers.HomeUiHandlers;
import org.accesointeligente.shared.AppPlace;
import org.accesointeligente.shared.RequestListType;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.*;

import com.google.gwt.event.shared.EventBus;

import javax.inject.Inject;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers {
	public interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
	}

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
	public void gotoRequest() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.REQUEST));
	}

	@Override
	public void gotoList() {
		placeManager.revealPlace(new PlaceRequest(AppPlace.LIST).with("type", RequestListType.GENERAL.getType()));
	}
}
