package com.powerreviews.project.integration;

import java.util.Date;
import java.util.Map;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.google.common.base.Strings;
import com.powerreviews.project.persistence.ReviewEntity;
import reactor.core.publisher.Mono;

//TODO - Need to get POST integration tests working with Constraints

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PosttTests {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void whenPostGoodReview_thenCorrect() {
    ReviewEntity review = new ReviewEntity(1, 1, "I like their Burritos.", 5, new Date());

    this.webClient.post().uri("/restaurant")
    .contentType(MediaType.APPLICATION_JSON)
      .syncBody(review)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectHeader()
      .valueMatches("Location", ".*/restaurant/\\d+");
  }
  
//  @Test
//  public void whenPostReviewWithLongComment_thenFail() {
//    String largeComment = Strings.repeat("I like their Burritos. The portions are muy grande! ", 6);
//    
//    ReviewEntity review = new ReviewEntity(1, 1, largeComment, 5, new Date());
//    
//    this.webClient.post().uri("/restaurant")
//    .contentType(MediaType.APPLICATION_JSON)
//      .syncBody(review)
//      .exchange()
//      .expectStatus()
//      .is4xxClientError()
//      .expectBody()
//      .jsonPath("$.errors").isEqualTo("Comment must not exceed 200 characters.");
//  }
  
//  @Test
//  public void whenPostReviewWithOverMaxRating_thenFail() {
//    String comment = "I like their Burritos. The portions are muy grande!";
//    
//    ReviewEntity review = new ReviewEntity(1, 1, comment, 8, new Date());
//    
//    this.webClient.post().uri("/restaurant")
//    .contentType(MediaType.APPLICATION_JSON)
//      .syncBody(review)
//      .exchange()
//      .expectStatus()
//      .is4xxClientError()
//      .expectBody()
//      .jsonPath("$.errors").isEqualTo("Rating must be less than or equal to 5.");
//  }
}