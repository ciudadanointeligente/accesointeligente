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
package org.accesointeligente.server;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Timer;

public class Scheduler implements Runnable {
	private static final Logger logger = Logger.getLogger(Scheduler.class);
	private static Scheduler instance = new Scheduler();
	private Timer timer;

	private Scheduler() {
		timer = new Timer();
	}

	public static Scheduler getInstance() {
		return instance;
	}

	@Override
	public void run() {
		logger.info("Running");
		timer.schedule(new RequestCreationTask(), 0, 3600000);
		timer.schedule(new RequestUpdateTask(), 0, 3600000);
		timer.schedule(new ResponseCheckerTask(), 0, 7200000);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		timer.scheduleAtFixedRate(new RobotCheckTask(), cal.getTime(), 43200000);
	}
}
