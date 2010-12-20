package org.accesointeligente.model;

import org.accesointeligente.shared.*;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Set;

public class Request extends LightEntity {
	private static final long serialVersionUID = 3099494353260207511L;

	private Integer id;
	private User user;
	private Institution institution;
	private String information;
	private String context;
	private String title;
	private Set<RequestCategory> categories;
	private Boolean anotherInstitution;
	private RequestFormat format;
	private RequestMethod method;
	private RequestStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<RequestCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<RequestCategory> categories) {
		this.categories = categories;
	}

	public Boolean getAnotherInstitution() {
		return anotherInstitution;
	}

	public void setAnotherInstitution(Boolean anotherInstitution) {
		this.anotherInstitution = anotherInstitution;
	}

	public RequestFormat getFormat() {
		return format;
	}

	public void setFormat(RequestFormat format) {
		this.format = format;
	}

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
}
