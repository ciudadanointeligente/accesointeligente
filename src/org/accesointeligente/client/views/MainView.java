package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.MainPresenter;
import org.accesointeligente.client.presenters.MainPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
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
	@UiField Image logo;
	@UiField FlowPanel mainPanel;
	@UiField MenuItem login;
	@UiField MenuItem logout;
	@UiField MenuItem home;
	@UiField MenuItem about;
	@UiField MenuItem contact;
	@UiField HTMLPanel footerPanel;
	@UiField Label loginPending;

	private MainPresenterIface presenter;

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));

		login.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem("login");
			}
		});

		logout.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem("logout");
			}
		});

		home.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem("home");
			}
		});

		about.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem("about");
			}
		});

		contact.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem("contact");
			}
		});
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
		login.setVisible(DisplayMode.LoggedOut.equals(mode));
		logout.setVisible(DisplayMode.LoggedIn.equals(mode));
		footerPanel.setVisible(DisplayMode.LoggedIn.equals(mode) || DisplayMode.LoggedOut.equals(mode));
		loginPending.setVisible(DisplayMode.LoginPending.equals(mode));
	}

	@Override
	public FlowPanel getLayout() {
		return mainPanel;
	}

	@UiHandler("logo")
	public void onLogoClick(ClickEvent event) {
		History.newItem("home");
	}
}
