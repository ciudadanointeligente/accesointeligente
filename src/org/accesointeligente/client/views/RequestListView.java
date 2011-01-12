package org.accesointeligente.client.views;

import org.accesointeligente.client.*;
import org.accesointeligente.client.CustomActionCell.Delegate;
import org.accesointeligente.client.presenters.RequestListPresenter;
import org.accesointeligente.client.presenters.RequestListPresenterIface;
import org.accesointeligente.model.Request;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class RequestListView extends Composite implements RequestListPresenter.Display {
	private static RequestListViewUiBinder uiBinder = GWT.create(RequestListViewUiBinder.class);

	interface RequestListViewUiBinder extends UiBinder<Widget, RequestListView> {
	}

	@UiField Anchor requestLink;
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
				params.setUrl("#response?requestId=" + request.getId());
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
				params.setText(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(request.getDate()));
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

					if (request.getResponse() != null) {
						responseDate = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(request.getResponse().getDate());
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
		Column<Request, Request> viewButtonColumn = new Column<Request, Request>(
				new CustomActionCell<Request>("+", ResourceBundle.INSTANCE.RequestListView().reqTableViewMore() ,new Delegate<Request>() {

			public void execute(Request request) {
				presenter.showRequest(request.getId());
			}
		})) {
			@Override
			public Request getValue(Request request) {
				return request;
			}
		};
		requestTable.addColumn(viewButtonColumn, "Ver +");
	}

	@Override
	public void initTableFavColumn() {
		// Favorite Request
		Column<Request, Request> favButtonColumn = new Column<Request, Request>(
				new CustomActionCell<Request>("", ResourceBundle.INSTANCE.RequestListView().reqTableNoFavorite(), new Delegate<Request>() {

			public void execute(Request request) {
				presenter.requestToggleFavorite(request);
			}
		})) {
			@Override
			public Request getValue(Request request) {
				return request;
			}
		};
		requestTable.addColumn(favButtonColumn, "Seguir");
	}

	@Override
	public void setRequests(ListDataProvider<Request> data) {
		data.addDataDisplay(requestTable);
	}

	@UiFactory
	SimplePager getPager() {
		requestPager = new SimplePager(TextLocation.CENTER);
		return requestPager;
	}

	@UiHandler("requestLink")
	public void onRequestListLinkClick(ClickEvent event) {
		History.newItem("request");
	}
}
