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
package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.UserGuideVideoPresenter;
import org.accesointeligente.client.uihandlers.UserGuideVideoUiHandlers;

import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.media.client.Video;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

import javax.inject.Inject;

public class UserGuideVideoView extends PopupViewWithUiHandlers<UserGuideVideoUiHandlers> implements UserGuideVideoPresenter.MyView {
	private static UserGuideVideoViewUiBinder uiBinder = GWT.create(UserGuideVideoViewUiBinder.class);
	interface UserGuideVideoViewUiBinder extends UiBinder<Widget, UserGuideVideoView> {}
	private final Widget widget;

	@UiField FocusPanel mainPanel;
	@UiField HTMLPanel videoPanel;
	@UiField Label close;

	private Video userGuideVideo;

	@Inject
	protected UserGuideVideoView(EventBus eventBus) {
		super(eventBus);
		widget = uiBinder.createAndBindUi(this);
		setAutoHideOnNavigationEventEnabled(true);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setVideo(String url) {
		videoPanel.clear();
		userGuideVideo = Video.createIfSupported();
		userGuideVideo.setAutoplay(true);
		userGuideVideo.setControls(true);
		userGuideVideo.setSrc(url);
		userGuideVideo.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
					getUiHandlers().close();
				}
			}
		});

		videoPanel.add(userGuideVideo);
	}

	@UiHandler("mainPanel")
	void onLoginPanelKeyDown(KeyDownEvent key) {
		if (key.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
			getUiHandlers().close();
		}
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent click) {
		getUiHandlers().close();
	}
}
