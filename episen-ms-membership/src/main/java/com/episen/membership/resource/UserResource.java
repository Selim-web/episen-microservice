package com.episen.membership.resource;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.episen.membership.model.User;
import com.episen.membership.service.UserService;

@RestController
@RequestMapping(value = "users", produces = {"application/json"})
public class UserResource {

	@Autowired
	private UserService userService;
	
	@PostMapping("auth")
	public ResponseEntity<String> authenticate(@RequestBody User user){

		return new ResponseEntity<String>(userService.authenticate(user), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

		String[] bearerToken = token.split("Bearer ");
		String jwtToken = bearerToken[1];
		if(!userService.isTokenExpired(jwtToken) && userService.isAdmin(jwtToken)){
			return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
		}
		return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);

	}
	
	// .../users/rkarra
	@GetMapping("{username}")
	public ResponseEntity<User> getOne(@PathVariable("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

		String[] bearerToken = token.split("Bearer ");
		String jwtToken = bearerToken[1];
		if(!userService.isTokenExpired(jwtToken) && userService.isAdmin(jwtToken)){
			return new ResponseEntity<User>(userService.getByUsername(username), HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@PostMapping
	public ResponseEntity<User> add(@RequestBody User user, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

		String[] bearerToken = token.split("Bearer ");
		String jwtToken = bearerToken[1];
		if(!userService.isTokenExpired(jwtToken) && userService.isAdmin(jwtToken)){
			userService.add(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping
	public ResponseEntity<User> put(@RequestBody User user, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

		String[] bearerToken = token.split("Bearer ");
		String jwtToken = bearerToken[1];
		if(!userService.isTokenExpired(jwtToken) && userService.isAdmin(jwtToken)){
			userService.update(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@DeleteMapping("{username}")
	public ResponseEntity<User> delete(@PathVariable("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws ParseException {

		String[] bearerToken = token.split("Bearer ");
		String jwtToken = bearerToken[1];
		if(!userService.isTokenExpired(jwtToken) && userService.isAdmin(jwtToken)){
			userService.delete(username);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}

}
