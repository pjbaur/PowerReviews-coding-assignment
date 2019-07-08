package com.powerreviews.project.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.powerreviews.project.exception.validator.Comment;

/**
 * A review contains user information, comments, a rating and a date.
 * 
 * @author paulbaur
 *
 */
@Entity(name = "review")
public class ReviewEntity extends ResourceSupport {
	// TODO - implement isolated (table-specific) IDs
	@Id
	@GeneratedValue
	private Integer reviewId;

	@Column
	private Integer userId;

	@Column
	private Integer restaurantId;

	@Column
	@Comment
	@Size(max = 200, message = "Comment must not exceed 200 characters.")
	private String comment;

	@Column
	@Min(value = 1, message = "Rating must be greater than or equal to 1.")
	@Max(value = 5, message = "Rating must be less than or equal to 5.")
	private Integer rating;

	@Column
	@JsonFormat(shape =  JsonFormat.Shape.STRING )
	private Date timestamp;

	public ReviewEntity() {
		super();
	}

	public ReviewEntity(Integer userId, Integer restaurantId, String comment, Integer rating, Date timestamp) {
		super();
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.comment = comment;
		this.rating = rating;
		this.timestamp = timestamp;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
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
		return String.format("Review [reviewId=%s, restaurantId=%s, comment=%s, rating=%s, timestamp=%s]", reviewId,
				restaurantId, comment, rating, timestamp);
	}
}