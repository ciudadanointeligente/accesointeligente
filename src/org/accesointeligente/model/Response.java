package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;

public class Response extends LightEntity {
	private static final long serialVersionUID = -2175405223931548188L;

	private Integer id;
	private Request request;
	private Date date;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
