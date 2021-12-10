package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.User;

public interface UserService {
	void save(User user);
	User findByUsername(String username);
	Iterable<User> getUsers();
	void hashPassword(User user);
	void addRoles(User user);
}
