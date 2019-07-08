package com.powerreviews.project;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IntegrationTests {
	private static final String APPLICATION_PORT = "8080";
	private static final String BASE_URL = "http://127.0.0.1:" + APPLICATION_PORT;

	OkHttpClient client;

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
