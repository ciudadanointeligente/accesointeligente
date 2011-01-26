package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.AboutProjectPresenter;
import org.accesointeligente.client.presenters.AboutProjectPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AboutProjectView extends Composite implements AboutProjectPresenter.Display {
	private static AboutProjectViewUiBinder uiBinder = GWT.create(AboutProjectViewUiBinder.class);
	interface AboutProjectViewUiBinder extends UiBinder<Widget, AboutProjectView> {}

	AboutProjectPresenterIface presenter;

	public AboutProjectView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AboutProjectPresenterIface presenter) {
		this.presenter = presenter;
	}
}
