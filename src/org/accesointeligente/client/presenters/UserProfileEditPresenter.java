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
import org.accesointeligente.client.UserGatekeeper;
import org.accesointeligente.client.services.*;
import org.accesointeligente.client.uihandlers.UserProfileEditUiHandlers;
import org.accesointeligente.model.*;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

import javax.inject.Inject;

public class UserProfileEditPresenter extends Presenter<UserProfileEditPresenter.MyView, UserProfileEditPresenter.MyProxy> implements UserProfileEditUiHandlers {
	public interface MyView extends View, HasUiHandlers<UserProfileEditUiHandlers> {
		void cleanPersonActivities();
		void cleanInstitutionActivities();
		void cleanInstitutionTypes();
		void cleanPersonAges();
		void cleanRegions();
		void addPersonActivity(Activity activity);
		void updatePersonActivity(Activity selectedActivity, List<Activity> activities);
		void addInstitutionActivity(Activity activity, Boolean checked);
		void addInstitutionType(InstitutionType institutionType);
		void addPersonAge(Age age);
		void updateRegion(Region selectedRegion, List<Region> regions);
		void addRegion(Region region);
		String getPersonFirstName();
		void setPersonFirstName(String name);
		String getPersonLastName();
		void setPersonLastName(String lastname);
		Gender getPersonGender();
		void setPersonGender(Gender gender);
		Activity getPersonActivity();
		void setPersonActivity(Activity activity);
		Age getPersonAge();
		void setPersonAge(Age age);
		String getInstitutionName();
		void setInstitutionName(String instName);
		InstitutionType getInstitutionType();
		void setInstitutionType(InstitutionType type);
		Set<Activity> getInstitutionActivities();
		void setInstitutionActivities(List<Activity> activities);
		Country getCountry();
		void setCountry(Country country);
		Region getRegion();
		void setRegion(Region reg);
		void setEmail(String emailAddress);
		String getPassword();
		String getOldPassword();
		void setInstitutionPanelVisibility(Boolean visible);
		void setPersonPanelVisibility(Boolean visible);
		Boolean validateForm();
	}

	@ProxyCodeSplit
	@UseGatekeeper(UserGatekeeper.class)
	@NameToken(AppPlace.USERPROFILE)
	public interface MyProxy extends ProxyPlace<UserProfileEditPresenter> {
	}

	@Inject
	private ActivityServiceAsync activityService;

	@Inject
	private AgeServiceAsync ageService;

	@Inject
	private InstitutionTypeServiceAsync institutionTypeService;

	@Inject
	private RegionServiceAsync regionService;

	@Inject
	private UserServiceAsync userService;

	private User user;
	private Boolean passwordOk = false;
	private Boolean updatePassword = false;

	@Inject
	public UserProfileEditPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReset() {
		user = ClientSessionUtil.getUser();
		getPersonActivities();
		getInstitutionActivities();
		getInstitutionTypes();
		getPersonAges();
		getRegions();
		showUser();
		Window.setTitle("Mis datos - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealContentEvent(MainPresenter.SLOT_MAIN_CONTENT, this));
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public Boolean getPasswordOk() {
		return passwordOk;
	}

	@Override
	public void setPasswordOk(Boolean ok) {
		passwordOk = ok;
	}

	@Override
	public Boolean getUpdatePassword() {
		return updatePassword;
	}

	@Override
	public void setUpdatePassword(Boolean update) {
		updatePassword = update;
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
				List<Activity> userActivities = new ArrayList<Activity>(user.getActivities());
				getView().updatePersonActivity(userActivities.get(0), result);
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
					if(user.getActivities().contains(activity)) {
						getView().addInstitutionActivity(activity, true);
					} else {
						getView().addInstitutionActivity(activity, false);
					}
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
				if (!user.getNaturalPerson()) {
					getView().setInstitutionType(user.getInstitutionType());
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
				if (user.getNaturalPerson()) {
					getView().setPersonAge(user.getAge());
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
				getView().updateRegion(user.getRegion(), result);
			}
		});
	}

	@Override
	public void checkPassword(String password) {
		userService.checkPass(user.getEmail(), password, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				showNotification("No se ha podido comprobar los datos de usuario", NotificationEventType.ERROR);
				passwordOk = false;
			}

			@Override
			public void onSuccess(Boolean result) {
				passwordOk = result;
			}
		});
	}

	@Override
	public void showUser() {
		getView().setEmail(user.getEmail());

		if (user.getNaturalPerson()) {
			getView().setInstitutionPanelVisibility(false);
			getView().setPersonPanelVisibility(true);
			getView().setPersonFirstName(user.getFirstName());
			getView().setPersonLastName(user.getLastName());
			getView().setPersonGender(user.getGender());

			List<Activity> activities = new ArrayList<Activity>(user.getActivities());
			Activity activity = activities.get(0);
			getView().setPersonActivity(activity);

		} else {

			getView().setInstitutionPanelVisibility(true);
			getView().setPersonPanelVisibility(false);
			getView().setInstitutionName(user.getFirstName());
			List<Activity> activities = new ArrayList<Activity>(user.getActivities());
			getView().setInstitutionActivities(activities);
		}

		getView().setCountry(user.getCountry());
		if (user.getCountry().equals(Country.CHILE)) {
			getView().setRegion(user.getRegion());
		}
	}

	@Override
	public void saveChanges() {
		if (getView().validateForm()) {
			user = new User();
			user.setId(ClientSessionUtil.getUser().getId());
			user.setNaturalPerson(ClientSessionUtil.getUser().getNaturalPerson());
			user.setEmail(ClientSessionUtil.getUser().getEmail());
			if (user.getNaturalPerson()) {
				user.setFirstName(getView().getPersonFirstName());
				user.setLastName(getView().getPersonLastName());
				Set<Activity> activities = new HashSet<Activity>();
				activities.add(getView().getPersonActivity());
				user.setGender(getView().getPersonGender());
				user.setActivities(activities);
				user.setAge(getView().getPersonAge());
			} else {
				user.setFirstName(getView().getInstitutionName());
				user.setActivities(getView().getInstitutionActivities());
				user.setInstitutionType(getView().getInstitutionType());
			}

			if (updatePassword) {
				user.setPassword(getView().getPassword());
			} else {
				user.setPassword(ClientSessionUtil.getUser().getPassword());
			}

			user.setCountry(getView().getCountry());

			if (getView().getCountry().equals(Country.CHILE)) {
				user.setRegion(getView().getRegion());
			}

			userService.updateUser(user, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					showNotification("No se han podido actulizar sus datos, intente nuevamente", NotificationEventType.ERROR);
				}

				@Override
				public void onSuccess(Void result) {
					ClientSessionUtil.setUser(user);
					showNotification("Se han actualizado sus datos", NotificationEventType.SUCCESS);
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
