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

import org.accesointeligente.client.presenters.TermsAndConditionsPresenter;
import org.accesointeligente.client.uihandlers.TermsAndConditionsUiHandlers;

import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import javax.inject.Inject;

public class TermsAndConditionsView extends PopupViewWithUiHandlers<TermsAndConditionsUiHandlers> implements TermsAndConditionsPresenter.MyView {
	private static TermsAndConditionsViewUiBinder uiBinder = GWT.create(TermsAndConditionsViewUiBinder.class);
	interface TermsAndConditionsViewUiBinder extends UiBinder<Widget, TermsAndConditionsView> {}
	private final Widget widget;

	@UiField FocusPanel mainPanel;
	@UiField Label close;

	@Inject
	protected TermsAndConditionsView(EventBus eventBus) {
		super(eventBus);
		widget = uiBinder.createAndBindUi(this);
		setAutoHideOnNavigationEventEnabled(true);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@UiHandler("mainPanel")
	void onLoginPanelKeyDown(KeyDownEvent key) {
		if (key.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			getUiHandlers().close();
		}
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent click) {
		getUiHandlers().close();
	}
}
