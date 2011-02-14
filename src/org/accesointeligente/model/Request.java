package org.accesointeligente.model;

import org.accesointeligente.shared.*;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;
import java.util.Set;

public class Request extends LightEntity {

	private Integer id;
	private User user;
	private Institution institution;
	private String information;
	private String context;
	private String title;
	private Set<RequestCategory> categories;
	private Boolean anotherInstitution;
	private RequestStatus status;
	private Date creationDate;
	private Date confirmationDate;
	private Date processDate;
	private Set<Response> responses;
	private String remoteIdentifier;
	private Set<UserFavoriteRequest> favorites;
	private Set<RequestComment> comments;
	private Double qualification;

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

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Set<Response> getResponses() {
		return responses;
	}

	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}

	public String getRemoteIdentifier() {
		return remoteIdentifier;
	}

	public void setRemoteIdentifier(String remoteIdentifier) {
		this.remoteIdentifier = remoteIdentifier;
	}

	public Set<UserFavoriteRequest> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<UserFavoriteRequest> favorites) {
		this.favorites = favorites;
	}

	public Set<RequestComment> getComments() {
		return comments;
	}

	public void setComments(Set<RequestComment> comments) {
		this.comments = comments;
	}

	public Double getQualification() {
		return qualification;
	}

	public void setQualification(Double qualification) {
		this.qualification = qualification;
	}
}
