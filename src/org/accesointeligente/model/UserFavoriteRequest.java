package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class UserFavoriteRequest extends LightEntity {
	private static final long serialVersionUID = 6404702140021489068L;

	User user;
	Request request;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
