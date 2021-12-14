/**
 * 
 */
package com.thwet.number_queue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 11, 2021
 */
@Entity
@Getter
@Setter
public class Queue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	Long id;

	@Column(name = "Front")
	int front;

	@Column(name = "Rear")
	int rear;

	@Column(name = "Capacity")
	int capacity;

	@Column(name = "Size")
	int size;

	@Column(name = "Value")
	int value;
}
