package com.demo.blogging.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogging.dto.ArticleDto;
import com.demo.blogging.exception.EntityNotFoundException;
import com.demo.blogging.exception.UserNotFoundException;
import com.demo.blogging.model.Article;
import com.demo.blogging.service.BloggingService;

@RestController
@RequestMapping("blogs")
public class BloggingController {

	private static Logger log = LoggerFactory.getLogger(BloggingController.class);

	private static final String DELETE_ERROR = "User can only delete articles that they created";
	private static final String UPDATE_ERROR = "User can only update articles that they created";

	@Autowired
	BloggingService bloggingService;


	@PostMapping("/save")
	public ResponseEntity<ArticleDto> createBlog(@Valid @RequestBody Article article,@RequestHeader("createdBy") String createdBy) {

		if(createdBy.isEmpty()) {
			throw new UserNotFoundException();
		}
		article.setCreatedBy(createdBy);;
		ArticleDto savedArticle = bloggingService.add(article);
		log.info(savedArticle.toString());
		return  new ResponseEntity<>(savedArticle,HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ArticleDto> getBlogs(@PathVariable UUID id) {
		Optional<ArticleDto> articleDto= bloggingService.getArticleDtoById(id);
		if(!articleDto.isPresent()) {
			throw new EntityNotFoundException(id.toString());
		}
		return  new ResponseEntity<>(articleDto.get(),HttpStatus.OK);
	}
	
	@GetMapping("/all/{user}")
	public ResponseEntity<List<ArticleDto>> getBlogs(@PathVariable String user,Pageable pageable) {
		List<ArticleDto> dtoList = bloggingService.findAllByUser(user,pageable);
		if(dtoList.size()==0) {
			throw new EntityNotFoundException();
		}
		return  new ResponseEntity<>(dtoList,HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<ArticleDto>> getBlogs(Pageable pageable) {
		List<ArticleDto> dtoList = bloggingService.findAll(pageable);
		if(dtoList.size()==0) {
			throw new EntityNotFoundException();
		}
		return  new ResponseEntity<>(dtoList,HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Article> updateBlog(@PathVariable UUID id, @RequestBody ArticleDto articleDto, @RequestHeader("createdBy") String createdBy) {

		log.info("Entered into updateBlog::"+id );
		Optional<Article> articleOptional= bloggingService.getArticleById(id);
		if(!articleOptional.isPresent()) {
			throw new EntityNotFoundException(id.toString());
		}

		if(!bloggingService.userCheck(articleOptional.get(), createdBy)) {
			throw new UserNotFoundException(UPDATE_ERROR);
		}

		log.info("Id isPresent::"+id );

		articleDto.setId(id);
		Article updatedArticle = bloggingService.updateBlog(articleDto);

		return new ResponseEntity<>(updatedArticle,HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteBlogs(@PathVariable UUID id,@RequestHeader("createdBy") String createdBy) {

		Article article = bloggingService.getArticleById(id)
				.orElseThrow(() -> new EntityNotFoundException(id.toString()));

		if(!bloggingService.userCheck(article, createdBy)) {
			throw new UserNotFoundException(DELETE_ERROR);
		}
		bloggingService.delete(id);
		Map<String, Boolean> response = new HashMap<>();

		response.put("deleted", Boolean.TRUE);

		return new ResponseEntity<>(response,HttpStatus.OK);

	}

}
