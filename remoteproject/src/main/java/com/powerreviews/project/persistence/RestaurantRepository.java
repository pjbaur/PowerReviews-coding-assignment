package com.powerreviews.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer>{
    @Query("SELECT MAX(id) FROM restaurant")
    int maxId();
    
    List<Restaurant> findAll();
    
    List<Restaurant> findByType(String type);
}