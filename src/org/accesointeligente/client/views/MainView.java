package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.MainPresenter;
import org.accesointeligente.client.presenters.MainPresenterIface;
import org.accesointeligente.shared.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.*;
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
	@UiField Label welcomeMessage;
	@UiField FlowPanel notificationPanel;
	@UiField FlowPanel mainPanel;
	@UiField MenuItem myMenu;
	@UiField MenuItem myrequests;
	@UiField MenuItem drafts;
	@UiField MenuItem favorites;
	@UiField MenuItem userProfile;
	@UiField MenuItem logout;
	@UiField MenuItem statistics;
	@UiField MenuItem aboutProject;
	@UiField MenuItem asociates;
	@UiField MenuItem contact;
	@UiField HTMLPanel footerPanel;
	@UiField Label loginPending;

	private MainPresenterIface presenter;

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));

		myrequests.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.LIST.getToken() + "?type=" + RequestListType.MYREQUESTS.getType());
			}
		});

		drafts.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.LIST.getToken() + "?type=" + RequestListType.DRAFTS.getType());
			}
		});

		favorites.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.LIST.getToken() + "?type=" + RequestListType.FAVORITES.getType());
			}
		});

		userProfile.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.USERPROFILE.getToken());
			}
		});

		logout.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.LOGOUT.getToken());
			}
		});

		statistics.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.STATISTICS.getToken());
			}
		});

		aboutProject.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.ABOUTPROJECT.getToken());
			}
		});

		asociates.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.ASOCIATES.getToken());
			}
		});

		contact.setCommand(new Command() {
			@Override
			public void execute() {
				History.newItem(AppPlace.CONTACT.getToken());
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
		welcomeMessage.setVisible(DisplayMode.LoggedIn.equals(mode));
		mainPanel.setVisible(DisplayMode.LoggedIn.equals(mode) || DisplayMode.LoggedOut.equals(mode));
		myrequests.setVisible(DisplayMode.LoggedIn.equals(mode));
		drafts.setVisible(DisplayMode.LoggedIn.equals(mode));
		favorites.setVisible(DisplayMode.LoggedIn.equals(mode));
		userProfile.setVisible(DisplayMode.LoggedIn.equals(mode));
		logout.setVisible(DisplayMode.LoggedIn.equals(mode));
		footerPanel.setVisible(DisplayMode.LoggedIn.equals(mode) || DisplayMode.LoggedOut.equals(mode));
		loginPending.setVisible(DisplayMode.LoginPending.equals(mode));

		if (DisplayMode.LoggedIn.equals(mode)) {
			myMenu.setText("Mi cuenta");
			myMenu.setCommand(null);
		} else {
			myMenu.setText("Ingresar");
			myMenu.setCommand(new Command() {
				@Override
				public void execute() {
						History.newItem(AppPlace.LOGIN.getToken());
				}
			});
		}
	}

	@Override
	public FlowPanel getLayout() {
		return mainPanel;
	}

	@Override
	public void setWelcomeMessage(String message) {
		welcomeMessage.setText(message);
	}

	@Override
	public void setNotificationMessage(NotificationEventParams params) {
		final FlowPanel notification = new FlowPanel();
		Label notificationClose = new Label();
		Label notificationLabel = new Label(params.getMessage());
		notification.setStyleName(params.getType().getType());
		notificationClose.addStyleName("closeNotice");
		notificationLabel.addStyleName("fLeft");
		notification.setVisible(true);

		Timer notificationTimer = new Timer() {
			@Override
			public void run() {
				notification.setVisible(false);
			}
		};
		notificationTimer.schedule(15000);

		notificationClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				notification.setVisible(false);

			}
		});

		notification.add(notificationClose);
		notification.add(notificationLabel);
		if(params.getType().equals(NotificationEventType.SUCCESS)) {
			clearNotifications();
		}
		notificationPanel.insert(notification, 0);
		if (notificationPanel.getWidgetCount() > 3) {
			for (int i = 3; i < notificationPanel.getWidgetCount(); i++) {
				notificationPanel.remove(i);
			}
		}
	}

	@Override
	public void clearNotifications() {
		notificationPanel.clear();
	}

	@UiHandler("logo")
	public void onLogoClick(ClickEvent event) {
		History.newItem(AppPlace.HOME.getToken());
	}
}
