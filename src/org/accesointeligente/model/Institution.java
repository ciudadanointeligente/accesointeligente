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

import org.accesointeligente.shared.InstitutionClass;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Institution extends LightEntity {

	private Integer id;
	private String name;
	private InstitutionClass institutionClass;
	private Boolean enabled;
	private Boolean canLogin;
	private Boolean canMakeRequest;
	private Boolean masterEnabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InstitutionClass getInstitutionClass() {
		return institutionClass;
	}

	public void setInstitutionClass(InstitutionClass institutionClass) {
		this.institutionClass = institutionClass;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getCanLogin() {
		return canLogin;
	}

	public void setCanLogin(Boolean canLogin) {
		this.canLogin = canLogin;
	}

	public Boolean getCanMakeRequest() {
		return canMakeRequest;
	}

	public void setCanMakeRequest(Boolean canMakeRequest) {
		this.canMakeRequest = canMakeRequest;
	}

	public Boolean getMasterEnabled() {
		return masterEnabled;
	}

	public void setMasterEnabled(Boolean disabled) {
		this.masterEnabled = disabled;
	}
}
