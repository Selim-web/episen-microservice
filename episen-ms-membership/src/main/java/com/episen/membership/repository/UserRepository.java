package com.episen.membership.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.episen.membership.model.User;

@Component
public class UserRepository {

	private Map<String, User> userInMemory = new HashMap<String, User>(); 
	
	
	public void add(User user) {
		System.out.println("adduser -> username : " + user.getUsername());
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		userInMemory.put(user.getUsername(), user);
	}
	
	public User getUserByUsername(String username) {
		System.out.println("Get user by name -> username : " + username);
		
		return userInMemory.get(username);
	}
	
	public List<User> getAll(){
		System.out.println("Get All -> count : " + userInMemory.size());
		
		return new ArrayList<>(userInMemory.values());
	}
	
	public void update(User userToUpdate) {
		
		userInMemory.put(userToUpdate.getUsername(), userToUpdate);
	}
	
	public void delete(String username) {
		
		userInMemory.remove(username);
	}
}
