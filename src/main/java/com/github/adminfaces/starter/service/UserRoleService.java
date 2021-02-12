package com.github.adminfaces.starter.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.model.UserRole;
import com.github.adminfaces.starter.model.UserRoleRepository;

@Service
@Transactional
public class UserRoleService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7599440152719663805L;

	@Autowired
	private UserRoleRepository userRoleRepository;

	public UserRole findById(Long id) {
		return userRoleRepository.getOne(id);
	}
	
	public List<UserRole> findByAll() {
		return userRoleRepository.findAll();
	}

	public void delete(UserRole userRole) {
		userRoleRepository.delete(userRole);		
	}
	
	public void deleteAllRolesByUser(UserLogin userLogin) {
		userRoleRepository.deleteByUserLogin(userLogin);		
	}

	public void deleteByUserLoginAndRoleParent(UserLogin userLogin, Role parent) {
		userRoleRepository.deleteByUserLoginAndRole_Parent(userLogin, parent);
	};
	
	public UserRole save(UserRole userrole) {
		return userRoleRepository.save(userrole);
	}
	
	public List<UserRole> findAllByUserLogin(UserLogin userLogin) {
		return userRoleRepository.findAllByUserLoginOrderByRole_IdAsc(userLogin);
	}
	
	public List<UserRole> findAllByUserLoginOrderByRoleIndex(UserLogin userLogin) {
		return userRoleRepository.findAllByUserLoginOrderByRole_IndexAsc(userLogin);
	}
	
	
	public List<UserRole> findAllParent() {
		return userRoleRepository.findAllByRole_ParentIsNull();
	}
	
	public UserRole findByUserLoginAndRole(UserLogin userLogin, Role role) {
		return userRoleRepository.findByUserLoginAndRole(userLogin, role);
	}
	
}