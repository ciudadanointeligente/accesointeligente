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
package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RegisterPresenter;
import org.accesointeligente.client.uihandlers.RegisterUiHandlers;
import org.accesointeligente.model.*;
import org.accesointeligente.shared.*;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import java.util.HashSet;
import java.util.Set;

public class RegisterView extends ViewWithUiHandlers<RegisterUiHandlers> implements RegisterPresenter.MyView {
	private static RegisterViewUiBinder uiBinder = GWT.create(RegisterViewUiBinder.class);
	interface RegisterViewUiBinder extends UiBinder<Widget, RegisterView> {}
	private final Widget widget;

	@UiField RadioButton person;
	@UiField RadioButton institution;
	@UiField HTMLPanel personPanel;
	@UiField TextBox personFirstName;
	@UiField TextBox personLastName;
	@UiField RadioButton personGenderFemale;
	@UiField RadioButton personGenderMale;
	@UiField ListBox personActivity;
	@UiField ListBox personAge;
	@UiField HTMLPanel institutionPanel;
	@UiField TextBox institutionName;
	@UiField ListBox institutionType;
	@UiField FlowPanel institutionActivities;
	@UiField RadioButton countryChile;
	@UiField RadioButton countryOther;
	@UiField ListBox region;
	@UiField TextBox email;
	@UiField PasswordTextBox password1;
	@UiField PasswordTextBox password2;
	@UiField CheckBox termsAndConditions;
	@UiField Button register;

	public RegisterView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void cleanPersonActivities() {
		personActivity.clear();
		personActivity.addItem("Selecciona tu actividad");
	}

	@Override
	public void cleanInstitutionActivities() {
		institutionActivities.clear();
	}

	@Override
	public void cleanInstitutionTypes() {
		institutionType.clear();
		institutionType.addItem("Selecciona tu tipo");
	}

	@Override
	public void cleanPersonAges() {
		personAge.clear();
		personAge.addItem("Selecciona tu edad");
	}

	@Override
	public void cleanRegions() {
		region.clear();
		region.addItem("Selecciona tu región");
	}

	@Override
	public void addPersonActivity(Activity activity) {
		personActivity.addItem(activity.getName(), activity.getId().toString());
	}

	@Override
	public void addInstitutionActivity(Activity activity) {
		CheckBox cb = new CheckBox();
		cb.setFormValue(activity.getId().toString());
		cb.setText(activity.getName());
		institutionActivities.add(cb);
	}

	@Override
	public void addInstitutionType(InstitutionType institutionType) {
		this.institutionType.addItem(institutionType.getName(), institutionType.getId().toString());
	}

	@Override
	public void addPersonAge(Age age) {
		personAge.addItem(age.getName(), age.getId().toString());
	}

	@Override
	public void addRegion(Region region) {
		this.region.addItem(region.getName(), region.getId().toString());
	}

	@Override
	public Boolean isPerson() {
		return person.getValue();
	}

	@Override
	public String getPersonFirstName() {
		return personFirstName.getText();
	}

	@Override
	public String getPersonLastName() {
		return personLastName.getText();
	}

	@Override
	public Gender getPersonGender() {
		if (personGenderFemale.getValue()) {
			return Gender.FEMALE;
		} else if (personGenderMale.getValue()) {
			return Gender.MALE;
		} else {
			return null;
		}
	}

	@Override
	public Activity getPersonActivity() {
		Activity activity = new Activity();
		activity.setId(Integer.parseInt(personActivity.getValue(personActivity.getSelectedIndex())));
		activity.setName(personActivity.getItemText(personActivity.getSelectedIndex()));
		return activity;
	}

	@Override
	public Age getPersonAge() {
		Age age = new Age();
		age.setId(Integer.parseInt(personAge.getValue(personAge.getSelectedIndex())));
		age.setName(personAge.getItemText(personAge.getSelectedIndex()));
		return age;
	}

	@Override
	public String getInstitutionName() {
		return institutionName.getText();
	}

	@Override
	public InstitutionType getInstitutionType() {
		InstitutionType type = new InstitutionType();
		type.setId(Integer.parseInt(institutionType.getValue(institutionType.getSelectedIndex())));
		type.setName(institutionType.getItemText(institutionType.getSelectedIndex()));
		return type;
	}

	@Override
	public Set<Activity> getInstitutionActivities() {
		Set<Activity> activities = new HashSet<Activity>();

		for (int i = 0; i < institutionActivities.getWidgetCount(); i++) {
			Widget widget = institutionActivities.getWidget(i);

			if (widget instanceof CheckBox) {
				CheckBox cb = (CheckBox) widget;

				if (cb.getValue()) {
					Activity activity = new Activity();
					activity.setId(Integer.parseInt(cb.getFormValue()));
					activity.setName(cb.getText());
					activities.add(activity);
				}
			}
		}

		return activities;
	}

