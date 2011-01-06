package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestSearchPresenter;
import org.accesointeligente.client.presenters.RequestSearchPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RequestSearchView extends Composite implements RequestSearchPresenter.Display {
	private static RequestSearchViewUiBinder uiBinder = GWT.create(RequestSearchViewUiBinder.class);

	interface RequestSearchViewUiBinder extends UiBinder<Widget, RequestSearchView> {}

	private RequestSearchPresenterIface presenter;

	public RequestSearchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(RequestSearchPresenterIface presenter) {
		this.presenter = presenter;
	}
}
