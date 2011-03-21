package org.accesointeligente.client.inject;

import org.accesointeligente.client.presenters.*;
import org.accesointeligente.client.views.*;

import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

import com.google.inject.Singleton;

public class PresenterModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
		bindPresenter(MainPresenter.class, MainPresenter.Display.class, MainView.class);
		bindPresenter(HomePresenter.class, HomePresenter.Display.class, HomeView.class);
		bindPresenter(RequestPresenter.class, RequestPresenter.Display.class, RequestView.class);
		bindPresenter(RequestStatusPresenter.class, RequestStatusPresenter.Display.class, RequestStatusView.class);
		bindPresenter(RequestEditPresenter.class, RequestEditPresenter.Display.class, RequestEditView.class);
		bindPresenter(RequestResponsePresenter.class, RequestResponsePresenter.Display.class, RequestResponseView.class);
		bindPresenter(RequestListPresenter.class, RequestListPresenter.Display.class, RequestListView.class);
		bindPresenter(RequestSearchPresenter.class, RequestSearchPresenter.Display.class, RequestSearchView.class);
		bindPresenter(StatisticsPresenter.class, StatisticsPresenter.Display.class, StatisticsView.class);
		bindPresenter(AboutProjectPresenter.class, AboutProjectPresenter.Display.class, AboutProjectView.class);
		bindPresenter(UserProfileEditPresenter.class, UserProfileEditPresenter.Display.class, UserProfileEditView.class);
		bindPresenter(ContactPresenter.class, ContactPresenter.Display.class, ContactView.class);
		bindPresenter(LoginPresenter.class, LoginPresenter.Display.class, LoginView.class);
		bindPresenter(RegisterPresenter.class, RegisterPresenter.Display.class, RegisterView.class);
		bindPresenter(PasswordRecoveryPresenter.class, PasswordRecoveryPresenter.Display.class, PasswordRecoveryView.class);
	}
}
