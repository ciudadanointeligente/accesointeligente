package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Age extends LightEntity {

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
}
