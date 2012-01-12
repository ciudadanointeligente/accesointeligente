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

import org.accesointeligente.client.AnonymousGatekeeper;
import org.accesointeligente.client.PlaceHistory;
import org.accesointeligente.client.UserGatekeeper;
import org.accesointeligente.client.presenters.*;
import org.accesointeligente.client.services.*;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;

import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({PresenterModule.class, ServiceModule.class})
public interface AppInjector extends Ginjector {
	EventBus getEventBus();
	PlaceManager getPlaceManager();
	UserGatekeeper getUserGatekeeper();
	AnonymousGatekeeper getAnonymousGatekeeper();
	PlaceHistory getPlaceHistory();
	Provider<MainPresenter> getMainPresenter();
	AsyncProvider<HomePresenter> getHomePresenter();
	AsyncProvider<RequestPresenter> getRequestPresenter();
	AsyncProvider<RequestStatusPresenter> getRequestStatusPresenter();
	AsyncProvider<RequestEditPresenter> getRequestEditPresenter();
	AsyncProvider<RequestResponsePresenter> getRequestResponsePresenter();
	AsyncProvider<RequestListPresenter> getRequestListPresenter();
	AsyncProvider<StatisticsPresenter> getStatisticsPresenter();
	AsyncProvider<AboutProjectPresenter> getAboutProjectPresenter();
	AsyncProvider<UserProfileEditPresenter> getUserProfileEditPresenter();
	AsyncProvider<ContactPresenter> getContactPresenter();
	AsyncProvider<LoginPresenter> getLoginPresenter();
	AsyncProvider<RegisterPresenter> getRegisterPresenter();
	AsyncProvider<PasswordRecoveryPresenter> getPasswordRecoveryPresenter();
	AsyncProvider<UserGuideVideoPresenter> getUserGuideVideoPresenter();
	AsyncProvider<UserGuidePresenter> getUserGuidePresenter();
	AsyncProvider<TermsAndConditionsPresenter> getTermsAndConditionsPresenter();
	AsyncProvider<ResponseUserSatisfactionPresenter> getResponseUserSatisfactionPresenter();
	ActivityServiceAsync getActivityService();
	AgeServiceAsync getAgeService();
	InstitutionServiceAsync getInstitutionService();
	InstitutionTypeServiceAsync getInstitutionTypeService();
	RegionServiceAsync getRegionService();
	SessionServiceAsync getSessionService();
	UserServiceAsync getUserService();
	RequestServiceAsync getRequestService();
	ContactServiceAsync getContactService();
}
