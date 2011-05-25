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

import org.accesointeligente.client.presenters.RequestEditPresenter;
import org.accesointeligente.client.uihandlers.RequestEditUiHandlers;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.RequestCategory;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequestEditView extends ViewWithUiHandlers<RequestEditUiHandlers> implements RequestEditPresenter.MyView {
	private static RequestEditViewUiBinder uiBinder = GWT.create(RequestEditViewUiBinder.class);
	interface RequestEditViewUiBinder extends UiBinder<Widget, RequestEditView> {}
	private final Widget widget;

	@UiField HTMLPanel institutionSearchPanel;
	@UiField SuggestBox institutionSearch;

	@UiField HTMLPanel requestPanel;
	@UiField TextArea requestInfo;
	@UiField TextArea requestContext;

	@UiField HTMLPanel requestDetailPanel;
	@UiField TextBox requestTitle;
	@UiField FlowPanel requestCategoryPanel;

	@UiField Button submitRequest;

	private Map<String, Institution> institutions;

	public RequestEditView() {
		widget = uiBinder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
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
	public void setInstitution(Institution institution) {
		institutionSearch.setText(institution.getName());
	}

	@Override
	public String getRequestInfo() {
		return requestInfo.getValue();
	}

	@Override
	public void setRequestInfo(String info) {
		requestInfo.setText(info);
	}

	@Override
	public String getRequestContext() {
		return requestContext.getValue();
	}

	@Override
	public void setRequestContext(String context) {
		requestContext.setText(context);
	}

	@Override
	public String getRequestTitle() {
		return requestTitle.getValue();
	}

	@Override
	public void setRequestTitle(String title) {
		requestTitle.setText(title);
	}

	@Override
	public void cleanRequestCategories() {
		requestCategoryPanel.clear();
	}

	@Override
	public void addRequestCategories(RequestCategory category, Boolean checked) {
		CheckBox cb = new CheckBox();
		cb.setFormValue(category.getId().toString());
		cb.setText(category.getName());
		if (checked) {
			cb.setValue(true);
		}
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
}
