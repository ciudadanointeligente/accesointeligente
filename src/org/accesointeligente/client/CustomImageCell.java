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
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;

public class CustomImageCell extends AbstractCell<CustomImageCellParams> {

	interface Template extends SafeHtmlTemplates {
		@Template("<img src=\"{0}\" title=\"{1}\" class=\"{2}\"/>")
		SafeHtml img(SafeUri url, String title, String styleNames);
	}

	private static Template template;

	/**
	 * Construct a new ImageCell.
	 */
	public CustomImageCell() {
		if (template == null) {
			template = GWT.create(Template.class);
		}
	}

	@Override
	public void render(Context context, CustomImageCellParams value, SafeHtmlBuilder sb) {
		if (value != null) {
			// The template will sanitize the URI.
			sb.append(template.img(value.getUrl(), value.getTitle(), value.getStyleNames()));
		}
	}
}
