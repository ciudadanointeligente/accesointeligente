package org.accesointeligente.shared;

import com.google.gwt.event.shared.GwtEvent;

public class NotificationEvent extends GwtEvent<NotificationEventHandler> {

	private NotificationEventParams params;
	public static final Type<NotificationEventHandler> TYPE = new Type<NotificationEventHandler>();

	public NotificationEvent(NotificationEventParams params) {
		this.params = params;
	}

	public NotificationEventParams getParams() {
		return params;
	}

	public void setParams(NotificationEventParams params) {
		this.params = params;
	}

	@Override
	public Type<NotificationEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NotificationEventHandler handler) {
		handler.onNotification(this);
	}
}
