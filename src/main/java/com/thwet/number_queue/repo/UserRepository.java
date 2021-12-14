/**
 * 
 */
package com.thwet.number_queue.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thwet.number_queue.model.User;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 14, 2021
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
