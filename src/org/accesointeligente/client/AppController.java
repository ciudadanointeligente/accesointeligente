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
package org.accesointeligente.client;

import org.accesointeligente.client.events.*;
import org.accesointeligente.client.inject.PresenterInjector;
import org.accesointeligente.client.presenters.*;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;

import java.util.*;

public class AppController implements ValueChangeHandler<String>, LoginRequiredEventHandler, LoginSuccessfulEventHandler {
	private final PresenterInjector presenterInjector = GWT.create(PresenterInjector.class);
	private MainPresenter mainPresenter;
	private EventBus eventBus;
	private PopupPanel popup;
	private static List<String> tokenHistory;

	public AppController() {
		eventBus = presenterInjector.getEventBus();
		tokenHistory = new ArrayList<String>();
		popup = new PopupPanel();
	}

	protected void setup() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);
		eventBus.addHandler(LoginRequiredEvent.TYPE, this);
		mainPresenter = presenterInjector.getMainPresenter();
		mainPresenter.setup();
		RootPanel.get().add(mainPresenter.getDisplay().asWidget());
		switchSection("home");
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		switchSection(token);
	}

	@Override
	public void loginSuccessful(LoginSuccessfulEvent event) {
		History.newItem(getPreviousHistoryToken());
	}

	@Override
	public void loginRequired(LoginRequiredEvent event) {
		if (!AppPlace.HOME.equals(getPlace(History.getToken()))) {
			History.newItem(AppPlace.HOME.getToken());
		}
	}

	public void switchSection(String token) {
		popup.hide();
		tokenHistory.add(token);
		Map<String, String> parameters = getHistoryTokenParameters(token);
		AppPlace place = getPlace(token);

		if (mainPresenter != null && place != AppPlace.HOME && place != AppPlace.LIST && place != AppPlace.EDITREQUEST && place != AppPlace.REQUESTSTATUS) {
			mainPresenter.clearNotifications();
		}

		if (ClientSessionUtil.checkSession()) {
			switch (place) {
				case HOME:
					setupPresenter(presenterInjector.getHomePresenter());
					break;
				case REQUEST:
					setupPresenter(presenterInjector.getRequestPresenter());
					break;
				case LOGOUT:
					ClientSessionUtil.destroySession();
					eventBus.fireEvent(new LoginRequiredEvent());
					break;
				case REQUESTSTATUS:
					try {
						Integer requestId = Integer.parseInt(parameters.get("requestId"));
						RequestStatusPresenter requestStatusPresenter = presenterInjector.getRequestStatusPresenter();
						setupPresenter(requestStatusPresenter);
						requestStatusPresenter.showRequest(requestId);
					} catch (Exception e) {
						showNotification("Id incorrecta: No se puede cargar la solicitud", NotificationEventType.ERROR);
					}
					break;
				case EDITREQUEST:
					try {
						Integer requestId = Integer.parseInt(parameters.get("requestId"));
						RequestEditPresenter editPresenter = presenterInjector.getRequestEditPresenter();
						setupPresenter(editPresenter);
						editPresenter.showRequest(requestId);
					} catch (Exception e) {
						showNotification("Id incorrecta: No se puede cargar la solicitud", NotificationEventType.ERROR);
					}
					break;
				case RESPONSE:
					try {
						Integer requestId = Integer.parseInt(parameters.get("requestId"));
						RequestResponsePresenter requestResponsePresenter = presenterInjector.getRequestResponsePresenter();
						setupPresenter(requestResponsePresenter);
						requestResponsePresenter.showRequest(requestId);
					} catch (Exception e) {
						showNotification("Id incorrecta: No se puede cargar la solicitud", NotificationEventType.ERROR);
					}
					break;
				case LIST:
					try {
						String listType = parameters.get("type");
						RequestListPresenter requestListPresenter = presenterInjector.getRequestListPresenter();
						setupPresenter(requestListPresenter);
						requestListPresenter.loadRequests(0, 100, listType);
					} catch (Exception e) {
						e.printStackTrace();
						showNotification("Tipo de lista incorrecto: No se puede cargar la lista", NotificationEventType.ERROR);
					}
					break;
				case STATISTICS:
					setupPresenter(presenterInjector.getStatisticsPresenter());
					break;
				case ABOUTPROJECT:
					setupPresenter(presenterInjector.getAboutProjectPresenter());
					break;
				case USERPROFILE:
					setupPresenter(presenterInjector.getUserProfileEditPresenter());
					break;
				case GUIDE:
					Frame frame = new Frame("LeyAcceso.html");
					frame.setHeight("600px");
					frame.setStyleName("gwtIframe");
					frame.getElement().setAttribute("scrolling", "no");
					getLayout().clear();
					getLayout().add(frame);
					break;
				case CONTACT:
					setupPresenter(presenterInjector.getContactPresenter());
					break;
				default:
					History.newItem(AppPlace.HOME.toString());
			}
		} else {
			switch (place) {
				case HOME:
					setupPresenter(presenterInjector.getHomePresenter());
					break;
				case REQUEST:
				case LOGIN:
					LoginPresenter loginPresenter = presenterInjector.getLoginPresenter();
					loginPresenter.setup();
					popup.setModal(true);
					popup.setGlassEnabled(true);
					popup.clear();
					popup.add(loginPresenter.getDisplay().asWidget());
					popup.center();
					break;
				case REGISTER:
					setupPresenter(presenterInjector.getRegisterPresenter());
					break;
				case RESPONSE:
					try {
						Integer requestId = Integer.parseInt(parameters.get("requestId"));
						RequestResponsePresenter requestResponsePresenter = presenterInjector.getRequestResponsePresenter();
						setupPresenter(requestResponsePresenter);
						requestResponsePresenter.showRequest(requestId);
					} catch (Exception e) {
						showNotification("Id incorrecta: No se puede cargar la solicitud", NotificationEventType.ERROR);
					}
					break;
				case LIST:
					try {
						String listType = parameters.get("type");
						RequestListPresenter requestListPresenter = presenterInjector.getRequestListPresenter();
						setupPresenter(requestListPresenter);
						requestListPresenter.loadRequests(0, 100, listType);
					} catch (Exception e) {
						showNotification("Tipo de lista incorrecto: No se puede cargar la lista", NotificationEventType.ERROR);
					}
					break;
				case STATISTICS:
					setupPresenter(presenterInjector.getStatisticsPresenter());
					break;
				case ABOUTPROJECT:
					setupPresenter(presenterInjector.getAboutProjectPresenter());
					break;
				case PASSWORDRECOVERY:
					setupPresenter(presenterInjector.getPasswordRecoveryPresenter());
					break;
				case GUIDE:
					Frame frame = new Frame("LeyAcceso.html");
					frame.setHeight("600px");
					frame.setStyleName("gwtIframe");
					frame.getElement().setAttribute("scrolling", "no");
					getLayout().clear();
					getLayout().add(frame);
					break;
				case CONTACT:
					setupPresenter(presenterInjector.getContactPresenter());
					break;
				default:
					History.newItem(AppPlace.HOME.toString());
			}
		}
	}

	public void setupPresenter(CustomWidgetPresenter presenter) {
		presenter.setup();
		getLayout().clear();
		getLayout().add(presenter.getDisplay().asWidget());
	}

	public FlowPanel getLayout() {
		return mainPresenter.getDisplay().getLayout();
	}

	/**
	 * get historyToken parameters
	 *
	 * like domaint.tld#anchor?[var=1&var3=2&var3=3]
	 *
	 * @param historyToken anchor tag
	 * @return hashmap of the parameters
	 */
	public static Map<String, String> getHistoryTokenParameters(String historyToken) {
		// skip if there is no question mark
		if (!historyToken.contains("?")) {
			return null;
		}
		// ? position
		int questionMarkIndex = historyToken.indexOf("?") + 1;

		// get the sub string of parameters var=1&var2=2&var3=3...
		String[] arStr = historyToken.substring(questionMarkIndex, historyToken.length()).split("&");
		Map<String, String> params = new HashMap<String, String>();

		for (int i = 0; i < arStr.length; i++) {
			String[] substr = arStr[i].split("=");
			params.put(substr[0], substr[1]);
		}

		return params;
	}

	public static List<String> getHistoryTokenList() {
		return tokenHistory;
	}

	public static String getCurrentHistoryToken() {
		return getHistoryTokenList().get(getHistoryTokenList().size() - 1);
	}

	public static String getPreviousHistoryToken() {
		if(getHistoryTokenList().size() > 2) {
			return getHistoryTokenList().get(getHistoryTokenList().size() - 2);
		}
		return AppPlace.HOME.getToken();
	}

	public static AppPlace getPlace(String token) {
		AppPlace place = AppPlace.HOME;

		try {
			place = AppPlace.valueOf(token.replaceFirst("^(.*)\\?.*$", "$1").toUpperCase());
		} catch (Exception ex) {
		}

		return place;
	}

	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		params.setDuration(NotificationEventParams.DURATION_NORMAL);
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
