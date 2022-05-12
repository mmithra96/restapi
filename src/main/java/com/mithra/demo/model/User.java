package com.mithra.demo.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;

@DynamoDbBean // Class level annotation that identifies "User" class as being a DynamoDb mappable entity.
@NoArgsConstructor // inserts an empty Constructor
@AllArgsConstructor // inserts an all parameter constructor
public class User {
	

	@NotEmpty(message = "Please provide a name") // Assures the Create,login,Update, logout endpoints needs UserName 
	private String userName; 
	@NotEmpty(message = "Please provide a valid mail address") // Assures the Create,login,Update, delete, logout endpoints needs emailId
	@Email // Simple validation of "@" and "." in emailId
	private String emailId;
	@NotEmpty(message = "Please enter your password") // Assures the Create,login,Update, logout endpoints needs password
	private String password;
	private String lastLoginDate;
	private Boolean isUserLoggedIn = false;
   
    @DynamoDbAttribute(value ="userName")  // "userName" -> "userName" in dynamoDb
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@DynamoDbPartitionKey
	@DynamoDbAttribute(value ="emailId")  // "emailId" -> "emailId" in dynamoDb and its the partition(primary) key
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@DynamoDbAttribute(value ="password")  // "password" -> "password" in dynamoDb
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@DynamoDbAttribute(value ="lastLoginDate")  // "lastLoginDate" -> "lastLoginDate" in dynamoDb
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	@DynamoDbAttribute(value ="isUserLoggedIn")  // "isUserLoggedIn" -> "isUserLoggedIn" in dynamoDb, flag to track if user is logged in
	public Boolean getIsUserLoggedIn() {
		return isUserLoggedIn;
	}
	public void setIsUserLoggedIn(Boolean isUserLoggedIn) {
		this.isUserLoggedIn = isUserLoggedIn;
	}

}
