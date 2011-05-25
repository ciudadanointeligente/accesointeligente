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

import org.accesointeligente.client.presenters.RequestPresenter;
import org.accesointeligente.client.uihandlers.RequestUiHandlers;
import org.accesointeligente.client.widgets.FocusSuggestBox;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.NotificationEventType;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequestView extends ViewWithUiHandlers<RequestUiHandlers> implements RequestPresenter.MyView {
	private static RequestViewUiBinder uiBinder = GWT.create(RequestViewUiBinder.class);
	interface RequestViewUiBinder extends UiBinder<Widget, RequestView> {}
	private final Widget widget;

	@UiField FormPanel requestForm;
	@UiField HTMLPanel institutionSearchPanel;
	@UiField FocusSuggestBox institutionSearch;

	@UiField HTMLPanel requestPanel;
	@UiField TextArea requestInfo;
	@UiField TextArea requestContext;

	@UiField HTMLPanel requestDetailPanel;
	@UiField TextBox requestTitle;
	@UiField FlowPanel requestCategoryPanel;

	@UiField Button submitRequest;

	private Map<String, Institution> institutions;

	public RequestView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void resetForm() {
		requestForm.reset();
	}

	@Override
	public Institution getInstitution() {
		try {
			return institutions.get(institutionSearch.getValue());
		} catch (Throwable e) {
			return null;
		}
	}

	@Override
	public String getRequestInfo() {
		return requestInfo.getValue();
	}

	@Override
	public String getRequestContext() {
		return requestContext.getValue();
	}

	@Override
	public String getRequestTitle() {
		return requestTitle.getValue();
	}

	@Override
	public void cleanRequestCategories() {
		requestCategoryPanel.clear();
	}

	@Override
	public void addRequestCategories(RequestCategory category) {
		CheckBox cb = new CheckBox();
		cb.setFormValue(category.getId().toString());
		cb.setText(category.getName());
		requestCategoryPanel.add(cb);
	}

	// RPC
	@Override
	public Set<RequestCategory> getRequestCategories() {
		Set<RequestCategory> categories = new HashSet<RequestCategory>();

		for (int i = 0; i < requestCategoryPanel.getWidgetCount(); i++) {
			Widget widget = requestCategoryPanel.getWidget(i);

			if (widget instanceof CheckBox) {
				CheckBox cb = (CheckBox) widget;

				if (cb.getValue()) {
					RequestCategory category = new RequestCategory();
					category.setId(Integer.parseInt(cb.getFormValue()));
					category.setName(cb.getText());
					categories.add(category);
				}
			}
		}

		return categories;
	}

	@Override
	public void setInstitutions(Map<String, Institution> institutions) {
		this.institutions = institutions;

		for (String name : institutions.keySet()) {
			((MultiWordSuggestOracle) institutionSearch.getSuggestOracle()).add(name);
		}
	}

	@UiHandler("submitRequest")
	protected void onNextClick(ClickEvent event) {
		getUiHandlers().submitRequest();
	}

	@UiHandler("institutionSearch")
	protected void onInstitutionSearchBlur(BlurEvent event) {
		Timer timer = new Timer() {
			@Override
			public void run() {
				if (getInstitution() == null) {
					getUiHandlers().showNotification("No encontramos esta institución. Comprueba que está bien escrita o intenta hacer tu solicitud directamente en la institución.", NotificationEventType.NOTICE);
				}
			}
		};
		timer.schedule(500);
	}
}
