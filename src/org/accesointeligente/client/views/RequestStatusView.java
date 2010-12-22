package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestStatusPresenter;
import org.accesointeligente.client.presenters.RequestStatusPresenterIface;
import org.accesointeligente.model.RequestCategory;
import org.accesointeligente.shared.RequestStatus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.Iterator;
import java.util.Set;

public class RequestStatusView extends Composite implements RequestStatusPresenter.Display {
	private static RequestStatusViewUiBinder uiBinder = GWT.create(RequestStatusViewUiBinder.class);
	interface RequestStatusViewUiBinder extends UiBinder<Widget, RequestStatusView> {}

	// UIFields
	@UiField Label requestStatus;
	@UiField Label institutionName;
	@UiField Label requestInfo;
	@UiField Label requestContext;
	@UiField Label requestTitle;
	@UiField FlowPanel requestCategoryPanel;
	@UiField RadioButton anotherInstitutionYes;
	@UiField RadioButton anotherInstitutionNo;

	private RequestStatusPresenterIface presenter;

	public RequestStatusView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(RequestStatusPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setStatus(RequestStatus status) {
		requestStatus.setText(status.getName());
	}

	@Override
	public void setInstitutionName(String name) {
		institutionName.setText(name);
	}

	@Override
	public void setRequestInfo(String info) {
		requestInfo.setText(info);
	}

	@Override
	public void setRequestContext(String context) {
		requestContext.setText(context);
	}

	@Override
	public void setRequestTitle(String title) {
		requestTitle.setText(title);
	}

	@Override
	public void addRequestCategories(RequestCategory category) {
		Label categoryLabel = new Label();
		categoryLabel.setText(category.getName());
		requestCategoryPanel.add(categoryLabel);
	}

	@Override
	public void setRequestCategories(Set<RequestCategory> categories) {
		Iterator<RequestCategory> iterator = categories.iterator();

		while (iterator.hasNext()) {
			RequestCategory category = iterator.next();
			addRequestCategories(category);
		}
	}

	@Override
	public void setAnotherInstitution(Boolean anotherInstitution) {
		if (anotherInstitution) {
			anotherInstitutionYes.setValue(true);
		} else {
			anotherInstitutionNo.setValue(true);
		}
	}

	@Override
	public void displayMessage(String message) {
		Window.alert(message);
	}
}