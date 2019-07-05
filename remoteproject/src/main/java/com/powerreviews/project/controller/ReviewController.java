package com.powerreviews.project.controller;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.powerreviews.project.persistence.Restaurant;
import com.powerreviews.project.persistence.Review;
import com.powerreviews.project.persistence.ReviewRepository;

@RestController
public class ReviewController {

	@Autowired
	private ReviewRepository reviewRepository;

	@GetMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Review>> retrieveAllReviews() {
		List<Review> reviews = (List<Review>) reviewRepository.findAll();
		return new ResponseEntity<List<Review>>(reviews, new HttpHeaders(),
				reviews == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping(value = "/reviews/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Review> retrieveReviewById(@PathVariable Integer id) {
		Review review = reviewRepository.findById(id).orElse(null) ;
		
		Resource<Review> resource = new Resource<Review>(review);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveReviewById(id));
		
		resource.add(linkTo.withSelfRel());
		return resource;
	}
	
    @GetMapping(value = "/reviews/restaurant/{restaurant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Review>> get(@PathVariable String restaurant) {
        List<Review> reviews = reviewRepository.findByRestaurant(restaurant);
        return new ResponseEntity<List<Review>>(reviews, new HttpHeaders(), reviews == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
    @PostMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody Review review) {
        int id = reviewRepository.maxId() + 1;
        review.setId(id);
        
        Review savedReview = reviewRepository.save(review);
        
        URI location = ServletUriComponentsBuilder
        		.fromCurrentRequest()
        		.path("/{id}")
        		.buildAndExpand(savedReview.getId())
        		.toUri();
        
        return ResponseEntity.created(location).build();
    }
}
