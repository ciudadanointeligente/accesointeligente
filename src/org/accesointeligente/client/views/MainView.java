/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.MainPresenter;
import org.accesointeligente.client.uihandlers.MainUiHandlers;
import org.accesointeligente.shared.NotificationEventParams;
import org.accesointeligente.shared.NotificationEventType;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

public class MainView extends ViewWithUiHandlers<MainUiHandlers> implements MainPresenter.MyView {
	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}
	private final Widget widget;

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
	@UiField MenuItem contact;
	@UiField HTMLPanel footerPanel;
	@UiField HTMLPanel creditsPanel;
	@UiField Label loginPending;

	public MainView() {
		widget = uiBinder.createAndBindUi(this);

		myrequests.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoMyRequests();
			}
		});

		drafts.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoDrafts();
			}
		});

		favorites.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoFavorites();
			}
		});

		userProfile.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoProfile();
			}
		});

		logout.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoLogout();
			}
		});

		statistics.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoStatistics();
			}
		});

		aboutProject.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoAboutProject();
			}
		});

		contact.setCommand(new Command() {
			@Override
			public void execute() {
				getUiHandlers().gotoContact();
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (MainPresenter.SLOT_MAIN_CONTENT.equals(slot)) {
			mainPanel.clear();

			if (content != null) {
				mainPanel.add(content);
			}
		} else {
			super.addToSlot(slot, content);
		}
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
		creditsPanel.setVisible(DisplayMode.LoggedIn.equals(mode) || DisplayMode.LoggedOut.equals(mode));
		loginPending.setVisible(DisplayMode.LoginPending.equals(mode));

		if (DisplayMode.LoggedIn.equals(mode)) {
			myMenu.setText("Mi cuenta");
			myMenu.setCommand(null);
		} else {
			myMenu.setText("Ingresar");
			myMenu.setCommand(new Command() {
				@Override
				public void execute() {
					getUiHandlers().gotoLogin();
				}
			});
		}
	}

	@Override
	public void setWelcomeMessage(String message) {
		welcomeMessage.setText(message);
	}

	@Override
	public void setNotificationMessage(final NotificationEventParams params) {
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

		if (params.getDuration() > 0) {
			notificationTimer.schedule(params.getDuration());
		}

		notificationClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				notificationPanel.remove(notification);
			}
		});

		notification.add(notificationClose);
		notification.add(notificationLabel);

		if (params.getType().equals(NotificationEventType.SUCCESS)) {
			clearNotifications();
		}

		notificationPanel.insert(notification, 0);

		if (notificationPanel.getWidgetCount() > 3) {
			for (Integer index = 3; index < notificationPanel.getWidgetCount(); index++) {
				notificationPanel.remove(index);
			}
		}
	}

	@Override
	public void clearNotifications() {
		notificationPanel.clear();
	}

	@UiHandler("logo")
	public void onLogoClick(ClickEvent event) {
		getUiHandlers().gotoHome();
	}
}
