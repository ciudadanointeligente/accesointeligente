package cl.votainteligente.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Activity extends LightEntity {
	private static final long serialVersionUID = -7949584853990558020L;

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
}
