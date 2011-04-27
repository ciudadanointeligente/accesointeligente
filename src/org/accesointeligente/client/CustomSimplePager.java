package org.accesointeligente.client;

import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.Range;

public class CustomSimplePager extends SimplePager {

	public CustomSimplePager(SimplePager.Resources pagerResources) {
		super(TextLocation.CENTER, pagerResources, false, 0, true);
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

}
