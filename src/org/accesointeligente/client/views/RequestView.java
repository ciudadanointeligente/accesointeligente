package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestPresenter;
import org.accesointeligente.client.presenters.RequestPresenterIface;
import org.accesointeligente.model.Institution;
import org.accesointeligente.model.RequestCategory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RequestView extends Composite implements RequestPresenter.Display {
	private static RequestViewUiBinder uiBinder = GWT.create(RequestViewUiBinder.class);

	interface RequestViewUiBinder extends UiBinder<Widget, RequestView> {}

	public enum State {
		INSTITUTION_SEARCH, // Step 1
		REQUEST_INFO, // Step 2
		REQUEST_DETAIL, // Step 3
		SUCCESS; // Step 4

		public State getPrevious() throws IndexOutOfBoundsException {
			int ordinal = this.ordinal();
			State[] values = State.values();

			if (ordinal > 0) {
				return values[ordinal - 1];
			} else {
				throw new IndexOutOfBoundsException();
			}
		}

		public State getNext() throws IndexOutOfBoundsException {
			int ordinal = this.ordinal();
			State[] values = State.values();

			if (ordinal < values.length) {
				return values[ordinal + 1];
			} else {
				throw new IndexOutOfBoundsException();
			}
		}
	}

	// Step 1
	@UiField HTMLPanel institutionSearchPanel;
	@UiField SuggestBox institutionSearch;

	// Step 2
	@UiField HTMLPanel requestPanel;
	@UiField TextArea requestInfo;
	@UiField TextArea requestContext;

	// Step 3
	@UiField HTMLPanel requestDetailPanel;
	@UiField TextBox requestTitle;
	@UiField FlowPanel requestCategoryPanel;
	@UiField RadioButton anotherInstitutionYes;
	@UiField RadioButton anotherInstitutionNo;
	@UiField CheckBox formatPaper;
	@UiField CheckBox formatDigital;
	@UiField CheckBox formatAny;
	@UiField CheckBox methodEmail;
	@UiField CheckBox methodMail;
	@UiField CheckBox methodOffice;

	// Step 4
	@UiField HTMLPanel successPanel;
	// TODO This panel needs a label and a button to show the summary

	// Navigation
	@UiField HTMLPanel buttonPanel;
	@UiField Button previous;
	@UiField Button next;

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
		institutionSearchPanel.setVisible(State.INSTITUTION_SEARCH.equals(state));
		requestPanel.setVisible(State.REQUEST_INFO.equals(state));
		requestDetailPanel.setVisible(State.REQUEST_DETAIL.equals(state));
		successPanel.setVisible(State.SUCCESS.equals(state));
		// Buttons
		previous.setVisible(!State.INSTITUTION_SEARCH.equals(state));
		next.setVisible(!State.SUCCESS.equals(state));
	}

	// Step 1
	@Override
	public Institution getInstitution() {
		try {
			return institutions.get(institutionSearch.getValue());
		} catch (Throwable e) {
			return null;
		}
	}

	// Step 2
	@Override
	public String getRequestInfo() {
		return requestInfo.getValue();
	}

	@Override
	public String getRequestContext() {
		return requestContext.getValue();
	}

	// Step 3
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
	public Boolean getFormatPaper() {
		return formatPaper.getValue();
	}

	@Override
	public Boolean getFormatDigital() {
		return formatDigital.getValue();
	}

	@Override
	public Boolean getFormatAny() {
		return formatAny.getValue();
	}

	@Override
	public Boolean getMethodEmail() {
		return methodEmail.getValue();
	}

	@Override
	public Boolean getMethodMail() {
		return methodMail.getValue();
	}

	@Override
	public Boolean getMethodOffice() {
		return methodOffice.getValue();
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

	// Buttons
	@UiHandler("next")
	protected void onNextClick(ClickEvent event) {
		if (presenter != null) {
			presenter.nextStep();
		}
	}

	@UiHandler("previous")
	protected void onPreviousClick(ClickEvent event) {
		if (presenter != null) {
			presenter.previousStep();
		}
	}
}
