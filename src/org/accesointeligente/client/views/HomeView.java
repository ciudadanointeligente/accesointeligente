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

import org.accesointeligente.client.AnchorCell;
import org.accesointeligente.client.AnchorCellParams;
import org.accesointeligente.client.presenters.HomePresenter;
import org.accesointeligente.client.uihandlers.HomeUiHandlers;
import org.accesointeligente.client.widgets.ShareThis;
import org.accesointeligente.model.Request;
import org.accesointeligente.shared.AppPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements HomePresenter.MyView {
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {}
	private final Widget widget;

	@UiField FocusPanel requestFormLink;
	@UiField FocusPanel requestListLink;
	@UiField FlowPanel lastResponses;
	@UiField CellTable<Request> lastResponsesRequestTable;
	@UiField HTMLPanel sharePanel;
	private ShareThis share;

	public HomeView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void initTable() {
		// Title
		Column<Request, AnchorCellParams> titleColumn = new Column<Request, AnchorCellParams>(new AnchorCell()) {
			@Override
			public AnchorCellParams getValue(final Request request) {
				AnchorCellParams params = new AnchorCellParams();
				params.setValue(request.getTitle());
				final String baseUrl = "#" + AppPlace.RESPONSE + ";requestId=";
				params.setUrl(new SafeUri() {

					@Override
					public String asString() {
						return baseUrl + request.getId();
					}
				});
				params.setStyleNames(ResourceBundle.INSTANCE.RequestListView().reqTableTitle());
				return params;
			}
		};
		lastResponsesRequestTable.addColumn(titleColumn);
	}

	@Override
	public void removeColumns() {
		while (lastResponsesRequestTable.getColumnCount() > 0) {
			lastResponsesRequestTable.removeColumn(0);
		}
	}

	@Override
	public void setRequests(ListDataProvider<Request> data) {
		data.addDataDisplay(lastResponsesRequestTable);
	}

	@Override
	public void setShare(String href) {
		sharePanel.clear();
		share = new ShareThis();
		share.setHref(href);
		// TODO: define social network messages
		share.setTitle("AccesoInteligente");
		share.setMessage("La información es tuya #AccesoInteligente");
		share.setLangLong("es_CL");
		share.setup();
		sharePanel.add(share);
	}

	@UiHandler("requestFormLink")
	public void onRequestFormLinkClick(ClickEvent event) {
		getUiHandlers().gotoRequest();
	}

	@UiHandler("requestListLink")
	public void onRequestListLinkClick(ClickEvent event) {
		getUiHandlers().gotoList();
	}
}
