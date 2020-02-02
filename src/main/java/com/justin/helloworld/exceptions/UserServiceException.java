package com.justin.helloworld.exceptions;

public class UserServiceException extends RuntimeException {

	
	private static final long serialVersionUID = 7984122190903992557L;
	
	public UserServiceException(String message) {
		super(message);
	}
}
