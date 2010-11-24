package cl.votainteligente.accesointeligente.model;

import net.sf.gilead.pojo.gwt.LightEntity;

public class InstitutionType extends LightEntity {
	private static final long serialVersionUID = -6303588102545359961L;

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
