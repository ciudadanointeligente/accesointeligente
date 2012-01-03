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
package org.accesointeligente.client.inject;

import org.accesointeligente.client.*;
import org.accesointeligente.client.presenters.*;
import org.accesointeligente.client.views.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;

import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class PresenterModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
		bind(UserGatekeeper.class).in(Singleton.class);
		bind(AnonymousGatekeeper.class).in(Singleton.class);
		bind(PlaceHistory.class).asEagerSingleton();
		bind(RootPresenter.class).asEagerSingleton();
		bindPresenter(MainPresenter.class, MainPresenter.MyView.class, MainView.class, MainPresenter.MyProxy.class);
		bindPresenter(HomePresenter.class, HomePresenter.MyView.class, HomeView.class, HomePresenter.MyProxy.class);
		bindPresenter(RequestPresenter.class, RequestPresenter.MyView.class, RequestView.class, RequestPresenter.MyProxy.class);
		bindPresenter(RequestStatusPresenter.class, RequestStatusPresenter.MyView.class, RequestStatusView.class, RequestStatusPresenter.MyProxy.class);
		bindPresenter(RequestEditPresenter.class, RequestEditPresenter.MyView.class, RequestEditView.class, RequestEditPresenter.MyProxy.class);
		bindPresenter(RequestResponsePresenter.class, RequestResponsePresenter.MyView.class, RequestResponseView.class, RequestResponsePresenter.MyProxy.class);
		bindPresenter(RequestListPresenter.class, RequestListPresenter.MyView.class, RequestListView.class, RequestListPresenter.MyProxy.class);
		bindPresenter(StatisticsPresenter.class, StatisticsPresenter.MyView.class, StatisticsView.class, StatisticsPresenter.MyProxy.class);
		bindPresenter(AboutProjectPresenter.class, AboutProjectPresenter.MyView.class, AboutProjectView.class, AboutProjectPresenter.MyProxy.class);
		bindPresenter(UserProfileEditPresenter.class, UserProfileEditPresenter.MyView.class, UserProfileEditView.class, UserProfileEditPresenter.MyProxy.class);
		bindPresenter(ContactPresenter.class, ContactPresenter.MyView.class, ContactView.class, ContactPresenter.MyProxy.class);
		bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class, LoginView.class, LoginPresenter.MyProxy.class);
		bindPresenter(RegisterPresenter.class, RegisterPresenter.MyView.class, RegisterView.class, RegisterPresenter.MyProxy.class);
		bindPresenter(PasswordRecoveryPresenter.class, PasswordRecoveryPresenter.MyView.class, PasswordRecoveryView.class, PasswordRecoveryPresenter.MyProxy.class);
		bindPresenter(UserGuideVideoPresenter.class, UserGuideVideoPresenter.MyView.class, UserGuideVideoView.class, UserGuideVideoPresenter.MyProxy.class);
		bindPresenter(UserGuidePresenter.class, UserGuidePresenter.MyView.class, UserGuideView.class, UserGuidePresenter.MyProxy.class);
		bindPresenter(TermsAndConditionsPresenter.class, TermsAndConditionsPresenter.MyView.class, TermsAndConditionsView.class, TermsAndConditionsPresenter.MyProxy.class);
		bindPresenter(ResponseUserSatisfactionPresenter.class, ResponseUserSatisfactionPresenter.MyView.class, ResponseUserSatisfactionView.class, ResponseUserSatisfactionPresenter.MyProxy.class);
		bindPresenterWidget(RequestSearchPresenter.class, RequestSearchPresenter.MyView.class, RequestSearchView.class);
	}
}
