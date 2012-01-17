/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import org.accesointeligente.shared.RequestExpireType;
import org.accesointeligente.shared.RequestStatus;
import org.accesointeligente.shared.UserSatisfaction;

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
	private RequestStatus status;
	private Date creationDate;
	private Date confirmationDate;
	private Date processDate;
	private Date responseDate;
	private Set<Response> responses;
	private String remoteIdentifier;
	private Set<UserFavoriteRequest> favorites;
	private Set<RequestComment> comments;
	private Double qualification;
	private UserSatisfaction userSatisfaction;
	private RequestExpireType expired;

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

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
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

	public UserSatisfaction getUserSatisfaction() {
		return userSatisfaction;
	}

	public void setUserSatisfaction(UserSatisfaction userSatisfaction) {
		this.userSatisfaction = userSatisfaction;
	}

	public RequestExpireType getExpired() {
		return expired;
	}

	public void setExpired(RequestExpireType expired) {
		this.expired = expired;
	}
}
