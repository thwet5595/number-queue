/**
 * 
 */
package com.thwet.number_queue.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 13, 2021
 */
@Getter
@Setter
public class QueueDto {
	public static int front;
	public static int rear;
	public static int capacity;
	public static int size;
	public static int queue[];

	public QueueDto(int size) {
		front = rear = 0;
		capacity = size;
		queue = new int[capacity];
	}
}
