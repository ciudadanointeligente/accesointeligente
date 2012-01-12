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

import org.accesointeligente.client.AnonymousGatekeeper;
import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.SessionData;
import org.accesointeligente.client.events.LoginSuccessfulEvent;
import org.accesointeligente.client.services.*;
import org.accesointeligente.client.uihandlers.RegisterUiHandlers;
import org.accesointeligente.model.*;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

import javax.inject.Inject;

public class RegisterPresenter extends Presenter<RegisterPresenter.MyView, RegisterPresenter.MyProxy> implements RegisterUiHandlers {
	public interface MyView extends View, HasUiHandlers<RegisterUiHandlers> {
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
		void setEmailFocus();
		String getPassword();
		Boolean validateForm();
	}

	@ProxyCodeSplit
	@UseGatekeeper(AnonymousGatekeeper.class)
	@NameToken(AppPlace.REGISTER)
	public interface MyProxy extends ProxyPlace<RegisterPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	private ActivityServiceAsync activityService;

	@Inject
	private AgeServiceAsync ageService;

	@Inject
	private InstitutionTypeServiceAsync institutionTypeService;

	@Inject
	private RegionServiceAsync regionService;

	@Inject
	private SessionServiceAsync sessionService;

	@Inject
	private UserServiceAsync userService;

	private Boolean emailAvailable = false;

	@Inject
	public RegisterPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		Window.setTitle("Registro - Acceso Inteligente");
	}

	@Override
	public void onReveal() {
		getPersonActivities();
		getInstitutionActivities();
		getInstitutionTypes();
		getPersonAges();
		getRegions();
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public void getPersonActivities() {
		getView().cleanPersonActivities();

		activityService.getActivities(true, new AsyncCallback<List<Activity>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Activity> result) {
				for (Activity activity : result) {
					getView().addPersonActivity(activity);
				}
			}
		});
	}

	@Override
	public void getInstitutionActivities() {
		getView().cleanInstitutionActivities();

		activityService.getActivities(false, new AsyncCallback<List<Activity>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo actividades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Activity> result) {
				for (Activity activity : result) {
					getView().addInstitutionActivity(activity);
				}
			}
		});
	}

	@Override
	public void getInstitutionTypes() {
		getView().cleanInstitutionTypes();

		institutionTypeService.getInstitutionTypes(new AsyncCallback<List<InstitutionType>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo tipos de institución", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<InstitutionType> result) {
				for (InstitutionType institutionType : result) {
					getView().addInstitutionType(institutionType);
				}
			}
		});

	}

	@Override
	public void getPersonAges() {
		getView().cleanPersonAges();

		ageService.getAges(new AsyncCallback<List<Age>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo edades", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Age> result) {
				for (Age age : result) {
					getView().addPersonAge(age);
				}
			}
		});
	}

	@Override
	public void getRegions() {
		getView().cleanRegions();

		regionService.getRegions(new AsyncCallback<List<Region>>() {
			@Override
			public void onFailure(Throwable caught) {
				showNotification("Error obteniendo regiones", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(List<Region> result) {
				for (Region region : result) {
					getView().addRegion(region);
				}
			}
		});
	}

	@Override
	public void checkEmail() {
		userService.checkEmailExistence(getView().getEmail(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("Ha ocurrido un error, por favor intente nuevamente", NotificationEventType.ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					showNotification("La dirección de correo ingresada ya se ha registrado", NotificationEventType.ERROR);
					emailAvailable = false;
					getView().setEmailFocus();
				} else {
					emailAvailable = true;
				}
			}
		});
	}

	@Override
	public void register() {
		if (emailAvailable == false) {
			showNotification("La dirección de correo ingresada ya se ha registrado", NotificationEventType.NOTICE);
			return;
		}

		if (getView().validateForm()) {
			final User user = new User();

			if (getView().isPerson()) {
				user.setFirstName(getView().getPersonFirstName());
				user.setLastName(getView().getPersonLastName());
				Set<Activity> activities = new HashSet<Activity>();
				activities.add(getView().getPersonActivity());
				user.setGender(getView().getPersonGender());
				user.setActivities(activities);
				user.setAge(getView().getPersonAge());
				user.setNaturalPerson(true);
			} else {
				user.setFirstName(getView().getInstitutionName());
				user.setActivities(getView().getInstitutionActivities());
				user.setInstitutionType(getView().getInstitutionType());
				user.setNaturalPerson(false);
			}

			user.setEmail(getView().getEmail());
			user.setPassword(getView().getPassword());
			user.setCountry(getView().getCountry());
			user.setRegisterDate(new Date());

			if (getView().getCountry().equals(Country.CHILE)) {
				user.setRegion(getView().getRegion());
			}

			userService.register(user, new AsyncCallback<User>() {
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

					userService.login(result.getEmail(), user.getPassword(), new AsyncCallback<Void>() {
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
							sessionService.getSessionData (new AsyncCallback<SessionData> () {
								@Override
								public void onFailure (Throwable caught) {
									showNotification("Error creando sesión", NotificationEventType.ERROR);
								}

								@Override
								public void onSuccess (SessionData result) {
									ClientSessionUtil.createSession (result);
									fireEvent (new LoginSuccessfulEvent ());
								}
							});
						}
					});

					placeManager.revealDefaultPlace();
				}
			});
		}
	}

	@Override
	public void showNotification(String message, NotificationEventType type) {
		NotificationEventParams params = new NotificationEventParams();
		params.setMessage(message);
		params.setType(type);
		params.setDuration(NotificationEventParams.DURATION_NORMAL);
		fireEvent(new NotificationEvent(params));
	}
}
