package com.mithra.demo.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mithra.demo.model.User;
import com.mithra.demo.service.UserServiceImpl;


@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserServiceImpl userService;
	
	/*readUsers() allows us to fetch the users details from DataBase , with encrypted password.
	 *1.The method returns List of User objects.
	 *2. If there are no users it says "No Users Found".
	 *3.If the fetching process results in any DB exceptions then it says 
	 *"UserController - List Users - Exception : [{exception message}]"
	 *   */	
	@RequestMapping(value ="/Users",method=RequestMethod.GET)
	public ResponseEntity<Object> readUsers() throws Exception{
		logger.info("UserController - List Users");
		try {
			List<User> userList = userService.getUsers();
			if(null != userList && !userList.isEmpty()) {
				return new ResponseEntity<Object>(userList,HttpStatus.OK);	
			}
			
			return new ResponseEntity<Object>("No Users Found", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.info("UserController - List Users - Exception : [{}]",e.getMessage());
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	/*createUser() allows us to create and store user in DataBase. 
	 *1.This method does not require login(Can use spring security or bearer tokens to authorize this operation)
	 *2. This method returns "User Created Successfully.", If the user is inserted successfully to DB.
	 *3. This method returns "User Creation Failure : User already exists", If the user already exists and this is
	 *based on the logic that EmailId is the primary key in the  DB.
	 *4.If the fetching process results in any DB exceptions then it says 
	 * "UserController -  Create User - Exception : [{exception message}]"
	 *   */	
	@RequestMapping(value ="/createUser",method=RequestMethod.POST)
	public ResponseEntity<?> createUsers(@Valid @RequestBody User user){
		try {
			logger.info("UserController - Create User");
			userService.createUser(user);
			return new ResponseEntity<Object>("User Created Successfully.",HttpStatus.CREATED);
		} catch (Exception e) {
			logger.info("UserController - Create User - Exception : [{}]",e.getMessage());
			return new ResponseEntity<Object>("User Creation Failure : "+e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	/*updateUser() allows us to update and store user in DataBase. 
	 *1. This method requires login else its returns "User Update Failure : User not logged In.Please login to Update"
	 *2. This method returns "User Updated Successfully.", If the user is updated successfully to DB.
	 *3.This method returns "User Update Failure : User does not Exist",If the user doesnot exist.
	 *4.If the fetching process results in any DB exceptions then it says 
	 * "UserController -  Update User - Exception : [{exception message}]"
	 *   */	
	@RequestMapping(value ="/updateUser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user){
		try {
			logger.info("UserController - Update User");
		     	String message = userService.updateUser(user);
				return new ResponseEntity<Object>(message,HttpStatus.OK);		

		} catch (Exception e) {
			logger.info("UserController - Update User - Exception : [{}]",e.getMessage());
			return new ResponseEntity<Object>("User Update Failure : "+ e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	/*deleteUser() allows us to delete from DataBase. 
	 *1. This method  does not require login 
	 *2. This method returns "User Deleted Successfully.", If the user is deleted successfully from DB.
	 *3.This method returns "User Deletion Failure : User does not exist",If the mailId doesnot exist.
	 *4.If the fetching process results in any DB exceptions then it says 
	 * "UserController -  Delete User - Exception : [{exception message}]"
	 *   */	
	@RequestMapping(value ="/deleteUser/{emailId}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable(value="emailId") @NotBlank @Email String emailId){
		logger.info("UserController - Delete User");
		try {
			userService.deleteUser(emailId);
			return new ResponseEntity<Object>("User Deleted Successfully.",HttpStatus.OK);

		} catch (Exception e) {
			logger.info("UserController - Delete User - Exception : [{}]",e.getMessage());
			return new ResponseEntity<Object>("User Deletion Failure : "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	} 
}
