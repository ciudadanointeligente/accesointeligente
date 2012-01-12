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

import org.accesointeligente.shared.ResponseType;
import org.accesointeligente.shared.UserSatisfaction;

import java.util.Date;
import java.util.Set;

public class Response extends LightEntity implements Comparable<Response> {

	private Integer id;
	private Request request;
	private String sender;
	private String subject;
	private String information;
	private Date date;
	private Set<Attachment> attachments;
	private UserResponse userResponse;
	private Boolean notified = false;
	private Boolean notifiedSatisfaction = false;
	private UserSatisfaction userSatisfaction;
	private String responseKey;
	private ResponseType type;

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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public UserResponse getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(UserResponse userResponse) {
		this.userResponse = userResponse;
	}

	public Boolean getNotified() {
		return notified;
	}

	public void setNotified(Boolean notified) {
		this.notified = notified;
	}

	public Boolean getNotifiedSatisfaction() {
		return notifiedSatisfaction;
	}

	public void setNotifiedSatisfaction(Boolean notifiedSatisfaction) {
		this.notifiedSatisfaction = notifiedSatisfaction;
	}

	public UserSatisfaction getUserSatisfaction() {
		return userSatisfaction;
	}

	public void setUserSatisfaction(UserSatisfaction userSatisfaction) {
		this.userSatisfaction = userSatisfaction;
	}

	public String getResponseKey() {
		return responseKey;
	}

	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}

	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType responseType) {
		this.type = responseType;
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
