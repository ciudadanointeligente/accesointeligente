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
package org.accesointeligente.shared;

import org.accesointeligente.model.Institution;

import java.io.Serializable;
import java.util.Date;

public class RequestSearchParams implements Serializable {

	private String keyWord;
	private Institution institution;
	private Date minDate;
	private Date maxDate;
	private Boolean statusPending;
	private Boolean statusClosed;
	private Boolean statusExpired;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Boolean getStatusPending() {
		return statusPending;
	}

	public void setStatusPending(Boolean statusPending) {
		this.statusPending = statusPending;
	}

	public Boolean getStatusClosed() {
		return statusClosed;
	}

	public void setStatusClosed(Boolean statusClosed) {
		this.statusClosed = statusClosed;
	}

	public Boolean getStatusExpired() {
		return statusExpired;
	}

	public void setStatusExpired(Boolean statusExpired) {
		this.statusExpired = statusExpired;
	}
}
