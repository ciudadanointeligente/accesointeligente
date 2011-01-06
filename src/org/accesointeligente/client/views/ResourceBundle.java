package org.accesointeligente.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface ResourceBundle extends ClientBundle {
	public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

	@Source("RequestListView.css")
	RequestListViewCss RequestListView();
}
