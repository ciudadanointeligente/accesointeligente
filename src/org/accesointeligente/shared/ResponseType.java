package org.accesointeligente.shared;

public enum ResponseType {
	EXTENSION("Prorroga"),
	DERIVATION("Derivación"),
	DENIAL("Denegación"),
	INFORMATION("Informativa"),
	INCOMPLETE("Incompleta");

	private String name;

	private ResponseType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
