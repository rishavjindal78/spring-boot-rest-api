package com.demo.blogging.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.blogging.dto.ArticleDto;
import com.demo.blogging.model.Article;
import com.demo.blogging.service.BloggingService;

@Component
public class BloggingMapper {
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private BloggingService service;
	
    public ArticleDto convertToDto(Article article) {
    	ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);
        return articleDto;
    }
    
    public List<ArticleDto> convertToDto(List<Article> article) {
    	return article.stream().map(entity -> convertToDto(entity)).collect(Collectors.toList());
    }
	
    public Article convertToEntity(ArticleDto articleDto) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);

    	Article article = modelMapper.map(articleDto, Article.class);
    	
         return article;
    }

}
