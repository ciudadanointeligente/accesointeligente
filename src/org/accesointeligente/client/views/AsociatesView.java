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

import org.accesointeligente.client.presenters.AsociatesPresenter;
import org.accesointeligente.client.presenters.AsociatesPresenterIface;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AsociatesView extends Composite implements AsociatesPresenter.Display {
	private static AsociatesViewUiBinder uiBinder = GWT.create(AsociatesViewUiBinder.class);
	interface AsociatesViewUiBinder extends UiBinder<Widget, AsociatesView> {}

	AsociatesPresenterIface presenter;

	public AsociatesView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(AsociatesPresenterIface presenter) {
		this.presenter = presenter;
	}
}
