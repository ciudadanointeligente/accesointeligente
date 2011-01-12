package org.accesointeligente.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class CustomActionImageCell<C> extends AbstractCell<CustomActionImageCellParams> {
	public static interface Delegate<C> {
		void execute(CustomActionImageCellParams object);
	}

	interface Template extends SafeHtmlTemplates {
		@Template("<div style=\"text-align: center;\" tabindex=\"-1\"><img src=\"{0}\" title=\"{1}\" style=\"cursor: pointer;\" tabindex=\"-1\"/></div>")
		SafeHtml img(String url, String title);
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
