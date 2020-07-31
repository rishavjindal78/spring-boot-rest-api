package com.demo.blogging.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.blogging.dto.ArticleDto;
import com.demo.blogging.model.Article;
import com.demo.blogging.service.BloggingService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = BloggingController.class)
public class BlogginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean(name="bloggingService")
	private BloggingService bloggingService;

	@InjectMocks
	private BloggingController bloggingController;

	LocalDateTime currentTime = LocalDateTime.now();
	ArticleDto dto;
	Article article;
	UUID id;

	@BeforeEach
	public void setup() {

		id = UUID.fromString("0e7721ce-b284-49d5-aae3-9d5c7e28475a");
		dto = new ArticleDto();

		dto.setId(id);
		dto.setTitle("How to learn Spring Booot");
		dto.setBody("You have to believe");

		dto.setDescription("Ever wonder how");

		article = new Article();

		article.setId(id);
		article.setTitle("How to learn Spring Booot");
		article.setBody("You have to believe");

		article.setDescription("Ever wonder how");
		article.setCreatedBy("user");
	}

	@Test
    public void shouldSaveTheBlogWithCode201() throws Exception{

        doReturn(dto).when(bloggingService).add(ArgumentMatchers.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/blogs/save").header("createdBy" , "rishav")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(article)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

        		.andExpect(jsonPath("$.title", is("How to learn Spring Booot")))
        		.andExpect(jsonPath("$.body", is("You have to believe")))

        		.andExpect(jsonPath("$.description", is("Ever wonder how")));
    }

	@Test
	public void shouldReturnTheBlogById() throws Exception {

		Mockito.when(bloggingService.getArticleDtoById(id)).thenReturn(Optional.of(dto));

		mockMvc.perform(MockMvcRequestBuilders.get("/blogs/"+id))
		.andExpect(status().is(200))
		.andExpect(jsonPath("$.title", is("How to learn Spring Booot")))
		.andExpect(jsonPath("$.body", is("You have to believe")))

		.andExpect(jsonPath("$.description", is("Ever wonder how")));

	}


	@Test
	public void shouldReturnIfEntityNotFoundHttpStatusCode400() throws Exception {
		Mockito.when(bloggingService.getArticleDtoById(id)).thenReturn(Optional.ofNullable(null));

		mockMvc.perform(MockMvcRequestBuilders.get("/blogs/"+id))
		.andExpect(status().is(404))
		.andExpect(jsonPath("$.message", is("BloggingNotFoundWithID:: 0e7721ce-b284-49d5-aae3-9d5c7e28475a")))
		.andExpect(jsonPath("$.details", is("uri=/blogs/0e7721ce-b284-49d5-aae3-9d5c7e28475a")));
	}

	@Test
	public void shouldNotDeleteBlogsWithoutHeaderHttpStatusCode400() throws Exception {
		Mockito.when(bloggingService.delete(id)).thenReturn("success");

		mockMvc.perform(MockMvcRequestBuilders.delete("/blogs/"+id))
		.andExpect(status().is(400));
	}

	@Test
	public void shouldNotDeleteBlogWithWrongHeaderHttpStatusCode400() throws Exception {
		Mockito.when(bloggingService.delete(id)).thenReturn("success");

		mockMvc.perform(MockMvcRequestBuilders.delete("/blogs/"+id).header("createdBy" , "vivek"))
		.andExpect(status().is(404))
		.andExpect(jsonPath("$.message", is("BloggingNotFoundWithID:: 0e7721ce-b284-49d5-aae3-9d5c7e28475a")))
		.andExpect(jsonPath("$.details", is("uri=/blogs/0e7721ce-b284-49d5-aae3-9d5c7e28475a")));	
	}

}
