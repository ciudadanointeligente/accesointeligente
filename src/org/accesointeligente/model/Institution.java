package org.accesointeligente.model;

import org.accesointeligente.shared.InstitutionClass;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Institution extends LightEntity {

	private Integer id;
	private String name;
	private InstitutionClass institutionClass;

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
}
