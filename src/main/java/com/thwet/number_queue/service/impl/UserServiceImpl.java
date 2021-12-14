/**
 * 
 */
package com.thwet.number_queue.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.thwet.number_queue.model.User;
import com.thwet.number_queue.repo.UserRepository;
import com.thwet.number_queue.service.UserService;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 14, 2021
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		this.userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return this.userRepository.findByEmail(email);
	}

	@Override
	public Authentication authenticate(String username, String password) throws Exception {
		Authentication auth = null;
		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {
			throw new DisabledException(e.getMessage());
		} catch (BadCredentialsException bce) {
			throw new BadCredentialsException("Incorrect Email or Password!");
		} catch (LockedException e) {
			// TODO: this user is locked! && Don't delete
			throw new LockedException(e.getMessage());
		}
		return auth;
	}

}
