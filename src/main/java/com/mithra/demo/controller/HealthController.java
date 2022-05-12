package com.mithra.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/*
	 * heathCheck() allows us to test the health of the api. This is mainly used
	 * when the api is hosted in the CloudStack for the target health checks.
	 */
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public ResponseEntity<?> heathCheck() {
		logger.info("HealthController - heathCheck");
		return new ResponseEntity<Object>("Health is good", HttpStatus.OK);
	}

}
