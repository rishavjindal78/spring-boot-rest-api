package com.demo.blogging.exception;

public class EntityNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 5046811321869316495L;

	public EntityNotFoundException(String entityName) {
		super("BloggingNotSupportedWithID:: "+entityName);
	}

}
