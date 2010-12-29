package org.accesointeligente.client;

import org.accesointeligente.client.events.*;
import org.accesointeligente.client.presenters.*;
import org.accesointeligente.client.views.*;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.HashMap;
import java.util.Map;

public class AppController implements ValueChangeHandler<String> {
	private MainPresenter mainPresenter;
	private EventBus eventBus;
	private PopupPanel popup;

	public AppController(EventBus eventBus) {
		this.eventBus = eventBus;
		mainPresenter = new MainPresenter(new MainView(), eventBus);
		this.eventBus.addHandler(LoginRequiredEvent.TYPE, mainPresenter);
		this.eventBus.addHandler(LoginSuccessfulEvent.TYPE, mainPresenter);
		popup = new PopupPanel();
	}

	protected void setup() {
		RootPanel.get().add(mainPresenter.getDisplay().asWidget());

		mainPresenter.bind();
		History.addValueChangeHandler(this);

		eventBus.addHandler(LoginSuccessfulEvent.TYPE, new LoginSuccessfulEventHandler() {
			@Override
			public void loginSuccessful(LoginSuccessfulEvent event) {
				if (!"home".equals(History.getToken())) {
					History.newItem("home");
				}
			}
		});

		eventBus.addHandler(LoginRequiredEvent.TYPE, new LoginRequiredEventHandler() {
			@Override
			public void loginRequired(LoginRequiredEvent event) {
				if (!"home".equals(History.getToken())) {
					History.newItem("home");
				}
			}
		});

		switchSection(History.getToken());
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		switchSection(token);
	}

	public void switchSection(String token) {
		popup.hide();

		if (ClientSessionUtil.checkSession()) {
			if (token.equals("home")) {
				HomePresenter presenter = new HomePresenter(new HomeView(), eventBus);
				presenter.bind();
				getLayout().clear();
				getLayout().add(presenter.getDisplay().asWidget());
			} else if (token.equals("request")) {
				RequestPresenter presenter = new RequestPresenter(new RequestView(), eventBus);
				presenter.bind();
				getLayout().clear();
				getLayout().add(presenter.getDisplay().asWidget());
			} else if (token.equals("logout")) {
				ClientSessionUtil.destroySession();
				eventBus.fireEvent(new LoginRequiredEvent());
			} else if (token.startsWith("status")) {
				Map<String, String> parameters = getHistoryTokenParameters(token);
				try {
					Integer requestId = Integer.parseInt(parameters.get("requestId"));
					RequestStatusPresenter presenter = new RequestStatusPresenter(new RequestStatusView(), eventBus);
					presenter.bind();
					presenter.showRequest(requestId);
					getLayout().clear();
					getLayout().add(presenter.getDisplay().asWidget());
				} catch (Exception e) {
					Window.alert("Id incorrecta: No se puede cargar la solicitud");
				}
			} else {
				History.newItem("home");
			}
		} else {
			if (token.equals("home")) {
				HomePresenter presenter = new HomePresenter(new HomeView(), eventBus);
				presenter.bind();
				getLayout().clear();
				getLayout().add(presenter.getDisplay().asWidget());
			} else if (token.equals("login")) {
				LoginPresenter presenter = new LoginPresenter(new LoginView(), eventBus);
				presenter.bind();
				popup.setModal(true);
				popup.setGlassEnabled(true);
				popup.clear();
				popup.add(presenter.getDisplay().asWidget());
				popup.center();
			} else if (token.equals("register")) {
				RegisterPresenter presenter = new RegisterPresenter(new RegisterView(), eventBus);
				presenter.bind();
				getLayout().clear();
				getLayout().add(presenter.getDisplay().asWidget());
			} else {
				History.newItem("home");
			}
		}
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
	private static Map<String, String> getHistoryTokenParameters(String historyToken) {
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
}
