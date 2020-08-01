package com.demo.blogging.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
public class BloggingIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	private static final String URL = "http://localhost:8080/blogs/tags/java";

	@Test
	public void testBloggingByTag() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get(URL))                
		.andExpect(status().is(200))
		.andExpect(jsonPath("$", hasSize(2)))

		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("e2755936-91e7-4a8a-be24-4ca02e8777de"));

	}

}
