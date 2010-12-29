package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.HomePresenter;
import org.accesointeligente.client.presenters.HomePresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;

public class HomeView extends Composite implements HomePresenter.Display {
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	interface HomeViewUiBinder extends UiBinder<Widget, HomeView> {}

	@UiField FocusPanel requestFormLink;
	@UiField FocusPanel requestListLink;
	@UiField FlowPanel lastResponses;

	private HomePresenterIface presenter;

	public HomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(HomePresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setAuthenticated(Boolean authenticated) {
		requestFormLink.setVisible(authenticated);
	}

	@UiHandler("requestFormLink")
	public void onRequestFormLinkClick(ClickEvent event) {
		History.newItem("request");
	}
}
