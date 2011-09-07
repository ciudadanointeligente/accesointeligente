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
package org.accesointeligente.server.services;


import org.accesointeligente.client.services.InstitutionTypeService;
import org.accesointeligente.model.InstitutionType;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.shared.ServiceException;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class InstitutionTypeServiceImpl extends PersistentRemoteService implements InstitutionTypeService {
	private PersistentBeanManager persistentBeanManager;

	public InstitutionTypeServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InstitutionType> getInstitutionTypes() throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			Criteria criteria = hibernate.createCriteria(InstitutionType.class);
			criteria.addOrder(Order.asc("id"));
			List<InstitutionType> institutionTypes = (List<InstitutionType>) persistentBeanManager.clone(criteria.list());
			hibernate.getTransaction().commit();
			return institutionTypes;
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
