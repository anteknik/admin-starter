package com.github.adminfaces.starter.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByName(String name);

	//find parent only if parent == null or childs only by specified parent
	public List<Role> findAllByParentOrderByIdAsc(Role parent);

	//find childs only
	public List<Role> findAllByParentNotNullOrderByIdAsc();

	//order by index purposed for ordered in link menu
	public List<Role> findAllByParentOrderByIndexAsc(Role parent);
	public List<Role> findAllByParentNotNullOrderByIndexAsc();

	
}