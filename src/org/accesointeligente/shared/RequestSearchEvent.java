package org.accesointeligente.shared;

import com.google.gwt.event.shared.GwtEvent;

public class RequestSearchEvent extends GwtEvent<RequestSearchEventHandler> {

	private RequestSearchParams params;
	public static final Type<RequestSearchEventHandler> TYPE = new Type<RequestSearchEventHandler>();

	public RequestSearchEvent(RequestSearchParams params) {
		this.params = params;
	}

	public RequestSearchParams getParams() {
		return params;
	}

	public void setParams(RequestSearchParams params) {
		this.params = params;
	}

	@Override
	public Type<RequestSearchEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestSearchEventHandler handler) {
		handler.onSearch(this);
	}
}
