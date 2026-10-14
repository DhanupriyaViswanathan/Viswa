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
	
	public User findById(BigDecimal id) {
		return UserFactory.toComposite(userRepository.findById(id).get());
	}
	
	public User save(User user) {
        return UserFactory.toComposite(userRepository.save(UserFactory.toEntity(user)));
    }

}
