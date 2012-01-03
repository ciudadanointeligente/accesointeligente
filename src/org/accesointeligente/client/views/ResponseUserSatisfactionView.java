package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.ResponseUserSatisfactionPresenter;
import org.accesointeligente.client.uihandlers.ResponseUserSatisfactionUiHandlers;
import org.accesointeligente.shared.RequestStatus;
import org.accesointeligente.shared.UserSatisfaction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ResponseUserSatisfactionView extends ViewWithUiHandlers<ResponseUserSatisfactionUiHandlers> implements ResponseUserSatisfactionPresenter.MyView {
	private static ResponseUserSatisfactionViewUiBinder uiBinder = GWT.create(ResponseUserSatisfactionViewUiBinder.class);

	interface ResponseUserSatisfactionViewUiBinder extends UiBinder<Widget, ResponseUserSatisfactionView> {
	}

	private final Widget widget;

	@UiField HTMLPanel userSatisfactionPanel;
	@UiField Button userSatisfiedButton;
	@UiField Button userUnsatisfiedButton;
	@UiField HTMLPanel requestStatusPanel;
	@UiField RadioButton requestDerivedRadioButton;
	@UiField RadioButton requestExtendedRadioButton;
	@UiField RadioButton requestDeniedRadioButton;
	@UiField RadioButton responseRadioButtonIncomplete;
	@UiField Button submitUserInsatisfactionButton;
	@UiField Button cancelUserInstafiscationButton;

	public ResponseUserSatisfactionView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void showUserSatisfactionPanel(Boolean visible) {
		userSatisfactionPanel.setVisible(visible);
	}

	@Override
	public void showRequestStatusPanel(Boolean visible) {
		requestStatusPanel.setVisible(visible);
	}

	@UiHandler("userSatisfiedButton")
	public void onUserSatisfiedClick(ClickEvent event) {
		getUiHandlers().setResponseUserSatisfaction(UserSatisfaction.SATISFIED);
	}

	@UiHandler("userUnsatisfiedButton")
	public void onUserUnsatisfiedClick(ClickEvent event) {
		showUserSatisfactionPanel(false);
		showRequestStatusPanel(true);
	}

	@UiHandler("submitUserInsatisfactionButton")
	public void onSubmitUserInsatisfactionButtonClick(ClickEvent event) {
		RequestStatus requestStatus = null;
		if (requestDerivedRadioButton.getValue()) {
			requestStatus = RequestStatus.DERIVED;
		} else if (requestExtendedRadioButton.getValue()) {
			requestStatus = RequestStatus.EXTENDED;
		} else if (requestDeniedRadioButton.getValue()) {
			requestStatus = RequestStatus.DENIED;
		} else if (responseRadioButtonIncomplete.getValue()) {
			requestStatus = getUiHandlers().getRequestStatus();
		}
		if (requestStatus != null) {
			getUiHandlers().setRequestStatus(requestStatus);
			getUiHandlers().setResponseUserSatisfaction(UserSatisfaction.UNSATISFIED);
		}
	}

	@UiHandler("cancelUserInstafiscationButton")
	public void onCancelUserInstafiscationButtonClick(ClickEvent event) {
		showUserSatisfactionPanel(true);
		showRequestStatusPanel(false);
	}
}
