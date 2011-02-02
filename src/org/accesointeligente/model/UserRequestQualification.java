package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class UserRequestQualification extends LightEntity {

	User user;
	Request request;
	Integer qualification;

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

	public Integer getQualification() {
		return qualification;
	}

	public void setQualification(Integer qualification) {
		this.qualification = qualification;
	}
}
