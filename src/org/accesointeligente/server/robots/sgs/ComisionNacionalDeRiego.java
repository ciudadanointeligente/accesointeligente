package org.accesointeligente.server.robots.sgs;

import org.accesointeligente.server.robots.SGS;

public class ComisionNacionalDeRiego extends SGS {

	public ComisionNacionalDeRiego() {
		super();
		setIdEntidad("250");
		setBaseUrl("http://sgs.cnr.gob.cl/sgs/index.php");
	}
}

