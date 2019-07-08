package com.powerreviews.project.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.powerreviews.project.persistence.ReviewEntity;
import com.powerreviews.project.persistence.ReviewRepository;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

	final static String APPLICATION_HAL_JSON_VALUE = "application/hal+json";
	
	@Autowired
	private ReviewRepository reviewRepository;

	@RequestMapping(method = RequestMethod.GET, produces = APPLICATION_HAL_JSON_VALUE)
	public Resources<ReviewEntity> getAllReviews() {
		final List<ReviewEntity> reviews = (List<ReviewEntity>) reviewRepository.findAll(new Sort(Sort.Direction.DESC, "timestamp"));
		
		for (final ReviewEntity review : reviews) {
			Integer reviewId = review.getReviewId();
			Link selfLink = linkTo(methodOn(ReviewController.class).getReviewById(reviewId)).withRel("/reviews");

			review.add(selfLink);
		}
		
		Link link = linkTo(ReviewController.class).withSelfRel();
		Resources<ReviewEntity> resources = new Resources<>(reviews, link);
		return resources;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<ReviewEntity> getReviewById(@PathVariable Integer id) {
		ReviewEntity review = reviewRepository.findById(id).orElse(null) ;
		
		Resource<ReviewEntity> resource = new Resource<ReviewEntity>(review);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getReviewById(id));
		
		resource.add(linkTo.withSelfRel());
		return resource;
	}
	
    @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewEntity>> getAllReviewsForRestaurant(@PathVariable Integer restaurantId) {
        List<ReviewEntity> reviews = reviewRepository.findByAndSort(restaurantId, new Sort(Sort.Direction.DESC, "timestamp"));
        return new ResponseEntity<List<ReviewEntity>>(reviews, new HttpHeaders(), reviews == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
    
	@PostMapping(value = "", produces = APPLICATION_HAL_JSON_VALUE)
	public ResponseEntity<Object> post(@Valid @RequestBody ReviewEntity review) {
		ReviewEntity savedReview = reviewRepository.save(review);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedReview.getReviewId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
