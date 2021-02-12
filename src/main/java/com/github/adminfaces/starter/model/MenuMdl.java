package com.github.adminfaces.starter.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class MenuMdl implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358265663989895L;
	
	private Long id;	
	private String name;
	private Long parentId;
	private String parentName;
	private String link;	
	private String icon;	
	private List<MenuMdl> submenus = new LinkedList<MenuMdl>();
	
	public MenuMdl(Long id, String name, Long parentId, String parentName, String link, String icon) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.parentName = parentName;		
		this.link = link;
		this.icon = icon;
	}	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public String getParentName() {
		return parentName;
	}


	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public List<MenuMdl> getSubmenus() {
		return submenus;
	}


	public void setSubmenus(List<MenuMdl> submenus) {
		this.submenus = submenus;
	}


	public void addSubmenu(MenuMdl menu) {
		this.submenus.add(menu);
	}
	
	public boolean isParent() {
		return this.parentId == null;
	}
	
	public boolean isChild() {
		return isParent() == false;
	}
}
