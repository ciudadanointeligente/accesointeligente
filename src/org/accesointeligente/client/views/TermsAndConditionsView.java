package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.TermsAndConditionsPresenter;
import org.accesointeligente.client.presenters.TermsAndConditionsPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class TermsAndConditionsView extends Composite implements TermsAndConditionsPresenter.Display {
	private static TermsAndConditionsViewUiBinder uiBinder = GWT.create(TermsAndConditionsViewUiBinder.class);
	interface TermsAndConditionsViewUiBinder extends UiBinder<Widget, TermsAndConditionsView> {}

	@UiField FocusPanel mainPanel;
	@UiField Label close;

	TermsAndConditionsPresenterIface presenter;

	public TermsAndConditionsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TermsAndConditionsView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(TermsAndConditionsPresenterIface presenter) {
		this.presenter = presenter;
	}

	@UiHandler("mainPanel")
	void onLoginPanelKeyDown(KeyDownEvent key) {
		if (presenter != null && key.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			presenter.close();
		}
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent click) {
		if (presenter != null) {
			presenter.close();
		}
	}
}
