package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestEditPresenter;
import org.accesointeligente.client.presenters.RequestEditPresenterIface;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.RequestCategory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequestEditView extends Composite implements RequestEditPresenter.Display {
	private static RequestEditViewUiBinder uiBinder = GWT.create(RequestEditViewUiBinder.class);
	interface RequestEditViewUiBinder extends UiBinder<Widget, RequestEditView> {}

	public enum State {
		EDIT,
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
	private RequestEditPresenterIface presenter;

	public RequestEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(RequestEditPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void setState(State state) {
		this.state = state;
		// Panels
		institutionSearchPanel.setVisible(State.EDIT.equals(state));
		requestPanel.setVisible(State.EDIT.equals(state));
		requestDetailPanel.setVisible(State.EDIT.equals(state));
		successPanel.setVisible(State.SUCCESS.equals(state));
		// Buttons
		submitRequest.setVisible(State.EDIT.equals(state));
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
	public Boolean getAnotherInstitutionYes() {
		return anotherInstitutionYes.getValue();
	}

	@Override
	public Boolean getAnotherInstitutionNo() {
		return anotherInstitutionNo.getValue();
	}

	@Override
	public void setAnotherInstitution(Boolean another) {
		if (another) {
			anotherInstitutionYes.setValue(true);
			anotherInstitutionNo.setValue(false);
		} else {
			anotherInstitutionYes.setValue(false);
			anotherInstitutionNo.setValue(true);
		}
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
