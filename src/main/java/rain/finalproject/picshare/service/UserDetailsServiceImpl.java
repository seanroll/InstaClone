package rain.finalproject.picshare.service;

import rain.finalproject.picshare.model.Role;
import rain.finalproject.picshare.model.User;
import rain.finalproject.picshare.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User ExistingUser = userRepository.findByUsername(username);
		if (ExistingUser == null) {
			throw new UsernameNotFoundException(username);
		}

		Set<GrantedAuthority> GrantedAuthorities = new HashSet<>();
		for (Role UserRole : ExistingUser.getRoles()){
			GrantedAuthorities.add(new SimpleGrantedAuthority(UserRole.getName()));
		}

		return new org.springframework.security.core.userdetails.User(ExistingUser.getUsername(), ExistingUser.getPassword(), GrantedAuthorities);
	}
}