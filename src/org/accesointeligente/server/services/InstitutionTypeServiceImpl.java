package org.accesointeligente.server.services;


import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.accesointeligente.client.services.InstitutionTypeService;
import org.accesointeligente.model.InstitutionType;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.shared.ServiceException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class InstitutionTypeServiceImpl extends PersistentRemoteService implements InstitutionTypeService {
	private static final long serialVersionUID = -8674556576040520021L;
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
