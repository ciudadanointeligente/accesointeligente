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
package org.accesointeligente.client.widgets;

import org.accesointeligente.client.AnchorCell;
import org.accesointeligente.client.AnchorCellParams;
import org.accesointeligente.model.Attachment;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;

import java.util.Date;

public class ResponseWidget extends Composite {
	private static ResponseWidgetUiBinder uiBinder = GWT.create(ResponseWidgetUiBinder.class);
	interface ResponseWidgetUiBinder extends UiBinder<Widget, ResponseWidget> {}

	@UiField Label responseDate;
	@UiField Label responseInfo;
	@UiField HTMLPanel responseAttachmentsPanel;
	@UiField CellTable<Attachment> attachmentsTable;
	@UiField FlowPanel userResponsePanel;

	public ResponseWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setInfo(String info) {
		responseInfo.setText(info);
	}

	public void setDate(Date date) {
		responseDate.setText(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(date));
	}

	public void initTableColumns() {
		// Name
		Column<Attachment, AnchorCellParams> downloadColumn = new Column<Attachment, AnchorCellParams>(new AnchorCell()) {
			@Override
			public AnchorCellParams getValue(final Attachment attachment) {
				AnchorCellParams params = new AnchorCellParams();
				params.setUrl(new SafeUri() {

					@Override
					public String asString() {
						return attachment.getUrl();
					}
				});
				params.setStyleNames("");
				params.setValue(attachment.getName());
				return params;
			}
		};
		attachmentsTable.addColumn(downloadColumn, "Nombre");

		// Type
		Column<Attachment, String> typeColumn = new Column<Attachment, String>(new TextCell()) {
			@Override
			public String getValue(Attachment attachment) {
				return attachment.getType().getName();
			}
		};
		attachmentsTable.addColumn(typeColumn, "Tipo");
	}

	public void setResponseAttachments(ListDataProvider<Attachment> data) {
		data.addDataDisplay(attachmentsTable);
	}

	public void add(Widget widget) {
		userResponsePanel.add(widget);
	}

	public void clearUserResponsePanel() {
		userResponsePanel.clear();
	}
}
