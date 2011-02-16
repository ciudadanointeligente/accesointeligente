package org.accesointeligente.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.Date;

public class UserResponseWidget extends Composite {
	private static UserResponseWidgetUiBinder uiBinder = GWT.create(UserResponseWidgetUiBinder.class);
	interface UserResponseWidgetUiBinder extends UiBinder<Widget, UserResponseWidget> {}

	@UiField Label userResponseDate;
	@UiField Label userResponseInformation;

	public UserResponseWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setDate(Date date) {
		userResponseDate.setText(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(date));
	}

	public void setInformation(String information) {
		userResponseInformation.setText(information);
	}
}
