package cl.votainteligente.accesointeligente.server.services;

import cl.votainteligente.accesointeligente.client.services.RegionService;
import cl.votainteligente.accesointeligente.model.Region;
import cl.votainteligente.accesointeligente.server.HibernateUtil;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

public class RegionServiceImpl extends PersistentRemoteService implements RegionService {
	private static final long serialVersionUID = -7693962949051764617L;
	private PersistentBeanManager persistentBeanManager;

	public RegionServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> getRegions() throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			Criteria criteria = hibernate.createCriteria(Region.class);
			criteria.addOrder(Order.asc("number"));
			List<Region> regions = (List<Region>) persistentBeanManager.clone(criteria.list());
			hibernate.getTransaction().commit();
			return regions;
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
