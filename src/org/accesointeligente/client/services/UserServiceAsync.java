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
package org.accesointeligente.client.services;

import org.accesointeligente.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	void login(String email, String password, AsyncCallback<Void> callback);
	void register(User user, AsyncCallback<User> callback);
	void checkPass(String email, String password, AsyncCallback<Boolean> callback);
	void updateUser(User user, AsyncCallback<Void> callback);
	void checkEmailExistence(String email, AsyncCallback<Boolean> callback);
	void resetPassword(String email, AsyncCallback<Void> callback);
}
