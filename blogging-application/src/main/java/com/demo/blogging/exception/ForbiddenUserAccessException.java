package com.demo.blogging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenUserAccessException extends RuntimeException{
	
	private static final long serialVersionUID = 8641911735720458076L;

	public ForbiddenUserAccessException(String message) {
		super(message);
	}

}
