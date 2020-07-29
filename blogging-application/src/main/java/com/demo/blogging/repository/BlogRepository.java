package com.demo.blogging.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blogging.model.Article;

public interface BlogRepository extends JpaRepository<Article,UUID> {
	
	 Optional<Article> findById(UUID id);

}
