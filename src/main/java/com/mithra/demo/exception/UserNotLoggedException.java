package com.mithra.demo.exception;

public class UserNotLoggedException extends RuntimeException {

	/* Thrown if user is not logged in for Update Operations */

	public UserNotLoggedException() {
	}

	public UserNotLoggedException(String message) {
		super(message);
	}

}
