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
