package org.accesointeligente.client;

import org.accesointeligente.client.presenters.LoginPresenter;
import org.accesointeligente.client.presenters.RegisterPresenter;
import org.accesointeligente.client.views.LoginView;
import org.accesointeligente.client.views.RegisterView;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;

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

//		eventBus.addHandler(LoginSuccessfulEvent.TYPE,	new LoginSuccessfulEventHandler () {
//			@Override
//			public void loginSuccessful(LoginSuccessfulEvent event) {
//				History.newItem("main");
//			}
//		});
//
//		eventBus.addHandler(LoginRequiredEvent.TYPE, new LoginRequiredEventHandler() {
//			@Override
//			public void loginRequired(LoginRequiredEvent event) {
//				History.newItem("login");
//			}
//		});

		switchSection(History.getToken());
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		switchSection(token);
	}

	public void switchSection(String token) {
		// TODO: implement ClientSessionUtil
		if (token.equals("login")) {
			LoginPresenter presenter = new LoginPresenter(new LoginView(), eventBus);
			presenter.bind();
			layout.clear();
			layout.add(presenter.getDisplay().asWidget());
		} else if (token.equals("register")) {
			RegisterPresenter presenter = new RegisterPresenter(new RegisterView(), eventBus);
			presenter.bind();
			layout.clear();
			layout.add(presenter.getDisplay().asWidget());
		} else {
			History.newItem("login");
		}

	}

	public FlowPanel getLayout() {
		return layout;
	}
}
