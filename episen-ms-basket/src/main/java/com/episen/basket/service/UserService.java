package com.episen.basket.service;

import com.episen.basket.model.User;
import com.episen.basket.repository.UserRepository;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class UserService {

	
	@Autowired
	private UserRepository repository;


	public User getByUsername(String username) {
		
		User user = repository.getUserByUsername(username);
		
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		
		return user;
		
	}
	
	public List<User> getAll(){
		return repository.getAll();
	}
	
	public void update(User userToUpdate) {
		
		if(null == repository.getUserByUsername(userToUpdate.getUsername())) {
			throw new RuntimeException("User not found");
		}
		
		repository.update(userToUpdate);
	}
	
	public void delete(String username) {
		
		if(null == repository.getUserByUsername(username)) {
			throw new RuntimeException("User not found");
		}
		
		repository.delete(username);
	}



	public boolean isAdmin(String token) throws ParseException {

		JWTClaimsSet jwtClaimsSet = getClaimsSet(token);
		List<String> authUserRoles = (List<String>) jwtClaimsSet.getClaim("ROLES");

		return authUserRoles.contains("ADMIN");
	}

	private JWTClaimsSet getClaimsSet(String jwtToken) throws ParseException {
		JWSObject plainObject = JWSObject.parse(jwtToken);
		return JWTClaimsSet.parse(plainObject.getPayload().toJSONObject());
	}


	
	
}
