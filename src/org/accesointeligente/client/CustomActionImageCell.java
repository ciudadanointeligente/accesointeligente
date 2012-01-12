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
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;

public class CustomActionImageCell<C> extends AbstractCell<CustomActionImageCellParams> {
	public static interface Delegate<C> {
		void execute(CustomActionImageCellParams object);
	}

	interface Template extends SafeHtmlTemplates {
		@Template("<div style=\"text-align: center;\" tabindex=\"-1\"><img src=\"{0}\" title=\"{1}\" style=\"cursor: pointer;\" tabindex=\"-1\"/></div>")
		SafeHtml img(SafeUri url, String title);
	}

	private static Template template;
	private final Delegate<CustomActionImageCellParams> delegate;

	public CustomActionImageCell(Delegate<CustomActionImageCellParams> delegate) {
		super("click", "keydown");
		this.delegate = delegate;

		if (template == null) {
			template = GWT.create(Template.class);
		}
	}

	@Override
	public void render(Context context, CustomActionImageCellParams value, SafeHtmlBuilder sb) {
		if (value != null) {
			// The template will sanitize the URI.
			sb.append(template.img(value.getUrl(), value.getTitle()));
		}
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, CustomActionImageCellParams value, NativeEvent event, ValueUpdater<CustomActionImageCellParams> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("click".equals(event.getType())) {
			onEnterKeyDown(context, parent, value, event, valueUpdater);
		}
	}

	@Override
	protected void onEnterKeyDown(Context context, Element parent, CustomActionImageCellParams value, NativeEvent event, ValueUpdater<CustomActionImageCellParams> valueUpdater) {
		delegate.execute(value);
	}
}
