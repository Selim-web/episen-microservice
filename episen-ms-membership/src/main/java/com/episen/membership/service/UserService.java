package com.episen.membership.service;

import com.episen.membership.model.User;
import com.episen.membership.repository.UserRepository;
import com.episen.membership.security.JwTokenGenerator;
import com.episen.membership.setting.InfraSettings;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.Claims;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class UserService {

	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JwTokenGenerator jwTokenGenerator;


	public void add(User user) {
		
		if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getEmail())) {
			throw new RuntimeException("User exception");
		}
		
		if(repository.getUserByUsername(user.getUsername()) != null) {
			throw new RuntimeException("User already exist");
		}
		
		repository.add(user);
	}
	
	
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


	public String authenticate(User user) {

		String jwtToken = "";
		User currentUser = getByUsername(user.getUsername());
		if(BCrypt.checkpw(user.getPassword(),currentUser.getPassword())){
			System.out.println("user authenticate");
			jwtToken = jwTokenGenerator.generateToken(currentUser.getUsername(), currentUser.getRoles());
		}
		else {
			System.out.println("user not authenticate");
		}
		return jwtToken;
	}

	public boolean isAdmin(String token) throws ParseException {

		JWTClaimsSet jwtClaimsSet = getClaimsSet(token);
		List<String> authUserRoles = (List<String>) jwtClaimsSet.getClaim("ROLES");

		return authUserRoles.contains("ADMIN");
	}

	private Date getExpirationDateFromToken(String token) throws ParseException {

		JWTClaimsSet jwtClaimsSet = getClaimsSet(token);
		Date date = (Date) jwtClaimsSet.getClaim("exp");
		return date;
	}

	public Boolean isTokenExpired(String token) throws ParseException {

		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private JWTClaimsSet getClaimsSet(String jwtToken) throws ParseException {

		JWSObject plainObject = JWSObject.parse(jwtToken);
		return JWTClaimsSet.parse(plainObject.getPayload().toJSONObject());
	}


	
	
}
