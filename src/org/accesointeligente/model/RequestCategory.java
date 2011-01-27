package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

import java.io.Serializable;

public class RequestCategory extends LightEntity implements Serializable {
	private static final long serialVersionUID = 3442939892038068139L;

	private Integer id;
	private String name;

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

		if (!(object instanceof RequestCategory)) {
			return false;
		}

		return id.equals(((RequestCategory) object).getId());
	}
}
