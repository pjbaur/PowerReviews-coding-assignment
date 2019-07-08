package com.powerreviews.project.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestaurantServiceClientIntegrationTest {

	@Autowired
	private RestaurantServiceClient client;
	
	@Autowired
	private MockRestServiceServer server;
	
	@Autowired
	private ObjectMapper objectMapper;
}
