package org.accesointeligente.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface LoginSuccessfulEventHandler extends EventHandler {
	void loginSuccessful (LoginSuccessfulEvent event);
}