	@Override
	public Country getCountry() {
		if (countryChile.getValue()) {
			return Country.CHILE;
		} else {
			return Country.OTHER;
		}
	}

	@Override
	public Region getRegion() {
		Region reg = new Region();
		reg.setId(Integer.parseInt(region.getValue(region.getSelectedIndex())));
		reg.setNumber(region.getSelectedIndex() + 1);
		reg.setName(region.getItemText(region.getSelectedIndex()));
		return reg;
	}

	@Override
	public String getEmail() {
		return email.getText();
	}

	@Override
	public void setEmailFocus() {
		email.setFocus(true);
	}

	@Override
	public String getPassword() {
		return password1.getText();
	}

	@Override
	public Boolean validateForm() {
		if (person.getValue()) {
			if (!personFirstName.getText().matches(".*\\w+.*")) {
				getUiHandlers().showNotification("Debe ingresar su nombre", NotificationEventType.NOTICE);
				return false;
			} else if (!personLastName.getText().matches(".*\\w+.*")) {
				getUiHandlers().showNotification("Debe ingresar su apellido", NotificationEventType.NOTICE);
				return false;
			} else if (!personGenderFemale.getValue() && !personGenderMale.getValue()) {
				getUiHandlers().showNotification("Debe seleccionar su sexo", NotificationEventType.NOTICE);
				return false;
			} else if (personActivity.getSelectedIndex() < 1) {
				getUiHandlers().showNotification("Actividad no válida, debe seleccionar una", NotificationEventType.NOTICE);
				return false;
			} else if (personAge.getSelectedIndex() < 1) {
				getUiHandlers().showNotification("Edad no válida, debe seleccionar una", NotificationEventType.NOTICE);
				return false;
			}
		} else if (institution.getValue()) {
			if (!institutionName.getText().matches(".*\\w+.*")) {
				getUiHandlers().showNotification("Debe ingresar su razón social", NotificationEventType.NOTICE);
				return false;
			} else if (institutionType.getSelectedIndex() < 1) {
				getUiHandlers().showNotification("Tipo de institución no válida, debe seleccionar una", NotificationEventType.NOTICE);
				return false;
			}
		} else {
			return false;
		}

		if (!countryChile.getValue() && !countryOther.getValue()) {
			getUiHandlers().showNotification("País no válido, debe seleccionar uno", NotificationEventType.NOTICE);
			return false;
		} else if (region.getSelectedIndex() < 1 && countryChile.getValue()) {
			getUiHandlers().showNotification("Región no válida, debe seleccionar una", NotificationEventType.NOTICE);
			return false;
		} else if (!Validator.validateEmail(getEmail())) {
			getUiHandlers().showNotification("Dirección de email no válida", NotificationEventType.NOTICE);
			return false;
		} else if (!password1.getText().matches("[A-Za-z0-9!@#$%^&*\\(\\):\\-_=+]+")) {
			getUiHandlers().showNotification("Contraseña no válida", NotificationEventType.NOTICE);
			return false;
		} else if (!password1.getText().equals(password2.getText())) {
			getUiHandlers().showNotification("Revise los campos de ingreso de contraseñas, no coinciden", NotificationEventType.NOTICE);
			return false;
		}

		return true;
	}

	@UiHandler("person")
	public void onPersonValueChange(ValueChangeEvent<Boolean> event) {
		personPanel.setVisible(event.getValue());
		institutionPanel.setVisible(!event.getValue());
	}

	@UiHandler("institution")
	public void onInstitutionValueChange(ValueChangeEvent<Boolean> event) {
		personPanel.setVisible(!event.getValue());
		institutionPanel.setVisible(event.getValue());
	}

	@UiHandler("countryChile")
	public void onCountryChileValueChange(ValueChangeEvent<Boolean> event) {
		region.setEnabled(event.getValue());
	}

	@UiHandler("countryOther")
	public void onCountryOtherValueChange(ValueChangeEvent<Boolean> event) {
		region.setEnabled(!event.getValue());
	}

	@UiHandler("email")
	public void onEmailBlur(BlurEvent event) {
		getUiHandlers().checkEmail();
	}

	@UiHandler("register")
	public void onRegisterClick(ClickEvent event) {
		getUiHandlers().register();
	}

	@UiHandler("termsAndConditions")
	public void onTermsAndConditionsChange(ValueChangeEvent<Boolean> event) {
		if (termsAndConditions.getValue() == true) {
			register.setEnabled(true);
		} else {
			register.setEnabled(false);
		}
	}
}
