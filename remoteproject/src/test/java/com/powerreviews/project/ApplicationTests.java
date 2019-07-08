package com.powerreviews.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.powerreviews.project.client.RestaurantServiceClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {



	@Autowired
	private RestaurantServiceClient client;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void contextLoads() {
	}

	
}