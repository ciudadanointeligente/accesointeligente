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

