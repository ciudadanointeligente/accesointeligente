package org.accesointeligente.server.services;


import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.accesointeligente.client.services.AgeService;
import org.accesointeligente.model.Age;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.shared.ServiceException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class AgeServiceImpl extends PersistentRemoteService implements AgeService {
	private static final long serialVersionUID = -6095320806491962761L;
	private PersistentBeanManager persistentBeanManager;

	public AgeServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Age> getAges() throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			Criteria criteria = hibernate.createCriteria(Age.class);
			criteria.addOrder(Order.asc("id"));
			List<Age> ages = (List<Age>) persistentBeanManager.clone(criteria.list());
			hibernate.getTransaction().commit();
			return ages;
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
