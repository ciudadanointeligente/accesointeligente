package cl.votainteligente.accesointeligente.client.services;

import cl.votainteligente.accesointeligente.model.User;
import cl.votainteligente.accesointeligente.shared.LoginException;
import cl.votainteligente.accesointeligente.shared.RegisterException;
import cl.votainteligente.accesointeligente.shared.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	User login(String username, String password) throws LoginException, ServiceException;
	User register(User user) throws RegisterException, ServiceException;
}
