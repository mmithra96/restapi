package com.mithra.demo.exception;

public class UserServiceException extends RuntimeException {

	/* Thrown for any DB exceptions etc .*/
	public UserServiceException() {
	}

	public UserServiceException(String message) {
		super(message);
	}

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
