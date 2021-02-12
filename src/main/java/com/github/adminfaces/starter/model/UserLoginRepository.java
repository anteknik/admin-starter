package com.github.adminfaces.starter.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
	UserLogin findByUsername(String username);
}

