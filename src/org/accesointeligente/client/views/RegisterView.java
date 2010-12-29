package org.accesointeligente.client.views;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashSet;
import java.util.Set;

import org.accesointeligente.client.presenters.RegisterPresenter;
import org.accesointeligente.client.presenters.RegisterPresenterIface;
import org.accesointeligente.model.Activity;
import org.accesointeligente.model.Age;
import org.accesointeligente.model.InstitutionType;
import org.accesointeligente.model.Region;
import org.accesointeligente.shared.Country;
import org.accesointeligente.shared.Gender;

public class RegisterView extends Composite implements RegisterPresenter.Display {
	private static RegisterViewUiBinder uiBinder = GWT.create(RegisterViewUiBinder.class);

	interface RegisterViewUiBinder extends UiBinder<Widget, RegisterView> {}

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
	@UiField Button register;

	private RegisterPresenterIface presenter;

	public RegisterView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(RegisterPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setErrorMessage(String message) {
		Window.alert(message);
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
		region.addItem("Selecciona tu region");
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
	public String getPassword() {
		return password1.getText();
	}

	@Override
	public Boolean validateForm() {
		if (person.getValue()) {
			if (!personFirstName.getText().matches(".*\\w+.*")) {
				return false;
			} else if (!personLastName.getText().matches(".*\\w+.*")) {
				return false;
			} else if (!personGenderFemale.getValue() && !personGenderMale.getValue()) {
				return false;
			} else if (personActivity.getSelectedIndex() < 1) {
				return false;
			} else if (personAge.getSelectedIndex() < 1) {
				return false;
			}
		} else if (institution.getValue()) {
			if (!institutionName.getText().matches(".*\\w+.*")) {
				return false;
			} else if (institutionType.getSelectedIndex() < 1) {
				return false;
			}
		} else {
			return false;
		}

		if (!countryChile.getValue() && !countryOther.getValue()) {
			return false;
		} else if (region.getSelectedIndex() < 1 && countryChile.getValue()) {
			return false;
		} else if (!email.getText().matches("\\S+")) {
			return false;
		} else if (!password1.getText().matches("\\w+")) {
			return false;
		} else if (!password1.getText().equals(password2.getText())) {
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

	@UiHandler("register")
	public void onRegisterClick(ClickEvent event) {
		if (presenter != null) {
			presenter.register();
		}
	}
}
