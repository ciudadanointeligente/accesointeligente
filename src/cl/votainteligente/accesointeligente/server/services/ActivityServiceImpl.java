package cl.votainteligente.accesointeligente.server.services;

import cl.votainteligente.accesointeligente.client.services.ActivityService;
import cl.votainteligente.accesointeligente.model.Activity;
import cl.votainteligente.accesointeligente.server.HibernateUtil;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

public class ActivityServiceImpl extends PersistentRemoteService implements ActivityService {
	private static final long serialVersionUID = -7458001808166552806L;
	private PersistentBeanManager persistentBeanManager;

	public ActivityServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivities(Boolean isPerson) throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			Criteria criteria = hibernate.createCriteria(Activity.class);
			criteria.add(Restrictions.eq("person", isPerson));
			criteria.addOrder(Order.asc("id"));
			List<Activity> activities = (List<Activity>) persistentBeanManager.clone(criteria.list());
			hibernate.getTransaction().commit();
			return activities;
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
