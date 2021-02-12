package com.github.adminfaces.starter.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.RoleRepository;

@Service
@Transactional
public class RoleService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7599440152719663805L;

	@Autowired
	private RoleRepository roleRepository;

	public Role findById(Long id) {
		return roleRepository.findById(id).orElse(null);
	}
	
	public List<Role> findAll() {
		return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}	
	
	public List<Role> findAllByParent(Role parent) {
		return roleRepository.findAllByParentOrderByIdAsc(parent);
	}

	public List<Role> findAllChild() {
		return roleRepository.findAllByParentNotNullOrderByIdAsc();
	}

	public List<Role> findAllByParentOrderByIndexAsc(Role parent) {
		return roleRepository.findAllByParentOrderByIndexAsc(parent);
	}
	
	public List<Role> findAllChildOrderByIndexAsc() {
		return roleRepository.findAllByParentNotNullOrderByIndexAsc();
	}
	
	public void delete(Role role) {
		roleRepository.delete(role);		
	}

	@Transactional
	public void save(Role role) {
		roleRepository.save(role);		
	}
}