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
package org.accesointeligente.client.presenters;

import org.accesointeligente.client.uihandlers.UserGuideVideoUiHandlers;
import org.accesointeligente.shared.AppPlace;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;

import javax.inject.Inject;

public class UserGuideVideoPresenter extends Presenter<UserGuideVideoPresenter.MyView, UserGuideVideoPresenter.MyProxy> implements UserGuideVideoUiHandlers {
	public interface MyView extends PopupView, HasUiHandlers<UserGuideVideoUiHandlers> {
		void setVideo(String url);
	}

	@ProxyCodeSplit
	@NameToken(AppPlace.USERGUIDEVIDEO)
	public interface MyProxy extends ProxyPlace<UserGuideVideoPresenter> {
	}

	private static final String USERGUIDEVIDEO_MP4 = "video/userguide.mp4";
	private static final String USERGUIDEVIDEO_WEBM = "video/userguide.webm";

	@Inject
	private PlaceManager placeManager;

	@Inject
	public UserGuideVideoPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	public void onReveal() {
		String userAgent = Navigator.getUserAgent().toLowerCase();

		if (userAgent.contains("msie")) {
			getView().setVideo(USERGUIDEVIDEO_MP4);
		} else if (userAgent.contains("chrome")) {
			getView().setVideo(USERGUIDEVIDEO_WEBM);
		} else if (userAgent.contains("chromium")) {
			getView().setVideo(USERGUIDEVIDEO_WEBM);
		} else if (userAgent.contains("safari")) {
			getView().setVideo(USERGUIDEVIDEO_MP4);
		} else if (userAgent.contains("opera")) {
			getView().setVideo(USERGUIDEVIDEO_WEBM);
		} else if (userAgent.contains("gecko")) {
			getView().setVideo(USERGUIDEVIDEO_WEBM);
		} else {
			getView().setVideo(USERGUIDEVIDEO_WEBM);
		}

		Window.setTitle("Guía de uso - Video - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealRootPopupContentEvent(this));
	}

	@Override
	public void close() {
		placeManager.navigateBack();
	}
}
