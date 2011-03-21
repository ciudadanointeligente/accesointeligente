package org.accesointeligente.client.inject;

import org.accesointeligente.client.presenters.*;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(PresenterModule.class)
public interface PresenterInjector extends Ginjector {
	public EventBus getEventBus();
	public MainPresenter getMainPresenter();
	public HomePresenter getHomePresenter();
	public RequestPresenter getRequestPresenter();
	public RequestStatusPresenter getRequestStatusPresenter();
	public RequestEditPresenter getRequestEditPresenter();
	public RequestResponsePresenter getRequestResponsePresenter();
	public RequestListPresenter getRequestListPresenter();
	public RequestSearchPresenter getRequestSearchPresenter();
	public StatisticsPresenter getStatisticsPresenter();
	public AboutProjectPresenter getAboutProjectPresenter();
	public UserProfileEditPresenter getUserProfileEditPresenter();
	public ContactPresenter getContactPresenter();
	public LoginPresenter getLoginPresenter();
	public RegisterPresenter getRegisterPresenter();
	public PasswordRecoveryPresenter getPasswordRecoveryPresenter();
}
