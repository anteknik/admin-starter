package com.github.adminfaces.starter.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.adminfaces.starter.model.UserRole;
import com.github.adminfaces.starter.service.UserRoleService;

@Named
@ViewScoped
public class UserRoleListMB {
	@Inject
	private UserRoleService userRoleService;

    private List<UserRole> userRoleList = new ArrayList<>();
    private List<UserRole> selectedUserRoleList = new ArrayList<>();

    @PostConstruct
    public void init()
    {
    	userRoleList = userRoleService.findAllParent();
    }

	public List<UserRole> getUserRoleList() {
		return userRoleList;
	}

	public List<UserRole> getSelectedUserRoleList() {
		return selectedUserRoleList;
	}

	public void setUserRoleList(List<UserRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public void setSelectedUserRoleList(List<UserRole> selectedUserRoleList) {
		this.selectedUserRoleList = selectedUserRoleList;
	}

    
    
}
