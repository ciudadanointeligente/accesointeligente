package cl.votainteligente.accesointeligente.server;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.GwtConfigurationHelper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;
	private static PersistentBeanManager persistentBeanManager = null;

	public static Session getSession() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable ex) {
				System.err.println("Couldn't get Hibernate session: " + ex.getMessage());
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory.getCurrentSession();
	}

	public static PersistentBeanManager getPersistentBeanManager() {
		if (sessionFactory == null) {
			getSession();
		}

		if (persistentBeanManager == null) {
			persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(new net.sf.gilead.core.hibernate.HibernateUtil(sessionFactory));
		}

		return persistentBeanManager;
	}
}
