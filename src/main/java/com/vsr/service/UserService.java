package com.vsr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vsr.entity.User;
import com.vsr.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
	
	public User save(User user) {
        return userRepository.save(user);
    }

}
