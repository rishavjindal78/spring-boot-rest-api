package com.demo.blogging.repository;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.demo.blogging.model.Article;


@DataJpaTest
public class BloggingRepositoryTest {
	
	@Autowired
	private BloggingRepository bloggingRepository;
	
	@Test
	public void shouldGetByAll() {
        Iterable<Article> data = bloggingRepository.findAll();
        MatcherAssert.assertThat(Lists.newArrayList(data).size(), Matchers.equalTo(0));
	}
	
	@Test
	public void shouldGetById() {
		UUID id = UUID.fromString("0e7721ce-b284-49d5-aae3-9d5c7e28475a");

        Optional<Article> data = bloggingRepository.findById(id);
        MatcherAssert.assertThat(data.isPresent(), Matchers.equalTo(false));
	}

}
