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
package org.accesointeligente.client.presenters;

import org.accesointeligente.client.uihandlers.TermsAndConditionsUiHandlers;
import org.accesointeligente.shared.AppPlace;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;

import javax.inject.Inject;

public class TermsAndConditionsPresenter extends Presenter<TermsAndConditionsPresenter.MyView, TermsAndConditionsPresenter.MyProxy> implements TermsAndConditionsUiHandlers {
	public interface MyView extends PopupView, HasUiHandlers<TermsAndConditionsUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(AppPlace.TERMSANDCONDITIONS)
	public interface MyProxy extends ProxyPlace<TermsAndConditionsPresenter> {
	}

	@Inject
	private PlaceManager placeManager;

	@Inject
	public TermsAndConditionsPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReset() {
		Window.setTitle("Términos y condiciones - Acceso Inteligente");
	}

	@Override
	protected void revealInParent() {
		fireEvent(new RevealRootPopupContentEvent(this));
	}

	@Override
	public void close() {
		placeManager.navigateBack();
	}
}
