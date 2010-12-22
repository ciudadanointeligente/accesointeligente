package org.accesointeligente.client;

import org.accesointeligente.client.events.*;
import org.accesointeligente.client.presenters.*;
import org.accesointeligente.client.views.*;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

import java.util.HashMap;
import java.util.Map;

public class AppController implements ValueChangeHandler<String> {
	private FlowPanel layout;
	private EventBus eventBus;

	public AppController(EventBus eventBus) {
		this.eventBus = eventBus;
		layout = new FlowPanel();
		setup();
	}

	private void setup() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(LoginSuccessfulEvent.TYPE,	new LoginSuccessfulEventHandler () {
			@Override
			public void loginSuccessful(LoginSuccessfulEvent event) {
				// TODO: implement MainPresenter/View
				History.newItem("request");
			}
		});

		eventBus.addHandler(LoginRequiredEvent.TYPE, new LoginRequiredEventHandler() {
			@Override
			public void loginRequired(LoginRequiredEvent event) {
				History.newItem("login");
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
		if (ClientSessionUtil.checkSession ()) {
			if (token.equals("request")) {
				RequestPresenter presenter = new RequestPresenter(new RequestView(), eventBus);
				presenter.bind();
				layout.clear();
				layout.add(presenter.getDisplay().asWidget());
			} else if (token.equals ("logout")) {
				ClientSessionUtil.destroySession ();
				History.newItem ("login");
			} else if (token.startsWith("status")) {
				Map<String, String> parameters = getHistoryTokenParameters(token);
				try {
					Integer requestId = Integer.parseInt(parameters.get("requestId"));
					RequestStatusPresenter presenter = new RequestStatusPresenter(new RequestStatusView(), eventBus);
					presenter.bind();
					presenter.showRequest(requestId);
					layout.clear();
					layout.add(presenter.getDisplay().asWidget());
				} catch (Exception e) {
					Window.alert("Id incorrecta: No se puede cargar la solicitud");
				}
			} else {
				// TODO: implement MainPresenter/View
				History.newItem ("request");
			}
		} else {
			if (token.equals ("login")) {
				LoginPresenter presenter = new LoginPresenter (new LoginView (), eventBus);
				presenter.bind ();
				layout.clear ();
				layout.add (presenter.getDisplay ().asWidget ());
			} else if (token.equals("register")) {
				RegisterPresenter presenter = new RegisterPresenter(new RegisterView(), eventBus);
				presenter.bind();
				layout.clear();
				layout.add(presenter.getDisplay().asWidget());
			} else {
				History.newItem ("login");
			}
		}
	}

	public FlowPanel getLayout() {
		return layout;
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
		//skip if there is no question mark
		if (!historyToken.contains("?")) {
		        return null;
		}
		// ? position
		int questionMarkIndex = historyToken.indexOf("?") + 1;

		//get the sub string of parameters var=1&var2=2&var3=3...
		String[] arStr = historyToken.substring(questionMarkIndex, historyToken.length()).split("&");
		Map<String, String> params = new HashMap<String, String>();

		for (int i = 0; i < arStr.length; i++) {
		        String[] substr = arStr[i].split("=");
		        params.put(substr[0], substr[1]);
		}

		return params;
	}
}
