package com.powerreviews.project.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.powerreviews.project.controller.ReviewController;

/**
 * A review contains user information, comments, a rating and a date.
 * 
 * @author paulbaur
 *
 */
@Entity(name = "review")
public class Review {
	@Id
	private Integer id;

//	@Column
//	private UserEntity user;
	
//	@ManyToOne(fetch=FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name="restaurant")
//	@JsonIgnore
	private Restaurant restaurant;

	@Column
	private String comment;

	@Column
	private Integer rating;

	@Column
	private Date timestamp;

	public Review() {
		super();
	}
	
	public Review(Integer id, Integer restaurantId, String comment, Integer rating, Date timestamp) {
		super();
		this.id = id;
		this.restaurant = restaurant;
		this.comment = comment;
		this.rating = rating;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return String.format("Review [id=%s, restaurant=%s, comment=%s, rating=%s, timestamp=%s]", id, restaurant,
				comment, rating, timestamp);
	}
	

}
