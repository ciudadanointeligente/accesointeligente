package org.accesointeligente.server.services;

import java.util.ArrayList;
import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.accesointeligente.client.services.UserService;
import org.accesointeligente.model.User;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.shared.LoginException;
import org.accesointeligente.shared.RegisterException;
import org.accesointeligente.shared.ServiceException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class UserServiceImpl extends PersistentRemoteService implements UserService {
	private static final long serialVersionUID = -389660005156048126L;
	private PersistentBeanManager persistentBeanManager;

	public UserServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User login(String email, String password) throws LoginException, ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();
		List<User> result = new ArrayList<User>();

		try {
			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("password", password));
			result = (List<User>) criteria.list();
			hibernate.getTransaction().commit();
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}

		if (result.size() != 1) {
			throw new LoginException();
		} else {
			return (User) persistentBeanManager.clone(result.get(0));
		}
	}

	@Override
	public User register(User user) throws RegisterException, ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", user.getEmail()));

			if (criteria.list().size() == 1) {
				throw new RegisterException();
			}

			hibernate.save(user);
			hibernate.getTransaction().commit();
			return (User) persistentBeanManager.clone(user);
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();

			if (ex instanceof RegisterException) {
				throw (RegisterException) ex;
			} else {
				throw new ServiceException();
			}
		}
	}
}
