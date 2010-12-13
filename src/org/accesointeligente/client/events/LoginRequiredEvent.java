package org.accesointeligente.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class LoginRequiredEvent extends GwtEvent<LoginRequiredEventHandler> {
	public static Type<LoginRequiredEventHandler> TYPE = new Type<LoginRequiredEventHandler> ();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginRequiredEventHandler> getAssociatedType () {
		return TYPE;
	}

	@Override
	protected void dispatch (LoginRequiredEventHandler handler) {
		handler.loginRequired (this);
	}
}
