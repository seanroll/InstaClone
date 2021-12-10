package rain.finalproject.picshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public String findLoggedInUsername() {
		Object Details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (Details instanceof UserDetails ? ((UserDetails)Details).getUsername() : null);
	}

	@Override
	public void autoLogin(String username, String password) {
		UserDetails Details = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken AuthenticationToken = new UsernamePasswordAuthenticationToken(Details, password, Details.getAuthorities());

		authenticationManager.authenticate(AuthenticationToken);

		if (AuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(AuthenticationToken);
		}
	}
}
