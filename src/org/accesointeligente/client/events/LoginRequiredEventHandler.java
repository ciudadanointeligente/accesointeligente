package org.accesointeligente.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface LoginRequiredEventHandler extends EventHandler {
	void loginRequired (LoginRequiredEvent event);
}
