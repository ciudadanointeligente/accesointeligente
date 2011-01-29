package org.accesointeligente.server.robots.sgs;

import org.accesointeligente.server.robots.SGS;

public class ServicioNacionalDeAduanas extends SGS {

	public ServicioNacionalDeAduanas() {
		super();
		setIdEntidad("122");
		setBaseUrl("http://www.aduana.cl/sgs/index.php");
	}
}

