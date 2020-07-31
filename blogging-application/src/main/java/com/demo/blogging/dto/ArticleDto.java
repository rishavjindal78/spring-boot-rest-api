package com.demo.blogging.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ArticleDto {

	private UUID id;

	private String title;
	private String description;

	private String body;

	private List<String> tags;

	private boolean favorited;

	private int favoritesCount;

	private Timestamp createdAt;

	private Timestamp updatedAt;

}
