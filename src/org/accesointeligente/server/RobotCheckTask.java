package org.accesointeligente.server;

import org.accesointeligente.model.Institution;
import org.accesointeligente.server.robots.Robot;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.util.List;
import java.util.TimerTask;

public class RobotCheckTask extends TimerTask {

	@Override
	public void run() {
		System.err.println("Iniciando RobotCheckTask");
		Session hibernate = HibernateUtil.getSession();

		try {
			hibernate.beginTransaction();
			Criteria criteria = hibernate.createCriteria(Institution.class);
			List<Institution> institutions = criteria.list();
			hibernate.getTransaction().commit();

			for (Institution institution : institutions) {
				Robot robot = RobotContext.getRobot(institution.getInstitutionClass());

				if (robot != null) {
					try {
						robot.login();
						institution.setCanLogin(true);
					} catch (Exception ex) {
						institution.setCanLogin(false);
					}

					if (institution.getCanLogin()) {
						try {
							institution.setCanMakeRequest(robot.checkInstitutionId());
						} catch (Exception ex) {
							institution.setCanMakeRequest(false);
						}
					} else {
						institution.setCanMakeRequest(false);
					}
				} else {
					institution.setCanLogin(false);
					institution.setCanMakeRequest(false);
				}

				institution.setEnabled(institution.getCanLogin() && institution.getCanMakeRequest());
				hibernate = HibernateUtil.getSession();
				hibernate.beginTransaction();
				hibernate.update(institution);
				hibernate.getTransaction().commit();
			}
		} catch (Exception ex) {
			if (hibernate.isOpen() && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
				ex.printStackTrace(System.err);
			}
		}
	}
}
