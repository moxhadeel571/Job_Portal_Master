package com.example.job_portal_master.ServiceImpl;

import com.example.job_portal_master.Entity.CustomUser;
import com.example.job_portal_master.Entity.Role;
import com.example.job_portal_master.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserServiceImpl userService;

	@Autowired
	public CustomUserDetailsService(UserServiceImpl userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser customUser = userService.getUserByEmail(username);
		if (customUser == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// Get the roles of the user and convert them to SimpleGrantedAuthority
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Role role : customUser.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.name()));
		}

		// Create the UserDetails object with the user's email, password, and roles
		return new User(customUser.getEmail(), customUser.getPassword(), authorities);
	}
}
