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
	private Boolean statusDerived;

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

	public Boolean getStatusDerived() {
		return statusDerived;
	}

	public void setStatusDerived(Boolean statusDerived) {
		this.statusDerived = statusDerived;
	}
}
