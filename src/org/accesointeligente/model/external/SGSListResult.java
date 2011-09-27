package org.accesointeligente.model.external;

import com.google.gson.annotations.SerializedName;

public class SGSListResult {
	@SerializedName("sEcho") private String echo;
	@SerializedName("iTotalRecords") private Integer totalRecords;
	@SerializedName("iTotalDisplayRecords") private Integer totalDisplayRecords;
	@SerializedName("aaData") private String[][] sgsRequests;

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

	public String[][] getSgsRequests() {
		return sgsRequests;
	}

	public void setSgsRequests(String[][] sgsRequests) {
		this.sgsRequests = sgsRequests;
	}
}

