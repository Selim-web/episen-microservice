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
	public ResponseEntity<List<User>> getAll(){
		
		return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
		
	}
	
	// .../users/rkarra
	@GetMapping("{username}")
	public ResponseEntity<User> getOne(@PathVariable("username") String username) {
		
		return new ResponseEntity<User>(userService.getByUsername(username), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Object> add(@RequestBody User user, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

		String[] bearerToken = token.split("Bearer ");
		try {
			String jwtToken = bearerToken[1];
			boolean isAdmin = userService.isAdmin(jwtToken);
			if(isAdmin){
				userService.add(user);
				return new ResponseEntity<Object>(user, HttpStatus.CREATED);
			}
			else{
				return new ResponseEntity<Object>("Not admin user", HttpStatus.UNAUTHORIZED);
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	@PutMapping
	public void put(@RequestBody User user) {
		// TODO Bloquer la modification que par Admin comme add
		userService.update(user);
	}
	
	
	@DeleteMapping("{username}")
	public void delete(@PathVariable("username") String username) {
		// TODO Bloquer la suppression que par Admin comme add
		userService.delete(username);
	}

}
