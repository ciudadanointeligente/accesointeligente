package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.MainPresenter;
import org.accesointeligente.client.presenters.MainPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class MainView extends Composite implements MainPresenter.Display {
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}

	public enum DisplayMode {
		LoggedIn,
		LoggedOut,
		LoginPending
	}

	@UiField HTMLPanel headerPanel;
	@UiField FlowPanel mainPanel;
	@UiField Hyperlink loginLink;
	@UiField Hyperlink logoutLink;
	@UiField Label loginPending;

	private MainPresenterIface presenter;

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(MainPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setDisplayMode(DisplayMode mode) {
		headerPanel.setVisible(DisplayMode.LoggedIn.equals(mode) || DisplayMode.LoggedOut.equals(mode));
		mainPanel.setVisible(DisplayMode.LoggedIn.equals(mode) || DisplayMode.LoggedOut.equals(mode));
		loginLink.setVisible(DisplayMode.LoggedOut.equals(mode));
		logoutLink.setVisible(DisplayMode.LoggedIn.equals(mode));
		loginPending.setVisible(DisplayMode.LoginPending.equals(mode));
	}

	@Override
	public FlowPanel getLayout() {
		return mainPanel;
	}
}
