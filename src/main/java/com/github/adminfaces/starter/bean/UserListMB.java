package com.github.adminfaces.starter.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.service.UserLoginService;

@Named
@ViewScoped
public class UserListMB {

	@Inject
	private UserLoginService userLoginService;

    private List<UserLogin> users = new ArrayList<>();
    
    private List<UserLogin> selectedUsers = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        users = userLoginService.findAll();
    }

    public void deleteSelected()
    {
        for (UserLogin selected : selectedUsers)
        {
        	userLoginService.delete(selected);
            selectedUsers.remove(selected);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Hapus user berhasil!"));
    }

	public List<UserLogin> getUsers() {
		return users;
	}

	public List<UserLogin> getSelectedUsers() {
		return selectedUsers;
	}

	public void setUsers(List<UserLogin> users) {
		this.users = users;
	}

	public void setSelectedUsers(List<UserLogin> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
    
    
}
