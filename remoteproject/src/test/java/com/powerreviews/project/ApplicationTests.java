package com.powerreviews.project;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import com.powerreviews.project.client.RestaurantServiceClient;
import com.powerreviews.project.persistence.RestaurantRepository;

import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.Dns.Companion;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	private static final String APPLICATION_PORT = "8080";

	@Autowired
	private RestaurantServiceClient client;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void contextLoads() {
	}

	private static final String BASE_URL = "http://127.0.0.1:" + APPLICATION_PORT;

//	OkHttpClient client;

	@Before
	public void init() {
		client = new OkHttpClient();
	}

	@Test
	public void whenGetRequest_thenCorrect() throws IOException {
		final Request request = new Request.Builder().url(BASE_URL + "/restaurant").build();

		final Call call = client.newCall(request);
		final Response response = call.execute();

		assertThat(response.code(), equalTo(200));
	}
}