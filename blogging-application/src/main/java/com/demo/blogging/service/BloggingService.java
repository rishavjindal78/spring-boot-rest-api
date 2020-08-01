package com.demo.blogging.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.blogging.dto.ArticleDto;
import com.demo.blogging.mapper.BloggingMapper;
import com.demo.blogging.model.Article;
import com.demo.blogging.repository.BloggingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BloggingService {

	private final BloggingRepository blogRepository;
	
	@Autowired
	BloggingMapper mapper;
	
	@Autowired
	public BloggingService(BloggingRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	public ArticleDto add(Article article) {

		return mapper.convertToDto(blogRepository.save(article));
	}
	
	public List<ArticleDto> findAll(Pageable pageable) {
		return mapper.convertToDto(blogRepository.findAll(pageable).getContent());
	}
	
	public List<ArticleDto> findAllByUser(String user, Pageable pageable) {
		List<Article> artileList = blogRepository.findAllByCreatedBy(user, pageable);
		return mapper.convertToDto(artileList);
	}

	
	public List<ArticleDto> findAllByTag(String tag) {
		return mapper.convertToDto(blogRepository.findAllByTags(tag));
		
	}

	public Optional<ArticleDto> getArticleDtoById(UUID id) {
		Optional<ArticleDto> articleDto = Optional.empty();
		if(blogRepository.findById(id).isPresent()) {
			articleDto = Optional.of(mapper.convertToDto(blogRepository.findById(id).get()));
		}
		return articleDto;
	}
	
	public Optional<Article> getArticleById(UUID id) {
		return blogRepository.findById(id);
	}

	public String delete(UUID id) {
		log.info("delete request by valid user::",id);
		blogRepository.deleteById(id);
		return "success";
	}

	public Article updateBlog(ArticleDto articleDto) {
		log.info("update request by valid user::",articleDto.getId());

		Article article = blogRepository.findById(articleDto.getId()).get();
		
		copyNonNullProperties(articleDto, article);
		
		return blogRepository.save(article);
	}
	
	public boolean userCheck(Article article,String createdBy) {
		
		return article.getCreatedBy().equalsIgnoreCase(createdBy);
	}
	
	private static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
	
	private static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
}
