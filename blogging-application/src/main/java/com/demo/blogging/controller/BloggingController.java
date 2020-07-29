package com.demo.blogging.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogging.exception.EntityNotFoundException;
import com.demo.blogging.model.Article;
import com.demo.blogging.repository.BlogRepository;

@RestController
@RequestMapping("blogs")
public class BloggingController {
	
	private static Logger log = LoggerFactory.getLogger(BloggingController.class);

	@Autowired
	BlogRepository blogRepository;
	
	
	@PostMapping("/save")
	public ResponseEntity<Article> createBlog(@RequestBody Article article) {
		
		Article savedArticle = blogRepository.save(article);
		log.info(savedArticle.toString());
		return  new ResponseEntity<>(savedArticle,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Article> getBlogs(@PathVariable UUID id) {
		Optional<Article> article= blogRepository.findById(id);
		if(!article.isPresent()) {
			throw new EntityNotFoundException(id.toString());
		}
		
		return  new ResponseEntity<>(article.get(),HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Article>> getBlogs() {
		
		return  new ResponseEntity<>(blogRepository.findAll(),HttpStatus.OK);
	}
	
	
}
