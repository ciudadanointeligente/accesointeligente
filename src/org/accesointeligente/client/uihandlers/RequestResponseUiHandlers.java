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
package org.accesointeligente.client.uihandlers;

import org.accesointeligente.client.widgets.ResponseWidget;
import org.accesointeligente.model.Request;
import org.accesointeligente.model.Response;
import org.accesointeligente.shared.NotificationEventType;

import com.google.gwt.user.client.ui.FlowPanel;

import com.gwtplatform.mvp.client.UiHandlers;

public interface RequestResponseUiHandlers extends UiHandlers {
	void showRequest(Integer requestId);
	void loadComments(Request request);
	void saveComment(String commentContent);
	void loadAttachments(Response response, ResponseWidget widget);
	void saveQualification(Integer rate);
	void getUserResponse(Response response, ResponseWidget widget);
	void saveUserResponse(String information, Response response, ResponseWidget widget);
	void loadBestVotedRequests();
	void updateResponse(Response response, FlowPanel userSatisfactionPanel, FlowPanel requestStatusPanel);
	void goBack();
	void gotoLogin();
	void showNotification(String message, NotificationEventType type);
}
