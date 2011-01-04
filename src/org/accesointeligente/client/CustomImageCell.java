package org.accesointeligente.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CustomImageCell extends AbstractCell<CustomImageCellParams> {

	interface Template extends SafeHtmlTemplates {
		@Template("<img src=\"{0}\" title=\"{1}\"/>")
		SafeHtml img(String url, String title);
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
			sb.append(template.img(value.getUrl(), value.getTitle()));
		}
	}
}
