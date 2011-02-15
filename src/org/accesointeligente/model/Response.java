package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;
import java.util.Set;

public class Response extends LightEntity implements Comparable<Response> {

	private Integer id;
	private Request request;
	private String information;
	private Date date;
	private Set<Attachment> attachments;

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

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public int compareTo(Response response) {
		if (response == null) {
			return -1;
		}

		Comparable<Integer> comparableThisResponse = this.getId();
		Comparable<Integer> comparableThatResponse = response.getId();

		if (comparableThisResponse == null) {
			return 1;
		} else if (comparableThatResponse == null) {
			return -1;
		} else {
			return comparableThisResponse.compareTo((Integer) comparableThatResponse);
		}
	}
}
