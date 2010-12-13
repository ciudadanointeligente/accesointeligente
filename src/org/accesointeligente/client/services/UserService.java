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
}