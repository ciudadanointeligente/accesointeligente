package org.accesointeligente.shared;

public enum RequestMethod {
	EMAIL("Email"),
	MAIL("Envío por correo"),
	OFFICE("Retiro en la oficina");

	private String name;

	RequestMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
