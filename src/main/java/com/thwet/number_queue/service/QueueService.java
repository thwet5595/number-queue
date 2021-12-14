/**
 * 
 */
package com.thwet.number_queue.service;

import java.util.List;

import com.thwet.number_queue.model.Queue;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 11, 2021
 */
public interface QueueService {
	public void initialQueue();

	public void save(Queue queue);

	public void queueEnqueue(int item);

	public void queueDequeue();

	public String queueDisplay();

	public List<Queue> findLastValue();
}
