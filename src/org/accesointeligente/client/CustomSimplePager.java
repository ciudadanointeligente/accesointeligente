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
