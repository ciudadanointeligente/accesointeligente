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

import org.accesointeligente.shared.Country;
import org.accesointeligente.shared.Gender;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.util.Date;
import java.util.Set;

public class User extends LightEntity {

	private Long id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Age age;
	private Gender gender;
	private Set<Activity> activities;
	private Country country;
	private Region region;
	private Boolean naturalPerson;
	private InstitutionType institutionType;
	private Date registerDate;
	private Date lastLoginDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public Age getAge() {
		return age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender sex) {
		this.gender = sex;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	public Boolean getNaturalPerson() {
		return naturalPerson;
	}

	public void setNaturalPerson(Boolean naturalPerson) {
		this.naturalPerson = naturalPerson;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (!(other instanceof User)) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		return getId().equals(((User) other).getId());
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
