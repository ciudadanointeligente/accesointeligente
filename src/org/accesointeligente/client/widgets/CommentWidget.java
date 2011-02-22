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
package org.accesointeligente.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

public class CommentWidget extends Composite {
	private static CommentWidgetUiBinder uiBinder = GWT.create(CommentWidgetUiBinder.class);
	interface CommentWidgetUiBinder extends UiBinder<Widget, CommentWidget> {}

	@UiField Image commentImage;
	@UiField Label commentAuthor;
	@UiField Label commentDate;
	@UiField Label commentContent;

	public CommentWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setImage(String url) {
		commentImage.setUrl(url);
	}

	public void setAuthor(String author) {
		commentAuthor.setText(author);
	}

	public void setDate(Date date) {
		commentDate.setText(DateTimeFormat.getFormat("dd/MM/yyyy HH:mm").format(date));
	}

	public void setContent(String content) {
		commentContent.setText(content);
	}
}
