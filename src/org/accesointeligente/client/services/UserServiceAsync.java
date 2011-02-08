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
