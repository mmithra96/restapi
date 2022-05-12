package com.mithra.demo.respository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.exception.UserNotLoggedException;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Repository
public class UserDbRepositoryImpl implements UserDbRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserDbRepositoryImpl.class);

	@Autowired
	private DynamoDbEnhancedClient dynamoDbEnhancedClient;
  
	/* getTable returns the Table "User" from DyanmoDB */

	private DynamoDbTable<User> getTable() {
	
		logger.info("User Repository - Retrieving Table");
		DynamoDbTable<User> userTable = dynamoDbEnhancedClient.table("User", TableSchema.fromBean(User.class));
		return userTable;
	}

	/*Encrypts the Plain-text password using Base64 before they are stored in DB*/
	private String passwordEncrption(User user) {
		logger.info("User Repository -  Encrpting Password");

		String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
		return encodedPassword;
	}

	/*saveUser saves the User Objects to DB, if the user does not exist*/
	@Override
	public User saveUser(User user) {
		logger.info("User Repository - Saving User in DB : [{}]", user.getEmailId());

		DynamoDbTable<User> userTable = getTable();
		user.setPassword(passwordEncrption(user)); //Password encrypted
		user.setLastLoginDate(""); // LoginDate is empty when creating User
		user.setIsUserLoggedIn(false); // IsUserLogin is set to false on creation
		try {
			Optional<User> existing = findByKey(user); //Using Optional as the result could be empty or null
			if (!existing.isPresent()) {
				userTable.putItem(user); //Item inserted in DB
			} else {
				throw new UserServiceException("User already exists");

			}
		} catch (DynamoDbException e) {
			logger.error("User Repository - User Creation - Exception : [{}]", e.getMessage());

			throw new UserServiceException("User Repository - User Creation - DBException :" + e.getMessage());

		}
		return findByKey(user).orElseThrow(() -> new UserServiceException("Error In Inserting New User in DB"));

	}

	/*saveUser deleted the User Objects to DB, if the user exists*/
	@Override
	public void deleteUser(String emailId) throws UserNotFoundException {
		logger.info("User Repository - Deleting User in DB : " + emailId);
		try {
			DynamoDbTable<User> userTable = getTable();
			
           //retrieves the Key(object) using the partition key "emailID"
			Optional<Key> existing = Optional.ofNullable(Key.builder().partitionValue(emailId).build()); 
			if (userTable.getItem(existing.get()) != null) {
				DeleteItemEnhancedRequest deleteRequest = DeleteItemEnhancedRequest.builder().key(existing.get())
						.build();
				userTable.deleteItem(deleteRequest);
			} else {
				logger.info("User does not exist");
				throw new UserNotFoundException("User does not exist");
			}

		} catch (DynamoDbException e) {
			logger.error("User Repository - User Deletion - Exception : [{}]", e.getMessage());
			throw new UserServiceException("User Repository - User Deletion - DBException :" + e.getMessage());
		}

	}

	/*	 Lists the User from DyanmoDB*/
	@Override
	public List<User> getUsers() {
		logger.info("User Repository -Get Users List from DB");

		List<User> resultSet = new ArrayList<User>();
		try {
			// Create a DynamoDbTable object
			DynamoDbTable<User> userTable = getTable();
			//Scans reads the entire table, Can use page Iterator in case of large data.
			Iterator<User> results = userTable.scan().items().iterator();
			results.forEachRemaining(resultSet::add);

		} catch (DynamoDbException e) {
			throw new UserServiceException("User Repository - Get Users List - DBException :" + e.getMessage());
		}
		return resultSet;
	}

	//Updates the User in DyanamoDB
	public void updateUserItem(User user) {
		DynamoDbTable<User> userTable = getTable();
		userTable.updateItem(user);
	}
	
	// Checks if the user exists, If yes updates User, else throws Error
	public String updateUser(User user) {
		logger.info("User Repository - User Updation: [{}]", user.getEmailId());
		String message = "User Updated Successfully";
		try {

			Optional<User> existing = findByKey(user);
			if (existing.isPresent()) {
				if (existing.get().getIsUserLoggedIn()) {
					user.setPassword(passwordEncrption(user));
					// always set to true, since by default flag is false,this may turn false even if user logs in
					user.setIsUserLoggedIn(true); 
					updateUserItem(user);
				} else {
					message = "User not logged In.Please login to Update";
					throw new UserNotLoggedException(message);
				}
			} else {
				message = "User does not Exist";
				throw new UserNotFoundException(message);
			}
		}

		catch (DynamoDbException e) {
			logger.error("User Repository - User Updation - Exception : [{}]", e.getMessage());

			throw new UserServiceException("User Repository - User Updation - DBException :" + e.getMessage());
		}
		return message;
	}

	//Returns User if user exists
	public Optional<User> findByKey(User user) {
		logger.info("User Repository - Find User : [{}]", user.getEmailId());

		try {
			DynamoDbTable<User> userTable = getTable();

			Key key = userTable.keyFrom(user);// An object that represents a key that can be used to identify a specific
			// record

			return Optional.ofNullable(userTable.getItem(key));
		} catch (Exception e) {
			throw new UserNotFoundException("Error Finding User on DB");

		}

	}
  
	//logoutUser() sets IsUserLoggedIn Flag to false when logging out
	public String logoutUser(User user) {
		logger.info("User Repository - Logout User : [{}]", user.getEmailId());
		Optional<User> existing = findByKey(user);
		String message = "User Logged out Successfully";
		if (existing.isPresent()) {
			User exist = existing.get();
			exist.setIsUserLoggedIn(false);
			updateUserItem(exist);
		} else {
			message = "User does not Exist";
		}

		return message;

	}

}
