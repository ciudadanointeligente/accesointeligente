/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2012 Fundación Ciudadano Inteligente
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
package org.accesointeligente.server;

import org.accesointeligente.server.robots.RequestNotifier;

import org.apache.log4j.Logger;

import java.util.TimerTask;

public class RequestNotificationTask extends TimerTask {
	private static final Logger logger = Logger.getLogger(RequestNotificationTask.class);

	@Override
	public void run() {
		new Thread() {
			@Override
			public void run() {
				logger.info("Begin");

				try {
					RequestNotifier requestNotifier = new RequestNotifier();
					requestNotifier.notifyExpiredRequests();
				} catch (Throwable t) {
					logger.error("RequestNotifier failed", t);
				}

				logger.info("Finish");
			}
		}.start();
	}
}
