package com.demo.blogging.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogging.dto.ArticleDto;
import com.demo.blogging.exception.EntityNotFoundException;
import com.demo.blogging.model.Article;
import com.demo.blogging.service.BloggingService;

@RestController
@RequestMapping("blogs")
public class BloggingController {
	
	private static Logger log = LoggerFactory.getLogger(BloggingController.class);

	@Autowired
	BloggingService bloggingService;
	
	
	@PostMapping("/save")
	public ResponseEntity<Article> createBlog(@RequestBody Article article) {
		
		Article savedArticle = bloggingService.add(article);
		log.info(savedArticle.toString());
		return  new ResponseEntity<>(savedArticle,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Article> getBlogs(@PathVariable UUID id) {
		Optional<Article> article= bloggingService.getArticleById(id);
		if(!article.isPresent()) {
			throw new EntityNotFoundException(id.toString());
		}
		
		return  new ResponseEntity<>(article.get(),HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Article>> getBlogs() {
		
		return  new ResponseEntity<>(bloggingService.findAll(),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Article> updateBlog(@PathVariable UUID id, @RequestBody ArticleDto articleDto) {
		
		log.info("Entered into updateBlog::"+id );
		Optional<Article> articleOptional= bloggingService.getArticleById(id);
		if(!articleOptional.isPresent()) {
			throw new EntityNotFoundException(id.toString());
		}
		
		log.info("Id isPresent::"+id );

		articleDto.setId(id);
		Article updatedArticle = bloggingService.updateBlog(articleDto);
		
		return new ResponseEntity<>(updatedArticle,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteBlogs(@PathVariable UUID id) {
		
		Article article = bloggingService.getArticleById(id)
					.orElseThrow(() -> new EntityNotFoundException(id.toString()));
		
		bloggingService.delete(article);
	     Map<String, Boolean> response = new HashMap<>();

	    response.put("deleted", Boolean.TRUE);

        return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	
}
