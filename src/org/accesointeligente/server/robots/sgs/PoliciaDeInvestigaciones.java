package org.accesointeligente.server.robots.sgs;

import org.accesointeligente.server.robots.SGS;

public class PoliciaDeInvestigaciones extends SGS {

	public PoliciaDeInvestigaciones() {
		super();
		setIdEntidad("105");
		setBaseUrl("http://gobiernotransparente.investigaciones.cl:8080/transparencia/index.php");
	}
}

