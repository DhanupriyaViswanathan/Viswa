package com.vsr.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vsr.composite.User;
import com.vsr.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController{

	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

	@GetMapping("/{id}")
    public User getUser(@PathVariable BigDecimal id) {
        return userService.findById(id);
    }

	@PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }
}
