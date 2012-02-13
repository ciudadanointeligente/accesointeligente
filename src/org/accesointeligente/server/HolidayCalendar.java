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

import org.accesointeligente.model.Holiday;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class HolidayCalendar {
	private static final Logger logger = Logger.getLogger(HolidayCalendar.class);

	public static boolean isOnTime(Date startDate, Integer rank) {
		Session hibernate = null;
		Date endDate = new Date();
		int workDays = 0;

		try {
			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Holiday.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.between("date", startDate, endDate));
			List<Holiday> holidays = (List<Holiday>) criteria.list();
			hibernate.getTransaction().commit();

			List <Calendar> holidayCal = new ArrayList<Calendar>();
			for (Holiday holiday: holidays) {
				Calendar cal = new GregorianCalendar();
				cal.setTime(holiday.getDate());
				holidayCal.add(cal);
			}

			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			startCal.setTime(startDate);
			endCal.setTime(endDate);

			while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
				if ((startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) && !holidayCal.contains(startCal)) {
					workDays++;
				}
				startCal.add(Calendar.DAY_OF_MONTH, 1);
			}

		} catch (Exception ex) {
			if (hibernate != null && hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}

			logger.error(ex.getMessage(), ex);
		}
		return (workDays < rank);
	}
}