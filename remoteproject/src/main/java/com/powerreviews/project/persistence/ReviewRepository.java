package com.powerreviews.project.persistence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer>{	
    @Query("SELECT r FROM review r WHERE r.restaurantId = ?1")
    List<ReviewEntity> findByAndSort(Integer restaurantId, Sort sort);

    @Query("SELECT r FROM review r WHERE r.restaurantId = ?1 ORDER BY timestamp DESC")
    List<ReviewEntity> getAllReviewsForRestaurant(Integer restaurantId);
    
    List<ReviewEntity> findAll();

	List<ReviewEntity> findAll(Sort sort);

	@Query("SELECT r FROM review r WHERE r.reviewId = ?1")
	ReviewEntity getReviewByIdForRestaurant(Integer restaurantId, Integer reviewId);
}