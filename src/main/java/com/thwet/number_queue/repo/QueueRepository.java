/**
 * 
 */
package com.thwet.number_queue.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thwet.number_queue.model.Queue;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 11, 2021
 */
@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
	public List<Queue> findAllByOrderByRearDesc();

	public List<Queue> findAllByOrderByValueDesc();
}
