package org.accesointeligente.server.services;

import org.accesointeligente.client.services.InstitutionService;
import org.accesointeligente.model.Institution;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.shared.ServiceException;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class InstitutionServiceImpl extends PersistentRemoteService implements InstitutionService {
	private static final long serialVersionUID = 7649117458892114784L;
	private PersistentBeanManager persistentBeanManager;

	public InstitutionServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Institution> getInstitutions() throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			Criteria criteria = hibernate.createCriteria(Institution.class);
			criteria.addOrder(Order.asc("id"));
			List<Institution> institutions = (List<Institution>) persistentBeanManager.clone(criteria.list());
			hibernate.getTransaction().commit();
			return institutions;
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
