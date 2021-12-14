/**
 * 
 */
package com.thwet.number_queue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thwet.number_queue.dto.QueueDto;
import com.thwet.number_queue.model.Queue;
import com.thwet.number_queue.repo.QueueRepository;
import com.thwet.number_queue.service.QueueService;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 11, 2021
 */
@Service
public class QueueServiceImpl implements QueueService {

	@Autowired
	private QueueRepository queueRepository;

	QueueDto qDto = new QueueDto(10);

	@Override
	public void initialQueue() {
		// TODO Auto-generated method stub
		this.queueEnqueue(1);
		this.queueEnqueue(2);
		this.queueEnqueue(3);
		this.queueEnqueue(4);
		this.queueEnqueue(5);
		this.queueEnqueue(6);
		this.queueEnqueue(7);
		this.queueEnqueue(8);
		this.queueEnqueue(9);
		this.queueEnqueue(10);
	}

	@Override
	public void save(Queue queue) {
		this.queueRepository.save(queue);
	}

	@Override
	public void queueEnqueue(int item) {

		// check if the queue is full
		if (qDto.capacity == qDto.rear) {
			System.out.println("Queue is full");
			return;
		}

		// insert element at the rear
		else {
			qDto.queue[qDto.rear] = item;
			qDto.rear++;
		}
		Queue objQueue = new Queue();
		objQueue.setCapacity(qDto.capacity);
		objQueue.setFront(qDto.front);
		objQueue.setRear(qDto.rear);
		objQueue.setSize(qDto.size);
		objQueue.setValue(item);
		this.queueRepository.save(objQueue);
		return;
	}

	@Override
	public void queueDequeue() {
		// TODO Auto-generated method stub
		// check if queue is empty
		if (qDto.front == qDto.rear) {
			System.out.printf("\nQueue is empty\n");
			return;
		}

		// shift elements to the right by one place uptil rear
		else {
			for (int i = 0; i < qDto.rear - 1; i++) {
				qDto.queue[i] = qDto.queue[i + 1];
			}

			// set queue[rear] to 0
			if (qDto.rear < qDto.capacity)
				qDto.queue[qDto.rear] = 0;

			// decrement rear
			qDto.rear--;
		}
		return;
	}

	@Override
	public String queueDisplay() {
		int i;
		String result = " ";

		if (qDto.front == qDto.rear) {
			System.out.println("Queue is Empty\n");
		}

		for (i = qDto.rear - 1; i >= qDto.front; i--) {
			System.out.print(qDto.queue[i] + " ");
			result = result + qDto.queue[i] + " ";
		}

		return result;
	}

	@Override
	public List<Queue> findLastValue() {
		return queueRepository.findAllByOrderByValueDesc();
	}
}
