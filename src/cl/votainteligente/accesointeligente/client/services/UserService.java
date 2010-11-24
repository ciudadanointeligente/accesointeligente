package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.User;
import cl.votainteligente.accesointeligente.shared.LoginException;
import cl.votainteligente.accesointeligente.shared.RegisterException;
import cl.votainteligente.accesointeligente.shared.ServiceException;

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
	 * @param username the user's provided username
	 * @param password the user's provided password
	 * @return the user that logged in
	 * @throws LoginException if the user provided incorrect credentials
	 * @throws ServiceException if something went wrong with the service
	 */
	User login(String username, String password) throws LoginException, ServiceException;

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