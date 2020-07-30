package com.demo.blogging.exception;

import java.util.UUID;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6027858965776059959L;
	
	
	public UserNotFoundException() {
		super("UserNotFoundForThisArticle::");
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
