package org.accesointeligente.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

public class CustomSimplePager extends SimplePager {

	public interface CustomSimplePagerMessages extends Messages {
		@Key("page.of")
		public String pageOf();

		@Key("page.ofover")
		public String pageOfOver();
	}

	CustomSimplePagerMessages messages;

	public CustomSimplePager(SimplePager.Resources pagerResources) {
		super(TextLocation.CENTER, pagerResources, false, 0, true);
		messages = GWT.create(CustomSimplePagerMessages.class);
	}

	@Override
	public void setPageStart(int index) {
		if (getDisplay() != null) {
			Range range = getDisplay().getVisibleRange();
			int pageSize = range.getLength();
			if (!isRangeLimited() && getDisplay().isRowCountExact()) {
				index = Math.min(index, getDisplay().getRowCount() - pageSize);
			}
			index = Math.max(0, index);
			if (index != range.getStart()) {
				getDisplay().setVisibleRange(index, pageSize);
			}
		}
	}

	@Override
	protected String createText() {
	    NumberFormat formatter = NumberFormat.getFormat("#,###");
	    HasRows display = getDisplay();
	    Range range = display.getVisibleRange();
	    int pageStart = range.getStart() + 1;
	    int pageSize = range.getLength();
	    int dataSize = display.getRowCount();
	    int endIndex = Math.min(dataSize, pageStart + pageSize - 1);
	    endIndex = Math.max(pageStart, endIndex);
	    boolean exact = display.isRowCountExact();
	    return formatter.format(pageStart) + "-" + formatter.format(endIndex)
	        + (exact ? " " + messages.pageOf() + " " : " " + messages.pageOfOver() + " ") + formatter.format(dataSize);
	}
}
