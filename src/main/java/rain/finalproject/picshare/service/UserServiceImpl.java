package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.User;
import rain.finalproject.picshare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public void hashPassword(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	}

	@Override
	public void addRoles(User user) {
		user.setRoles(new HashSet<>(roleRepository.findAll()));
	}
}
