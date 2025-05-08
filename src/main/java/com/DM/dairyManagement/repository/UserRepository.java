package com.DM.dairyManagement.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DM.dairyManagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 // Find user by email only
	Optional<User> findByEmail(String email);





}

