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

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

public class UserResponseWidget extends Composite {
	private static UserResponseWidgetUiBinder uiBinder = GWT.create(UserResponseWidgetUiBinder.class);
	interface UserResponseWidgetUiBinder extends UiBinder<Widget, UserResponseWidget> {}

	@UiField Label userResponseDate;
	@UiField Label userResponseInformation;

	public UserResponseWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setDate(Date date) {
		userResponseDate.setText(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(date));
	}

	public void setInformation(String information) {
		userResponseInformation.setText(information);
	}
}
