package org.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class RequestCategory extends LightEntity {
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
}
