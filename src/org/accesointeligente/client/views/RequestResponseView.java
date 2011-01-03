package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestResponsePresenter;
import org.accesointeligente.client.presenters.RequestResponsePresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RequestResponseView extends Composite implements RequestResponsePresenter.Display {
	private static RequestResponseViewUiBinder uiBinder = GWT.create(RequestResponseViewUiBinder.class);
	interface RequestResponseViewUiBinder extends UiBinder<Widget, RequestResponseView> {}

	private RequestResponsePresenterIface presenter;

	public RequestResponseView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(RequestResponsePresenterIface presenter) {
		this.presenter = presenter;
	}
}
