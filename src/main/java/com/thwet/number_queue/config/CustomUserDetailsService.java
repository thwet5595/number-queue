/**
 * 
 */
package com.thwet.number_queue.config;

/**
 * @author Thwet Thwet Mar
 *
 * Dec 14, 2021
 */

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thwet.number_queue.model.User;
import com.thwet.number_queue.repo.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);

		LOGGER.info("User : " + user);
		if (user == null) {
			LOGGER.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true,
				true, true, getGrantedAuthorities(user));
	}

	public List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		/*
		 * String role = user.getRole();
		 * 
		 * if (role == null) { return Collections.emptyList(); }
		 */

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return authorities;
	}
}