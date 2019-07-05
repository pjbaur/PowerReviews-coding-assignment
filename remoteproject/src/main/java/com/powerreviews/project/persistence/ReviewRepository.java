package com.powerreviews.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ReviewRepository extends CrudRepository<Review, Integer>{
    @Query("SELECT MAX(id) FROM review")
    int maxId();
	
    @Query("select r from review r where r.restaurant = ?1")
    List<Review> findByRestaurant(String restaurant);
    
    
}
