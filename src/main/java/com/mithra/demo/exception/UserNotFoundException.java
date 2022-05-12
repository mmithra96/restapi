package com.mithra.demo.exception;

public class UserNotFoundException extends RuntimeException {
	
	/* Thrown if user does not exist in DB */
	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
