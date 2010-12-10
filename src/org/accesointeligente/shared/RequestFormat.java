package org.accesointeligente.shared;

public enum RequestFormat {
	PAPER("Papel"),
	DIGITAL("Digital"),
	ANY("Cualquiera");

	private String name;

	RequestFormat(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
