/**
 * 
 */
package com.thwet.number_queue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 14, 2021
 */
@Entity
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	Long id;

	@Column(name = "Email")
	private String email;

	@Column(name = "Password")
	private String password;
}
