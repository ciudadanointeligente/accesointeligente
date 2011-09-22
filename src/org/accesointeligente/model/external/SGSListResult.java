package org.accesointeligente.model.external;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SGSListResult {
	@SerializedName("sEcho") private String echo;
	@SerializedName("iTotalRecords") private Integer totalRecords;
	@SerializedName("iTotalDisplayRecords") private Integer totalDisplayRecords;
	@SerializedName("aaData") private SGSRequest[] sgsRequests;

	public String getEcho() {
		return echo;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getTotalDisplayRecords() {
		return totalDisplayRecords;
	}

	public void setTotalDisplayRecords(Integer totalDisplayRecords) {
		this.totalDisplayRecords = totalDisplayRecords;
	}

	public SGSRequest[] getSgsRequests() {
		return sgsRequests;
	}

	public void setSgsRequests(SGSRequest[] sgsRequests) {
		this.sgsRequests = sgsRequests;
	}

	public static class SGSRequest {
		private String identifier;
		private Date creationDate;
		private Date responseDate;
		private String code;
		private String responseTimeComment;
		private String status;
		private String detailUrl;

		public String getIdentifier() {
			return identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public Date getResponseDate() {
			return responseDate;
		}

		public void setResponseDate(Date responseDate) {
			this.responseDate = responseDate;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getResponseTimeComment() {
			return responseTimeComment;
		}

		public void setResponseTimeComment(String responseTimeComment) {
			this.responseTimeComment = responseTimeComment;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDetailUrl() {
			return detailUrl;
		}

		public void setDetailUrl(String detailUrl) {
			this.detailUrl = detailUrl;
		}
	}
}
