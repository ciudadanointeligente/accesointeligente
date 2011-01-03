package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestListPresenter;
import org.accesointeligente.client.presenters.RequestListPresenterIface;
import org.accesointeligente.model.Request;

import com.google.gwt.cell.client.*;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
	interface RequestListViewUiBinder extends UiBinder<Widget, RequestListView> {}

	@UiField Anchor requestLink;
	@UiField CellTable<Request> requestTable;
	@UiField SimplePager requestPager;

	private RequestListPresenterIface presenter;

	public RequestListView() {
		initWidget(uiBinder.createAndBindUi(this));
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
		Column<Request, String> statusColumn = new Column<Request, String>(new ImageCell()) {
			@Override
			public String getValue(Request request) {
				return request.getStatus().getUrl();
			}
		};
		requestTable.addColumn(statusColumn, "Estado");

		// Title
		Column<Request, String> titleColumn = new Column<Request, String>(new TextCell()) {
			@Override
			public String getValue(Request request) {
				return request.getTitle();
			}
		};
		requestTable.addColumn(titleColumn, "Nombre de la solicitud");

		// Institution
		Column<Request, String> institutionColumn = new Column<Request, String>(new TextCell()) {
			@Override
			public String getValue(Request request) {
				return request.getInstitution().getName();
			}
		};
		requestTable.addColumn(institutionColumn, "Institución");

		// Date
		Column<Request, String> dateColumn = new Column<Request, String>(new TextCell()) {
			@Override
			public String getValue(Request request) {
				return DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(request.getDate());
			}
		};
		requestTable.addColumn(dateColumn, "Fecha");

		// Action
		Column<Request, Request> buttonColumn = new Column<Request, Request>(new ActionCell<Request> ("Ver solicitud", new Delegate<Request> () {
			public void execute (Request request) {
				presenter.showRequest(request.getId());
			}
		})) {
			@Override
			public Request getValue(Request request) {
				return request;
			}
		};
		requestTable.addColumn(buttonColumn, "Acciones");

	}

	@Override
	public void setTableSize(Integer size) {
		requestTable.setRowCount(size, true);
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
