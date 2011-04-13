package org.accesointeligente.client.views;

import org.accesointeligente.client.presenters.UserGuideVideoPresenter;
import org.accesointeligente.client.presenters.UserGuideVideoPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.media.client.Video;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

public class UserGuideVideoView extends Composite implements UserGuideVideoPresenter.Display {
	private static UserGuideVideoViewUiBinder uiBinder = GWT.create(UserGuideVideoViewUiBinder.class);
	interface UserGuideVideoViewUiBinder extends UiBinder<Widget, UserGuideVideoView> {}

	@UiField FocusPanel mainPanel;
	@UiField HTMLPanel videoPanel;
	@UiField Label close;

	private UserGuideVideoPresenterIface presenter;
	private Video userGuideVideo;

	public UserGuideVideoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public UserGuideVideoView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(UserGuideVideoPresenterIface presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setVideo(String url) {
		videoPanel.clear();
		userGuideVideo = new Video(url);
		userGuideVideo.getElement().setAttribute("autoplay", "autoplay");
		userGuideVideo.getElement().setAttribute("controls", "controls");
		userGuideVideo.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (presenter != null && event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
					presenter.close();
				}
			}
		});

		videoPanel.add(userGuideVideo);
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
