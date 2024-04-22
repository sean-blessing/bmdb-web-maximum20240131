package com.bmdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.UserRepo;
import com.bmdb.model.User;
import com.bmdb.model.UserLogin;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/login")
	public User login(@RequestBody UserLogin ul) {
		User user = userRepo.findByEmailAndPassword(ul.getEmail(), ul.getPassword());
		if (user == null) {
			System.err.println("Email or password not found");
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Email or password not found");
		}
		return user;
	}

}
