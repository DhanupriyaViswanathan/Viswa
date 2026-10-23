package com.vsr.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vsr.composite.User;
import com.vsr.factory.UserFactory;
import com.vsr.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public List<User> findAll(){
		return UserFactory.toCompositeList(userRepository.findAll());
	}
	
	public User findByEmail(String email) {
		return UserFactory.toComposite(userRepository.findByEmail(email).get());
	}
	
	public User save(User user) {
        return UserFactory.toComposite(userRepository.save(UserFactory.toEntity(user)));
    }

}
