package org.accesointeligente.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class LoginSuccessfulEvent extends GwtEvent<LoginSuccessfulEventHandler> {
	public static Type<LoginSuccessfulEventHandler> TYPE = new Type<LoginSuccessfulEventHandler> ();

	@Override
	public Type<LoginSuccessfulEventHandler> getAssociatedType () {
		return TYPE;
	}

	@Override
	public void dispatch (LoginSuccessfulEventHandler handler) {
		handler.loginSuccessful (this);
	}
}
