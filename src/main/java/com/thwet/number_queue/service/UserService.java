/**
 * 
 */
package com.thwet.number_queue.service;

import org.springframework.security.core.Authentication;

import com.thwet.number_queue.model.User;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 14, 2021
 */
public interface UserService {

	void save(User user);

	User findByEmail(String email);

	Authentication authenticate(String username, String password) throws Exception;
}
