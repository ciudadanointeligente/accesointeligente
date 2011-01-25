package org.accesointeligente.server.robots.sgs;

import org.accesointeligente.server.robots.SGS;

public class ServicioDeSaludAtacama extends SGS {

	public ServicioDeSaludAtacama() {
		super();
		setIdEntidad("211");
		setBaseUrl("http://200.54.170.197/sgs/index.php?accion=Home");
	}
}

