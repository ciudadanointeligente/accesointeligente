package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.AsociatesPresenter;
import org.accesointeligente.client.presenters.AsociatesPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AsociatesView extends Composite implements AsociatesPresenter.Display {
	private static AsociatesViewUiBinder uiBinder = GWT.create(AsociatesViewUiBinder.class);
	interface AsociatesViewUiBinder extends UiBinder<Widget, AsociatesView> {}

	AsociatesPresenterIface presenter;

	public AsociatesView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AsociatesPresenterIface presenter) {
		this.presenter = presenter;
	}
}
