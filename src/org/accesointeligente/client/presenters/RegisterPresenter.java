/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.client.presenters;


import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.events.LoginSuccessfulEvent;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.model.*;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

public class RegisterPresenter extends WidgetPresenter<RegisterPresenter.Display> implements RegisterPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(RegisterPresenterIface presenter);
		void cleanPersonActivities();
		void cleanInstitutionActivities();
		void cleanInstitutionTypes();
		void cleanPersonAges();
		void cleanRegions();
		void addPersonActivity(Activity activity);
		void addInstitutionActivity(Activity activity);
		void addInstitutionType(InstitutionType institutionType);
		void addPersonAge(Age age);
		void addRegion(Region region);
		Boolean isPerson();
		String getPersonFirstName();
		String getPersonLastName();
		Gender getPersonGender();
		Activity getPersonActivity();
		Age getPersonAge();
		String getInstitutionName();
		InstitutionType getInstitutionType();
		Set<Activity> getInstitutionActivities();
		Country getCountry();
		Region getRegion();
		String getEmail();
		String getPassword();
		Boolean validateForm();
	}

	public RegisterPresenter(Display display, EventBus eventBus) {
		super(display, eventBus);
	}

	@Override
	protected void onBind() {
		display.setPresenter(this);
		getPersonActivities();
		getInstitutionActivities();
		getInstitutionTypes();
		getPersonAges();
		getRegions();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
	}

	@Override
	public void getPersonActivities() {
		display.cleanPersonActivities();

		RPC.getActivityService().getActivities(true, new AsyncCallback<List<Activity>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Activity> result) {
				for (Activity activity : result) {
					display.addPersonActivity(activity);
				}
			}
		});
	}

	@Override
	public void getInstitutionActivities() {
		display.cleanInstitutionActivities();

		RPC.getActivityService().getActivities(false, new AsyncCallback<List<Activity>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Activity> result) {
				for (Activity activity : result) {
					display.addInstitutionActivity(activity);
				}
			}
		});
	}

	@Override
	public void getInstitutionTypes() {
		display.cleanInstitutionTypes();

		RPC.getInstitutionTypeService().getInstitutionTypes(new AsyncCallback<List<InstitutionType>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo tipos de institución", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<InstitutionType> result) {
				for (InstitutionType institutionType : result) {
					display.addInstitutionType(institutionType);
				}
			}
		});

	}

	@Override
	public void getPersonAges() {
		display.cleanPersonAges();

		RPC.getAgeService().getAges(new AsyncCallback<List<Age>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo edades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Age> result) {
				for (Age age : result) {
					display.addPersonAge(age);
				}
			}
		});
	}

	@Override
	public void getRegions() {
		display.cleanRegions();

		RPC.getRegionService().getRegions(new AsyncCallback<List<Region>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo regiones", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Region> result) {
				for (Region region : result) {
					display.addRegion(region);
				}
			}
		});
	}

	@Override
	public void register() {
		if (display.validateForm()) {
			final User user = new User();

			if (display.isPerson()) {
				user.setFirstName(display.getPersonFirstName());
				user.setLastName(display.getPersonLastName());
				Set<Activity> activities = new HashSet<Activity>();
				activities.add(display.getPersonActivity());
				user.setGender(display.getPersonGender());
				user.setActivities(activities);
				user.setAge(display.getPersonAge());
				user.setNaturalPerson(true);
			} else {
				user.setFirstName(display.getInstitutionName());
				user.setActivities(display.getInstitutionActivities());
				user.setInstitutionType(display.getInstitutionType());
				user.setNaturalPerson(false);
			}

			user.setEmail(display.getEmail());
			user.setPassword(display.getPassword());
			user.setCountry(display.getCountry());
			user.setRegisterDate(new Date());

			if (display.getCountry().equals(Country.CHILE)) {
				user.setRegion(display.getRegion());
			}

			RPC.getUserService().register(user, new AsyncCallback<User>() {
				@Override
				public void onFailure(Throwable caught) {
					if (caught instanceof RegisterException) {
						showNotification("Ya existe un usuario registrado con ese email", NotificationEventType.ERROR);
					} else {
						showNotification("No se pudo registrar el nuevo usuario, intente nuevamente", NotificationEventType.ERROR);
					}
				}

				@Override
				public void onSuccess(User result) {
					showNotification("Registro exitoso!", NotificationEventType.SUCCESS);

					RPC.getUserService().login(result.getEmail(), user.getPassword(), new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							if (caught instanceof ServiceException) {
								showNotification("Fallo la conexion", NotificationEventType.ERROR);
							} else if (caught instanceof LoginException) {
								showNotification("Email y/o contraseña incorrecta", NotificationEventType.ERROR);
							}
						}

						@Override
						public void onSuccess(Void result) {
							RPC.getSessionService ().getSessionData (new AsyncCallback<SessionData> () {
								@Override
								public void onFailure (Throwable caught) {
									showNotification("Error creando sesión", NotificationEventType.ERROR);
								}

								@Override
								public void onSuccess (SessionData result) {
									ClientSessionUtil.createSession (result);
									eventBus.fireEvent (new LoginSuccessfulEvent ());
								}
							});
						}
					});
					History.newItem(AppPlace.HOME.getToken());
				}
			});
		}
	}

	@Override
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
