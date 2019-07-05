package com.powerreviews.project.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.powerreviews.project.persistence.Restaurant;
import com.powerreviews.project.persistence.RestaurantRepository;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class RestaurantController {
    
	@Autowired
	private RestaurantRepository restaurantRepository;

    @GetMapping("/restaurant")
    public List<Restaurant> retrieveAllRestaurants() {
    	return restaurantRepository.findAll();
    }
    
    @GetMapping(value = "/restaurant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Restaurant> retrieveRestaurantById(@PathVariable Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        
        Resource<Restaurant> resource = new Resource<Restaurant>(restaurant);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveRestaurantById(id));
        
        resource.add(linkTo.withSelfRel());
        return resource;
    }

    @RequestMapping(value = "/restaurant/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resources<Restaurant> get(@PathVariable String type) {
        Collection<Restaurant> restaurants = restaurantRepository.findByType(type);
        
        Link link = linkTo(RestaurantController.class).withSelfRel();
        Resources<Restaurant> resources = new Resources<Restaurant>(restaurants);
        
        return resources; 
    }
    
    @PostMapping(value = "/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody Restaurant restaurant) {
        int id = restaurantRepository.maxId() + 1;
        restaurant.setId(id);
        
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        
        URI location = ServletUriComponentsBuilder
        	.fromCurrentRequest()
        	.path("/{id}")
        	.buildAndExpand(savedRestaurant.getId())
        	.toUri();
        
        return ResponseEntity.created(location).build();
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> put(@RequestBody Restaurant restaurant) {
        Restaurant updated = restaurantRepository.findById(restaurant.getId()).orElse(null);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updated.setLatitude(restaurant.getLatitude());
        updated.setLongitude(restaurant.getLongitude());
        updated.setName(restaurant.getName());
        updated.setType(restaurant.getType());
        restaurantRepository.save(updated);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/restaurant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> delete(@PathVariable Integer id) {
         restaurantRepository.deleteById(id);
    	
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
