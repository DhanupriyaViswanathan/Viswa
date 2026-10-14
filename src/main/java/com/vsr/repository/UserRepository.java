package com.vsr.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vsr.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, BigDecimal> {
	 // Get all users
    List<User> findAll();

    // Get user by ID
    Optional<User> findById(BigDecimal userId);

    // Check whether user exists
    boolean existsById(BigDecimal userId);

    // Save / update user
    @SuppressWarnings("unchecked")
	User save(User user);

    // Delete user by ID
    void deleteById(BigDecimal userId);

    // Delete a user
    void delete(User user);

    // Delete all users
    void deleteAll();

    // Count users
    long count();

    // Find users by first name
    List<User> findByFirstName(String firstName);

    // Find users by last name
    List<User> findByLastName(String lastName);

    // Find users by email
    Optional<User> findByEmail(String email);

    // Find users by role
    List<User> findByRole(String role);

    // Find users by access
    List<User> findByUserAccess(String userAccess);

    // Check whether email exists
    boolean existsByEmail(String email);

    // Find by first name and last name
    List<User> findByFirstNameAndLastName(
            String firstName,
            String lastName
    );
}
