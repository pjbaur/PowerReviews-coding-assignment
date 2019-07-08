package com.powerreviews.project.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.ReviewEntity;
import com.powerreviews.project.persistence.ReviewRepository;

@RestController
@RequestMapping(value = "/restaurant")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class RestaurantController {

	final static String APPLICATION_HAL_JSON_VALUE = "application/hal+json";

	org.slf4j.Logger logger = LoggerFactory.getLogger(RestaurantController.class);

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	ReviewRepository reviewRepository;

	private List<Link> getReviewLinks(final RestaurantEntity restaurant) {
		ArrayList<Link> links = new ArrayList<>();
		Integer restaurantId = restaurant.getRestaurantId();
		if (reviewRepository.getAllReviewsForRestaurant(restaurantId).size() > 0) {
			List<ReviewEntity> reviews = reviewRepository.getAllReviewsForRestaurant(restaurantId);
			for (ReviewEntity review : reviews) {
				final Link reviewLink = linkTo(
						methodOn(ReviewController.class).getReviewById(review.getReviewId()))
						.withRel("reviews");
				logger.info("Review link: {}", reviewLink.getHref());
				links.add(reviewLink);
			}
		}
		return links;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = APPLICATION_HAL_JSON_VALUE)
	public Resources<RestaurantEntity> getAllRestaurants() {
		final List<RestaurantEntity> allRestaurants = restaurantRepository.findAll();

		for (final RestaurantEntity restaurant : allRestaurants) {
			Integer restaurantId = restaurant.getRestaurantId();

			Link selfLink = linkTo(RestaurantController.class).slash(restaurantId).withSelfRel();
			restaurant.add(selfLink);

			List<Link> links = getReviewLinks(restaurant);
			restaurant.add(links);
		}

		Resources<RestaurantEntity> resources = new Resources<>(allRestaurants);
		return resources;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<RestaurantEntity> findById(@PathVariable Integer id) {
		RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
		//TODO Need to handle null restaurant
		
		Resource<RestaurantEntity> resource = new Resource<>(restaurant);
		resource.add(linkTo(methodOn(this.getClass()).findById(id)).withSelfRel());
		List<Link> links = getReviewLinks(restaurant);
		resource.add(links);
		return resource;
	}

	@RequestMapping(value = "/{restaurantId}/{reviewId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReviewEntity getReviewById(@PathVariable final Integer restaurantId, @PathVariable final Integer reviewId) {
		return reviewRepository.findById(reviewId).orElse(null);
	}

	@RequestMapping(value = "/{restaurantId}/reviews", method = RequestMethod.GET, produces = APPLICATION_HAL_JSON_VALUE)
	public Resources<ReviewEntity> getReviewsForRestaurant(@PathVariable final Integer restaurantId) {
		final List<ReviewEntity> reviews = reviewRepository.getAllReviewsForRestaurant(restaurantId);

		for (final ReviewEntity review : reviews) {
			Link selfLink = linkTo(
					methodOn(RestaurantController.class).getReviewById(restaurantId, review.getReviewId()))
					.withSelfRel();
			review.add(selfLink);
		}

		Link link = linkTo(methodOn(RestaurantController.class).getReviewsForRestaurant(restaurantId)).withSelfRel();
		Resources<ReviewEntity> resources = new Resources<>(reviews, link);
		return resources;
	}

	@RequestMapping(value = "/type/{type}", method = RequestMethod.GET, produces = APPLICATION_HAL_JSON_VALUE)
	public Resources<RestaurantEntity> getRestaurantsByType(@PathVariable String type) {
		Collection<RestaurantEntity> restaurants = restaurantRepository.findByType(type);

		for (final RestaurantEntity restaurant : restaurants) {
			Integer restaurantId = restaurant.getRestaurantId();
			Link selfLink = linkTo(RestaurantController.class).slash(restaurantId).withSelfRel();
			restaurant.add(selfLink);

			List<Link> links = getReviewLinks(restaurant);
			restaurant.add(links);
		}

		Resources<RestaurantEntity> resources = new Resources<RestaurantEntity>(restaurants);
		return resources;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postNewRestaurant(@RequestBody RestaurantEntity restaurant) {
		RestaurantEntity savedRestaurant = restaurantRepository.save(restaurant);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedRestaurant.getRestaurantId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantEntity> put(@RequestBody RestaurantEntity restaurant) {
		RestaurantEntity updated = restaurantRepository.findById(restaurant.getRestaurantId()).orElse(null);
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

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantEntity> delete(@PathVariable Integer id) {
		restaurantRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
