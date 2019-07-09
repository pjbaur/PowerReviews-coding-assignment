package com.powerreviews.project.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Integer>{
    @Query("SELECT r from restaurant r where r.type = ?1")
    List<RestaurantEntity> findByType(String type);
    
    List<RestaurantEntity> findAll();
}