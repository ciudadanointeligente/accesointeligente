package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestPresenter;
import org.accesointeligente.client.presenters.RequestPresenterIface;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.RequestCategory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.*;

public class RequestView extends Composite implements RequestPresenter.Display {
	private static RequestViewUiBinder uiBinder = GWT.create(RequestViewUiBinder.class);

	interface RequestViewUiBinder extends UiBinder<Widget, RequestView> {}

	public enum State {
		REQUEST,
		SUCCESS
	}

	@UiField HTMLPanel institutionSearchPanel;
	@UiField SuggestBox institutionSearch;

	@UiField HTMLPanel requestPanel;
	@UiField TextArea requestInfo;
	@UiField TextArea requestContext;

	@UiField HTMLPanel requestDetailPanel;
	@UiField TextBox requestTitle;
	@UiField FlowPanel requestCategoryPanel;
	@UiField RadioButton anotherInstitutionYes;
	@UiField RadioButton anotherInstitutionNo;

	@UiField HTMLPanel successPanel;
	@UiField Button showRequest;

	@UiField Button submitRequest;

	private State state;
	private Map<String, Institution> institutions;
	private RequestPresenterIface presenter;

	public RequestView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(RequestPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void displayMessage(String message) {
		Window.alert(message);
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
		// Panels
		institutionSearchPanel.setVisible(State.REQUEST.equals(state));
		requestPanel.setVisible(State.REQUEST.equals(state));
		requestDetailPanel.setVisible(State.REQUEST.equals(state));
		successPanel.setVisible(State.SUCCESS.equals(state));
		// Buttons
		submitRequest.setVisible(State.REQUEST.equals(state));
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
	public Boolean getAnotherInstitutionYes() {
		return anotherInstitutionYes.getValue();
	}

	@Override
	public Boolean getAnotherInstitutionNo() {
		return anotherInstitutionNo.getValue();
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
		if (presenter != null) {
			presenter.submitRequest();
		}
	}

	@UiHandler("showRequest")
	protected void onShowRequestClick(ClickEvent event) {
		if (presenter != null) {
			presenter.showRequest();
		}
	}
}
