package com.mithra.demo.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mithra.demo.model.User;

@Repository
public interface UserDbRepository {

	public User saveUser(User user); // Saves User to DB

	public String updateUser(User user); // Validates and Updates User to DB

	public void updateUserItem(User user); // Updated User to DB

	public void deleteUser(String emailId); // Delete User from DB

	public List<User> getUsers(); // Lists User from DB

	public Optional<User> findByKey(User user); // Returns if the item exists in DB

}
