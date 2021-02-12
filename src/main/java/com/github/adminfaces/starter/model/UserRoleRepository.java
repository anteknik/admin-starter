package com.github.adminfaces.starter.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	List<UserRole> findAllByUserLoginOrderByRole_IdAsc(UserLogin userLogin);
	List<UserRole> findAllByUserLoginOrderByRole_IndexAsc(UserLogin userLogin);
	
	void deleteByUserLogin(UserLogin userLogin);
	void deleteByUserLoginAndRole_Parent(UserLogin userLogin, Role parent);
	
	List<UserRole> findAllByUserLogin(UserLogin userLogin);
	
	List<UserRole> findAllByRole_ParentIsNull();
	
	UserRole findByUserLoginAndRole(UserLogin userLogin, Role role);
}
