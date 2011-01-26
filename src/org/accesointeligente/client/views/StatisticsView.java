package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.StatisticsPresenter;
import org.accesointeligente.client.presenters.StatisticsPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StatisticsView extends Composite implements StatisticsPresenter.Display {
	private static StatisticsViewUiBinder uiBinder = GWT.create(StatisticsViewUiBinder.class);
	interface StatisticsViewUiBinder extends UiBinder<Widget, StatisticsView> {}

	StatisticsPresenterIface presenter;

	public StatisticsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(StatisticsPresenterIface presenter) {
		this.presenter = presenter;
	}
}
