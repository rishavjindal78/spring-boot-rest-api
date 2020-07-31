package com.demo.blogging.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blogging.model.Article;

public interface BloggingRepository extends JpaRepository<Article,UUID> {
	
	 Optional<Article> findById(UUID id);
	 
	 List<Article> findAllByCreatedBy(String createdBy, Pageable pageable);


}
