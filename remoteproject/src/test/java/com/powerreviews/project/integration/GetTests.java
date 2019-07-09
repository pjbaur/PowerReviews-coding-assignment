package com.powerreviews.project.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GetTests {

	@Autowired
	private WebTestClient webClient;
	
	@Test
	public void whenGetRequestAllRestaurants_thenCorrect() {
		this.webClient.get().uri("/restaurant").exchange()
		.expectStatus().isOk();
	}
	
	@Test
	public void whenGetRequestRestaurantByType_thenCorrect() {
		this.webClient.get().uri("/restaurant/type/Tacos").exchange()
		.expectStatus().isOk();
	}
	
	@Test
	public void whenGetRequestRestaurantById_thenCorrect() {
		this.webClient.get().uri("/restaurant/1").exchange()
		.expectStatus().isOk();
	}
	
	@Test
	public void whenGetRequestReviewsForARestaurant_thenCorrect() {
		this.webClient.get().uri("/reviews/restaurant/1").exchange()
		.expectStatus().isOk();
	}
	
	
}
