/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.server.solr;

import org.accesointeligente.server.ApplicationProperties;

import org.apache.log4j.Logger;

import java.util.TimerTask;

public class SolrIndexTask extends TimerTask {
	private static final Logger logger = Logger.getLogger(SolrIndexTask.class);

	@Override
	public void run() {
		new Thread() {
			@Override
			public void run() {
				logger.info("Begin");

				try {
					if (ApplicationProperties.getProperty("solr.server.status") != null && ApplicationProperties.getProperty("solr.server.status").equals(SolrStatus.AVAILABLE.toString())) {
						SolrClient.reindex();
						logger.info("index created");
					}
				} catch (Throwable t) {
					logger.error("Solr indexation failed", t);
				}

				logger.info("Finish");
			}
		}.start();
	}
}
