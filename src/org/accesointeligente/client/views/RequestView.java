package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestPresenter;
import org.accesointeligente.client.presenters.RequestPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RequestView extends Composite implements RequestPresenter.Display {
	private static RequestViewUiBinder uiBinder = GWT.create(RequestViewUiBinder.class);

	interface RequestViewUiBinder extends UiBinder<Widget, RequestView> {}

	public enum State {
		INSTITUTION_SEARCH,
		REQUEST_INFO,
		REQUEST_DETAIL,
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
	@UiField CheckBox formatPaper;
	@UiField CheckBox formatDigital;
	@UiField CheckBox formatAny;
	@UiField CheckBox methodEmail;
	@UiField CheckBox methodMail;
	@UiField CheckBox methodOffice;

	@UiField HTMLPanel successPanel;

	@UiField HTMLPanel buttonPanel;
	@UiField Button previous;
	@UiField Button next;

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
}
