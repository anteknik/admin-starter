package com.github.adminfaces.starter.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.model.MenuMdl;
import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.model.UserRole;
import com.github.adminfaces.starter.service.UserLoginService;
import com.github.adminfaces.starter.service.UserRoleService;

@ManagedBean
@ViewScoped
public class UserMenuMB {

	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private UserRoleService userRoleService;

	
    private List<UserRole> userRoleList = new ArrayList<>();
    
    private List<MenuMdl> menuList = new ArrayList<>();
    
    @PostConstruct
    public void init()
    {
    	UserLogin currentUser = userLoginService.findByUsername(getAuth().getName());    	
    	//userRoleList = userRoleService.findAllByUserLogin(currentUser);
    	userRoleList = userRoleService.findAllByUserLoginOrderByRoleIndex(currentUser);
    	menuList = createMenuList();
    }
    
    
    public Authentication getAuth() {
    	return SecurityContextHolder.getContext().getAuthentication();
    }


    public List<MenuMdl> createMenuList() {
    	List<MenuMdl> menuList = new LinkedList<MenuMdl>();
    	MenuMdl menu = null;
    	MenuMdl parentMenu = null;
    	Role role = null;
    	Role parent = null;
    	
    	for(UserRole item : userRoleList) {
    		role = item.getRole();    		
    		if ( role == null ) continue;
    		
    		//System.out.println("role-name:" + role.getName() + "=>parent:" + (role.getParent() != null ? role.getParent().getName() : "null"));
    		parent = role.getParent();
    		
    		menu = new MenuMdl(role.getId(), role.getName(), (parent != null ? parent.getId() : null), (parent != null ? parent.getName() : ""), role.getLink(), role.getIcon());
    		//add as parent
    		if ( menu.isParent() ) { 
    			//System.out.println("ADD-PARENT====>role:" + menu.getId() + "=>" + menu.getName() + "=>parent:" + menu.getParentName());        		
    			//add parent
    			menuList.add(menu);
    		}
    		else {
    			//System.out.println("FIND-PARENT=========>" + menu.getParentId());
	    		parentMenu = findParent(menuList, menu.getParentId());
	    		//add child to parent role
	    		if ( parentMenu != null ) {	   
	    			//System.out.println("ADD-CHILD====>role:" + menu.getName() + "=>TO-parent:" + menu.getParentName());	        		
	    			parentMenu.addSubmenu(menu);
	    		}	    		
    		}
    	}
    	//test
    	/*
    	for(MenuMdl menu1 : menuList) {
    		System.out.println("menu:" + menu1.getName());
    		for(MenuMdl submenu : menu.getSubmenus()) {
    			System.out.println("submenu:" + submenu.getName());
    		}
    	}
    	*/
    	return menuList;
    }
    
    
    private MenuMdl findParent(List<MenuMdl> menuList, Long parentId) {
    	//System.out.println("===================findParent======================");
    	for(MenuMdl item : menuList) {
    		if ( item.isChild() ) continue;
    		
    		//System.out.println("PARENT-MENU==>item.getId()===>" + item.getId() + ";parentId==>" + item.getParentId() );
    		if ( item.getId() == parentId ) {
    			//System.out.println("FOUND-PARENT-MENU==>item.getId()===>" + item.getId() + "==parentId==>" + parentId );
    			return item;
    		}
    	}
    	//System.out.println("===================findParent======================");
    	return null;
    }
    
	public List<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}


	public List<MenuMdl> getMenuList() {
		return menuList;
	}


	public void setMenuList(List<MenuMdl> menuList) {
		this.menuList = menuList;
	}
    
    
}