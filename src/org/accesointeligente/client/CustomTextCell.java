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
