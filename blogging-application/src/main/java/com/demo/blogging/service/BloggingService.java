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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.blogging.dto.ArticleDto;
import com.demo.blogging.mapper.BloggingMapper;
import com.demo.blogging.model.Article;
import com.demo.blogging.repository.BlogRepository;

@Service
public class BloggingService {

	@Autowired
	BlogRepository blogRepository;
	
	
	private BloggingMapper mapper;

	public Article add(Article article) {

		return blogRepository.save(article);
	}
	
	public Page<Article> findAll(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}


	public Optional<Article> getArticleById(UUID id) {

		return blogRepository.findById(id);
	}

	public void delete(Article article) {
		
		blogRepository.deleteById(article.getId());
	}

	public Article updateBlog(ArticleDto articleDto) {
		mapper = new BloggingMapper();
		Article article = blogRepository.findById(articleDto.getId()).get();
		
		copyNonNullProperties(articleDto, article);
		
		return blogRepository.save(article);
	}
	
	public static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
	
	public static String[] getNullPropertyNames (Object source) {
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
