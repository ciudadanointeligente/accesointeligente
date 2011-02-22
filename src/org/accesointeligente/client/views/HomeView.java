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
package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.HomePresenter;
import org.accesointeligente.client.presenters.HomePresenterIface;
import org.accesointeligente.shared.AppPlace;
import org.accesointeligente.shared.RequestListType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;

public class HomeView extends Composite implements HomePresenter.Display {
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {}

	@UiField FocusPanel requestFormLink;
	@UiField FocusPanel requestListLink;
	@UiField FlowPanel lastResponses;

	private HomePresenterIface presenter;

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(HomePresenterIface presenter) {
		this.presenter = presenter;
	}

	@UiHandler("requestFormLink")
	public void onRequestFormLinkClick(ClickEvent event) {
		History.newItem(AppPlace.REQUEST.getToken());
	}

   @UiHandler("requestListLink")
   public void onRequestListLinkClick(ClickEvent event) {
           History.newItem(AppPlace.LIST.getToken() + "?type=" + RequestListType.GENERAL.getType());
   }
}
