package com.powerreviews.project.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.powerreviews.project.persistence.RestaurantEntity;

@Service
public class RestaurantServiceClient {
	 private final RestTemplate restTemplate;

	    public RestaurantServiceClient(RestTemplateBuilder restTemplateBuilder) {
	        restTemplate = restTemplateBuilder.build();
	    }

	    public RestaurantEntity getRestaurantDetails(Integer restaurantId) {
	        return restTemplate.getForObject("/restaurant/{restaurantId}", RestaurantEntity.class, restaurantId);
	    }
}