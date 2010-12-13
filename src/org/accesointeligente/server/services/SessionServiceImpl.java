package org.accesointeligente.server.services;

import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.services.SessionService;
import org.accesointeligente.client.services.SessionServiceException;
import org.accesointeligente.server.HibernateUtil;
import org.accesointeligente.server.SessionUtil;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;

public class SessionServiceImpl extends PersistentRemoteService implements SessionService {
	private static final long serialVersionUID = 2965134634476060984L;
	private PersistentBeanManager persistentBeanManager;

	public SessionServiceImpl () {
		persistentBeanManager = HibernateUtil.getPersistentBeanManager ();
		setBeanManager(persistentBeanManager);
	}

	@Override
	public SessionData getSessionData () throws SessionServiceException {
		return (SessionData) persistentBeanManager.clone (SessionUtil.getSessionData ());
	}
}
