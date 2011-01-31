package org.accesointeligente.server;

import org.accesointeligente.model.Request;
import org.accesointeligente.server.robots.Robot;
import org.accesointeligente.shared.RequestStatus;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.TimerTask;

public class RequestUpdateTask extends TimerTask {
	@Override
	public void run() {
		System.err.println("Iniciando RequestUpdateTask");
		Session hibernate = HibernateUtil.getSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Request.class);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.setFetchMode("institution", FetchMode.JOIN);
			criteria.add(Restrictions.eq("status", RequestStatus.PENDING));
			List<Request> newRequests = criteria.list();
			hibernate.getTransaction().commit();

			for (Request request : newRequests) {
				System.err.println("[INFO] RequestUpdateTask: requestId = " + request.getId());

				try {
					Robot robot = Robot.getRobot(request.getInstitution().getInstitutionClass());

					if (robot != null) {
						RequestStatus status = robot.checkRequestStatus(request);

						if (status != null) {
							request.setStatus(status);
						}

						hibernate = HibernateUtil.getSession();
						hibernate.beginTransaction();
						hibernate.update(request);
						hibernate.getTransaction().commit();
					}
				} catch (Exception ex) {
					System.err.println("[ERROR] RequestUpdateTask: requestId = " + request.getId());
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
				ex.printStackTrace(System.err);
			}
		}
	}
}
