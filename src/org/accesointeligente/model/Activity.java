package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Activity extends LightEntity {

	private Integer id;
	private String name;
	private Boolean person;

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

	public void setPerson(Boolean person) {
		this.person = person;
	}

	public Boolean isPerson() {
		return person;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}

		if (id == null) {
			return false;
		}

		if (!(object instanceof Activity)) {
			return false;
		}

		return id.equals(((Activity) object).getId());
	}
}
