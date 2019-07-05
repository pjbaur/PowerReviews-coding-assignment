package com.powerreviews.project;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerreviews.project.persistence.Restaurant;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.ReviewRepository;
import com.powerreviews.project.persistence.UserEntity;
import com.powerreviews.project.persistence.UserRepository;

@SpringBootApplication()
//@EnableConfigServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(RestaurantRepository restaurantRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();

			TypeReference<List<Restaurant>> restaurantTypeReference = new TypeReference<List<Restaurant>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/restaurants.json");
			try {
				List<Restaurant> restaurants = mapper.readValue(inputStream, restaurantTypeReference);
				//save restaurants to the database
				restaurantRepository.saveAll(restaurants);
			} catch (IOException e){
				System.out.println("Unable to save restaurants: " + e.getMessage());
			}

			TypeReference<List<UserEntity>> userTypeReference = new TypeReference<List<UserEntity>>(){};
			inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
			try {
				List<UserEntity> users = mapper.readValue(inputStream, userTypeReference);
				//save users to the database
				userRepository.saveAll(users);
			} catch (IOException e){
				System.out.println("Unable to save users: " + e.getMessage());
			}
			
//			TypeReference<List<Review>> reviewTypeReference = new TypeReference<List<Review>>(){};
//			inputStream = TypeReference.class.getResourceAsStream("/json/reviews.json");
//			try {
//				List<Review> reviews = mapper.readValue(inputStream, reviewTypeReference);
//				//save users to the database
//				reviewRepository.saveAll(reviews);
//			} catch (IOException e){
//				System.out.println("Unable to save users: " + e.getMessage());
//			}
		};
	}
}
