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

import org.accesointeligente.client.presenters.ResponseUserSatisfactionPresenter;
import org.accesointeligente.client.uihandlers.ResponseUserSatisfactionUiHandlers;
import org.accesointeligente.shared.ResponseType;
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
	@UiField HTMLPanel backHomePanel;
	@UiField Button backHomeButton;

	public ResponseUserSatisfactionView() {
		widget = uiBinder.createAndBindUi(this);
		requestDerivedRadioButton.setText(ResponseType.DERIVATION.getName());
		requestExtendedRadioButton.setText(ResponseType.EXTENSION.getName());
		requestDeniedRadioButton.setText(ResponseType.DENIAL.getName());
		responseRadioButtonIncomplete.setText(ResponseType.INCOMPLETE.getName());
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

	@Override
	public void showBackHomePanel(Boolean visible) {
		backHomePanel.setVisible(visible);
	}

	@UiHandler("userSatisfiedButton")
	public void onUserSatisfiedClick(ClickEvent event) {
		getUiHandlers().updateResponse(ResponseType.INFORMATION, UserSatisfaction.SATISFIED);
	}

	@UiHandler("userUnsatisfiedButton")
	public void onUserUnsatisfiedClick(ClickEvent event) {
		showUserSatisfactionPanel(false);
		showRequestStatusPanel(true);
	}

	@UiHandler("submitUserInsatisfactionButton")
	public void onSubmitUserInsatisfactionButtonClick(ClickEvent event) {
		ResponseType responseType = null;
		if (requestDerivedRadioButton.getValue()) {
			responseType = ResponseType.DERIVATION;
		} else if (requestExtendedRadioButton.getValue()) {
			responseType = ResponseType.EXTENSION;
		} else if (requestDeniedRadioButton.getValue()) {
			responseType = ResponseType.DENIAL;
		} else if (responseRadioButtonIncomplete.getValue()) {
			responseType = ResponseType.INCOMPLETE;
		}
		if (responseType != null) {
			getUiHandlers().updateResponse(responseType, UserSatisfaction.UNSATISFIED);
		}
	}

	@UiHandler("cancelUserInstafiscationButton")
	public void onCancelUserInstafiscationButtonClick(ClickEvent event) {
		showUserSatisfactionPanel(true);
		showRequestStatusPanel(false);
	}

	@UiHandler("backHomeButton")
	public void onBackHomeButtonClick(ClickEvent event) {
		getUiHandlers().goHome();
	}
}
