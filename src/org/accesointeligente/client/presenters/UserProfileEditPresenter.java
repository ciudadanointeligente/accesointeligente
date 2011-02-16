package org.accesointeligente.client.presenters;

import org.accesointeligente.client.ClientSessionUtil;
import org.accesointeligente.client.services.RPC;
import org.accesointeligente.model.*;
import org.accesointeligente.shared.*;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserProfileEditPresenter extends WidgetPresenter<UserProfileEditPresenter.Display> implements UserProfileEditPresenterIface {
	public interface Display extends WidgetDisplay {
		void setPresenter(UserProfileEditPresenterIface presenter);
		void cleanPersonActivities();
		void cleanInstitutionActivities();
		void cleanInstitutionTypes();
		void cleanPersonAges();
		void cleanRegions();
		void addPersonActivity(Activity activity);
		void addInstitutionActivity(Activity activity, Boolean checked);
		void addInstitutionType(InstitutionType institutionType);
		void addPersonAge(Age age);
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

	User user = ClientSessionUtil.getUser();
	Boolean passwordOk = false;
	Boolean updatePassword = false;

	public UserProfileEditPresenter(Display display, EventBus eventBus) {
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
		showUser();
	}

	@Override
	protected void onUnbind() {
	}

	@Override
	protected void onRevealDisplay() {
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
				if (user.getNaturalPerson()) {
					display.setPersonActivity(result.get(0));
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
					if(user.getActivities().contains(activity)) {
						display.addInstitutionActivity(activity, true);
					} else {
						display.addInstitutionActivity(activity, false);
					}
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
				if (!user.getNaturalPerson()) {
					display.setInstitutionType(user.getInstitutionType());
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
				if (user.getNaturalPerson()) {
					display.setPersonAge(user.getAge());
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
				if (user.getCountry().equals(Country.CHILE)) {
					display.setRegion(user.getRegion());
				}
			}
		});
	}

	@Override
	public void checkPassword(String password) {
		RPC.getUserService().checkPass(user.getEmail(), password, new AsyncCallback<Boolean>() {

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
		display.setEmail(user.getEmail());
		if (user.getNaturalPerson()) {
			display.setInstitutionPanelVisibility(false);
			display.setPersonPanelVisibility(true);
			display.setPersonFirstName(user.getFirstName());
			display.setPersonLastName(user.getLastName());
			display.setPersonGender(user.getGender());
		} else {
			display.setInstitutionPanelVisibility(true);
			display.setPersonPanelVisibility(false);
			display.setInstitutionName(user.getFirstName());
		}

		display.setCountry(user.getCountry());
	}

	@Override
	public void saveChanges() {
		if (display.validateForm()) {
			user = new User();
			user.setId(ClientSessionUtil.getUser().getId());
			user.setNaturalPerson(ClientSessionUtil.getUser().getNaturalPerson());
			user.setEmail(ClientSessionUtil.getUser().getEmail());
			if (user.getNaturalPerson()) {
				user.setFirstName(display.getPersonFirstName());
				user.setLastName(display.getPersonLastName());
				Set<Activity> activities = new HashSet<Activity>();
				activities.add(display.getPersonActivity());
				user.setGender(display.getPersonGender());
				user.setActivities(activities);
				user.setAge(display.getPersonAge());
			} else {
				user.setFirstName(display.getInstitutionName());
				user.setActivities(display.getInstitutionActivities());
				user.setInstitutionType(display.getInstitutionType());
			}

			if (updatePassword) {
				user.setPassword(display.getPassword());
			} else {
				user.setPassword(ClientSessionUtil.getUser().getPassword());
			}

			user.setCountry(display.getCountry());

			if (display.getCountry().equals(Country.CHILE)) {
				user.setRegion(display.getRegion());
			}

			RPC.getUserService().updateUser(user, new AsyncCallback<Void>() {

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
		eventBus.fireEvent(new NotificationEvent(params));
	}
}
