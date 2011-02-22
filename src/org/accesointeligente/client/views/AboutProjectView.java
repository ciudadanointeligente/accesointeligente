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

import org.accesointeligente.client.presenters.AboutProjectPresenter;
import org.accesointeligente.client.presenters.AboutProjectPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AboutProjectView extends Composite implements AboutProjectPresenter.Display {
	private static AboutProjectViewUiBinder uiBinder = GWT.create(AboutProjectViewUiBinder.class);
	interface AboutProjectViewUiBinder extends UiBinder<Widget, AboutProjectView> {}

	AboutProjectPresenterIface presenter;

	public AboutProjectView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AboutProjectPresenterIface presenter) {
		this.presenter = presenter;
	}
}
