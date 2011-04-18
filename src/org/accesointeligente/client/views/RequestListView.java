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

import org.accesointeligente.client.*;
import org.accesointeligente.client.CustomActionImageCell.Delegate;
import org.accesointeligente.client.presenters.RequestListPresenter;
import org.accesointeligente.client.presenters.RequestListPresenterIface;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.Response;
import org.accesointeligente.model.UserFavoriteRequest;
import org.accesointeligente.shared.AppPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

import java.util.ArrayList;
import java.util.List;

public class RequestListView extends Composite implements RequestListPresenter.Display {
	private static RequestListViewUiBinder uiBinder = GWT.create(RequestListViewUiBinder.class);

	interface RequestListViewUiBinder extends UiBinder<Widget, RequestListView> {
	}

	@UiField Anchor requestLink;
	@UiField HTMLPanel searchToolTip;
	@UiField Label listTitle;
	@UiField Anchor searchPanelHandle;
	@UiField FlowPanel searchPanel;
	@UiField CellTable<Request> requestTable;
	@UiField SimplePager requestPager;

	private RequestListPresenterIface presenter;

	public RequestListView() {
		initWidget(uiBinder.createAndBindUi(this));
		ResourceBundle.INSTANCE.RequestListView().ensureInjected();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(RequestListPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void displayMessage(String message) {
		Window.alert(message);
	}

	@Override
	public void setListTitle(String title)  {
		listTitle.setText(title);
	}

	@Override
	public void setListTitleStyle(String style)  {
		listTitle.setStyleName(listTitle.getStyleName() + " " + style);
	}

	@Override
	public void setSearchWidget(Widget widget) {
		searchPanel.clear();
		searchPanel.add(widget);
	}

	@Override
	public void removeSearchWidget() {
		searchPanel.setVisible(false);
		searchPanelHandle.setVisible(false);
	}

	@Override
	public void initTable() {
		initTableColumns();
		requestPager.setDisplay(requestTable);
	}

	@Override
	public void initTableColumns() {
		// Status
		Column<Request, CustomImageCellParams> statusColumn = new Column<Request, CustomImageCellParams>(new CustomImageCell()) {
			@Override
			public CustomImageCellParams getValue(Request request) {
				CustomImageCellParams params = new CustomImageCellParams();
				params.setUrl(request.getStatus().getUrl());
				params.setTitle(request.getStatus().getName());
				params.setStyleNames(ResourceBundle.INSTANCE.RequestListView().reqTableStatus());
				return params;
			}
		};
		requestTable.addColumn(statusColumn, "Estado");

		// Title
		Column<Request, AnchorCellParams> titleColumn = new Column<Request, AnchorCellParams>(new AnchorCell()) {
			@Override
			public AnchorCellParams getValue(Request request) {
				AnchorCellParams params = new AnchorCellParams();
				params.setValue(request.getTitle());
				params.setUrl(presenter.getRequestBaseUrlPlace() + request.getId());
				params.setStyleNames(ResourceBundle.INSTANCE.RequestListView().reqTableTitle());
				return params;
			}
		};
		requestTable.addColumn(titleColumn, "Titulo");

		// Institution
		Column<Request, CustomTextCellParams> institutionColumn = new Column<Request, CustomTextCellParams>(new CustomTextCell()) {
			@Override
			public CustomTextCellParams getValue(Request request) {
				CustomTextCellParams params = new CustomTextCellParams();
				params.setText(request.getInstitution().getName());
				params.setStyleNames(ResourceBundle.INSTANCE.RequestListView().reqTableInstitution());
				return params;
			}
		};
		requestTable.addColumn(institutionColumn, "Organismo");

		// Request Date
		Column<Request, CustomTextCellParams> requestDateColumn = new Column<Request, CustomTextCellParams>(new CustomTextCell()) {
			@Override
			public CustomTextCellParams getValue(Request request) {
				CustomTextCellParams params = new CustomTextCellParams();
				if (request.getConfirmationDate() == null) {
					params.setText(DateTimeFormat.getFormat("dd/MM/yyyy").format(request.getCreationDate()));
				} else {
					params.setText(DateTimeFormat.getFormat("dd/MM/yyyy").format(request.getConfirmationDate()));
				}
				params.setStyleNames(ResourceBundle.INSTANCE.RequestListView().reqTableRequestDate());
				return params;
			}
		};
		requestTable.addColumn(requestDateColumn, "Consulta");

		// Response Date
		Column<Request, CustomTextCellParams> responseDateColumn = new Column<Request, CustomTextCellParams>(new CustomTextCell()) {
			@Override
			public CustomTextCellParams getValue(Request request) {
				CustomTextCellParams params = new CustomTextCellParams();
				params.setStyleNames(ResourceBundle.INSTANCE.RequestListView().reqTableResponseDate());
				try {
					String responseDate = "Esperando respuesta";

					if (request.getResponses() != null && request.getResponses().size() > 0) {
						List<Response> responses = new ArrayList<Response>(request.getResponses());
						responseDate = DateTimeFormat.getFormat("dd/MM/yyyy").format(responses.get(0).getDate());
					}
					params.setText(responseDate);
					return params;
				} catch (Exception e) {
					Window.alert(e.getMessage());
					params.setText("");
					return params;
				}
			}
		};
		requestTable.addColumn(responseDateColumn, "Respuesta");

		// View Request
		Column<Request, CustomActionImageCellParams> viewButtonColumn = new Column<Request, CustomActionImageCellParams>(
				new CustomActionImageCell<CustomActionImageCellParams>(new Delegate<CustomActionImageCellParams>() {

			public void execute(CustomActionImageCellParams params) {
				presenter.showRequest(((Request) params.getValue()).getId());
			}
		})) {
			@Override
			public CustomActionImageCellParams getValue(Request request) {
				CustomActionImageCellParams params = new CustomActionImageCellParams();
				params.setUrl("images/reqList/viewMore.png");
				params.setTitle("Ver +");
				params.setValue(request);
				return params;
			}
		};
		requestTable.addColumn(viewButtonColumn, "Ver +");
	}

	@Override
	public void initTableFavColumn() {
		// Favorite Request
		Column<Request, CustomActionImageCellParams> favButtonColumn = new Column<Request, CustomActionImageCellParams>(
				new CustomActionImageCell<CustomActionImageCellParams>(new Delegate<CustomActionImageCellParams>() {

			public void execute(CustomActionImageCellParams params) {
				presenter.requestToggleFavorite((Request) params.getValue());
			}
		})) {
			@Override
			public CustomActionImageCellParams getValue(Request request) {
				CustomActionImageCellParams params = new CustomActionImageCellParams();
				params.setUrl("images/reqList/no-favorite.png");
				params.setTitle("Seguir");

				for (UserFavoriteRequest favorite : request.getFavorites()) {
					if (ClientSessionUtil.getUser().equals(favorite.getUser())) {
						params.setUrl("images/reqList/favorite.png");
						params.setTitle("Dejar de seguir");
						break;
					}
				}

				params.setValue(request);
				return params;
			}
		};
		requestTable.addColumn(favButtonColumn, "Seguir");
	}

	@Override
	public void initTableReceiptColumn() {
		// Favorite Request
		Column<Request, CustomActionImageCellParams> receiptButtonColumn = new Column<Request, CustomActionImageCellParams>(
				new CustomActionImageCell<CustomActionImageCellParams>(new Delegate<CustomActionImageCellParams>() {

			public void execute(CustomActionImageCellParams params) {
				Request request = (Request) params.getValue();
				Window.Location.replace("receipt?requestId=" + request.getId());
			}
		})) {
			@Override
			public CustomActionImageCellParams getValue(Request request) {
				CustomActionImageCellParams params = new CustomActionImageCellParams();
				params.setUrl("images/mandato.png");
				params.setTitle("Descarga el mandato");
				params.setValue(request);
				return params;
			}
		};
		requestTable.addColumn(receiptButtonColumn, "Mandato");
	}

	@Override
	public void removeColumns() {
		while (requestTable.getColumnCount() > 0) {
			requestTable.removeColumn(0);
		}
	}

	@Override
	public void setRequests(ListDataProvider<Request> data) {
		data.addDataDisplay(requestTable);
	}

	@Override
	public void searchPanelToggleVisible() {
		if (searchPanel.isVisible()) {
			searchPanel.setVisible(false);
		} else {
			searchPanel.setVisible(true);
		}
	}

	@Override
	public void searchToolTipToggleVisible() {
		if (searchToolTip.isVisible()) {
			searchToolTip.setVisible(false);
		} else {
			searchToolTip.setVisible(true);
		}
	}

	@UiFactory
	SimplePager getPager() {
		requestPager = new SimplePager(TextLocation.CENTER);
		return requestPager;
	}

	@UiHandler("requestLink")
	public void onRequestListLinkClick(ClickEvent event) {
		History.newItem(AppPlace.REQUEST.getToken());
	}

	@UiHandler("searchPanelHandle")
	public void onSearchPanelHandleClick(ClickEvent event) {
		searchPanelToggleVisible();
	}
}
