package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.RequestSearchPresenter;
import org.accesointeligente.client.presenters.RequestSearchPresenterIface;
import org.accesointeligente.model.Institution;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

import java.util.Date;
import java.util.Map;

public class RequestSearchView extends Composite implements RequestSearchPresenter.Display {
	private static RequestSearchViewUiBinder uiBinder = GWT.create(RequestSearchViewUiBinder.class);

	interface RequestSearchViewUiBinder extends UiBinder<Widget, RequestSearchView> {}

	@UiField TextBox keyWord;
	@UiField SuggestBox institution;
	@UiField DateBox minDate;
	@UiField DateBox maxDate;
	@UiField CheckBox statusPending;
	@UiField CheckBox statusClosed;
	@UiField CheckBox statusExpired;
	@UiField CheckBox statusDerived;
	@UiField Button search;
	@UiField Button clear;

	private Map<String, Institution> institutions;
	private RequestSearchPresenterIface presenter;

	public RequestSearchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(RequestSearchPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public String getKeyWord() {
		return keyWord.getText();
	}

	@Override
	public Institution getInstitution() {
		try {
			return institutions.get(institution.getValue());
		} catch (Throwable e) {
			return null;
		}
	}

	@Override
	public Date getMinDate() {
		return minDate.getValue();
	}

	@Override
	public Date getMaxDate() {
		return maxDate.getValue();
	}

	@Override
	public Boolean getStatusPending() {
		return statusPending.getValue();
	}

	@Override
	public Boolean getStatusClosed() {
		return statusClosed.getValue();
	}

	@Override
	public Boolean getStatusExpired() {
		return statusExpired.getValue();
	}

	@Override
	public Boolean getStatusDerived() {
		return statusDerived.getValue();
	}

	@Override
	public void displayMessage(String message) {
		Window.alert(message);
	}

	@Override
	public void setInstitutions(Map<String, Institution> institutions) {
		this.institutions = institutions;

		for (String name : institutions.keySet()) {
			((MultiWordSuggestOracle) institution.getSuggestOracle()).add(name);
		}
	}

	@UiFactory
	DateBox getDateBox() {
		DateBox myDateBox = new DateBox();
		myDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
		return myDateBox;
	}

	@UiHandler("search")
	protected void onSearchClick(ClickEvent event) {
		if (presenter != null) {
			presenter.requestSearch();
		}
	}

	@UiHandler("search")
	protected void onSearchKeyDown(KeyDownEvent event) {
		if (presenter != null) {
			presenter.requestSearch();
		}
	}

	@UiHandler("clear")
	protected void onClearClick(ClickEvent event) {
		keyWord.setText("");
		institution.setText("");
		minDate.setValue(null);
		maxDate.setValue(null);
		statusPending.setValue(false);
		statusClosed.setValue(false);
		statusExpired.setValue(false);
		statusDerived.setValue(false);
	}

	@UiHandler("clear")
	protected void onClearKeyDown(KeyDownEvent event) {
		keyWord.setText("");
		institution.setText("");
		minDate.setValue(null);
		maxDate.setValue(null);
		statusPending.setValue(false);
		statusClosed.setValue(false);
		statusExpired.setValue(false);
		statusDerived.setValue(false);
	}
}
