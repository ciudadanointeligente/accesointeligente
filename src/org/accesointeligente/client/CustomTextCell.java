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
package org.accesointeligente.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CustomTextCell extends AbstractCell<CustomTextCellParams> {

	interface Template extends SafeHtmlTemplates {
		@Template("<div class=\"{0}\">{1}</div>")
		SafeHtml img(String styleNames, String text);
	}

	private static Template template;

	public CustomTextCell() {
		if (template == null) {
			template = GWT.create(Template.class);
		}
	}

	@Override
	public void render(Context context, CustomTextCellParams value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.append(template.img(value.getStyleNames(), value.getText()));
		}
	}
}
