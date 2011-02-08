package org.accesointeligente.server.services;

import org.accesointeligente.client.services.UserService;
import org.accesointeligente.model.User;
import org.accesointeligente.server.*;
import org.accesointeligente.shared.*;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class UserServiceImpl extends PersistentRemoteService implements UserService {
	private static final long serialVersionUID = -389660005156048126L;
	private PersistentBeanManager persistentBeanManager;

	public UserServiceImpl() {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager();
		setBeanManager(persistentBeanManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void login(String email, String password) throws LoginException, ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();
		List<User> result = new ArrayList<User>();

		try {
			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.eq("email", email));
			criteria.setFetchMode("activities", FetchMode.JOIN);
			result = (List<User>) criteria.list();
			hibernate.getTransaction().commit();
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}

		if (result.size() != 1) {
			throw new LoginException();
		} else {
			User user = result.get(0);

			if (!BCrypt.checkpw(password, user.getPassword())) {
				throw new LoginException();
			} else {
				SessionUtil.setSession (getThreadLocalRequest ().getSession ());
				SessionUtil.setAttribute ("sessionId", UUID.randomUUID ().toString ());
				SessionUtil.setAttribute ("user", (User) persistentBeanManager.clone(user));
			}
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

			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

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

	@Override
	public Boolean checkPass(String email, String password) throws LoginException, ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();
		User user = null;

		try {
			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			user = (User) criteria.uniqueResult();
			hibernate.getTransaction().commit();
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}

		if (user == null) {
			throw new LoginException();
		} else {
			if (!BCrypt.checkpw(password, user.getPassword())) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public void updateUser(User user) throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();

		try {
			user = (User) persistentBeanManager.merge(user);

			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.add(Restrictions.eq("id", user.getId()));
			User actualUser = (User) criteria.uniqueResult();
			hibernate.getTransaction().commit();

			if (user.getPassword() != null && !actualUser.getPassword().equals(user.getPassword())) {
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			}

			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			hibernate.update(user);
			hibernate.getTransaction().commit();
			SessionUtil.setAttribute ("user", (User) persistentBeanManager.clone(user));
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}

	@Override
	public Boolean checkEmailExistence(String email) throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();
		User user = null;

		try {
			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			user = (User) criteria.uniqueResult();
			hibernate.getTransaction().commit();
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}

		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void resetPassword(String email) throws ServiceException {
		Session hibernate = HibernateUtil.getSession();
		hibernate.beginTransaction();
		String newPassword = null;

		try {
			Criteria criteria = hibernate.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			User user = (User) criteria.uniqueResult();
			hibernate.getTransaction().commit();

			newPassword = RandomPassword.getRandomString(8);
			user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));

			hibernate = HibernateUtil.getSession();
			hibernate.beginTransaction();
			hibernate.update(user);
			hibernate.getTransaction().commit();

			Emailer emailer = new Emailer(user.getEmail(), "Accesointeligente le comunica: Se ha reestablecido su contraseña",
					(user.getGender().equals(Gender.FEMALE))? "Sra." : "Sr." + user.getFirstName() + ", <br />" +
					"<p>" + "ha solicitado a través de nuestro sistema de recuperación de contraseña la creación de una nueva<br />" +
					"La cual le damos a continuación: " + newPassword + "</p>");
			emailer.connectAndSend();
		} catch (Throwable ex) {
			hibernate.getTransaction().rollback();
			throw new ServiceException();
		}
	}
}
