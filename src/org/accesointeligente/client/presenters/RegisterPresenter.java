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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
