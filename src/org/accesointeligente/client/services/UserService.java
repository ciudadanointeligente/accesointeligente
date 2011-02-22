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
import org.accesointeligente.shared.LoginException;
import org.accesointeligente.shared.RegisterException;
import org.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * UserService has methods for user registration and login
 *
 * @author rarcos@votainteligente.cl
 */
@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	/**
	 * Logs a user into the system
	 *
	 * @param email the user's provided email address
	 * @param password the user's provided password
	 * @throws LoginException if the user provided incorrect credentials
	 * @throws ServiceException if something went wrong with the service
	 */
	void login(String email, String password) throws LoginException, ServiceException;

	/**
	 * Registers a new user
	 *
	 * @param user the user to be registered
	 * @return the registered user
	 * @throws RegisterException if something went wrong with the registration process
	 * @throws ServiceException if something went wrong with the service
	 */
	User register(User user) throws RegisterException, ServiceException;

	/**
	 * Verifies if password belongs to user
	 *
	 * @param email the user's provided email address
	 * @param password the user's provided password
	 * @return the check result
	 * @throws LoginException if the user provided incorrect credentials
	 * @throws ServiceException if something went wrong with the service
	 */
	Boolean checkPass(String email, String password) throws LoginException, ServiceException;

	/**
	 * Updates the user information
	 *
	 * @param user the user to be updated
	 * @throws ServiceException if something went wrong with the service
	 */
	void updateUser(User user) throws ServiceException;

	/**
	 * Verifies if the email exist in the user database
	 *
	 * @param email the user's provided email address
	 * @return true if exists, false if not
	 * @throws ServiceException if something went wrong with the service
	 */
	Boolean checkEmailExistence(String email) throws ServiceException;

	/**
	 * Generates, encrypts and updates a random password for the user
	 *
	 * @param email the user's provided email address
	 * @throws ServiceException
	 */
	void resetPassword(String email) throws ServiceException;
}