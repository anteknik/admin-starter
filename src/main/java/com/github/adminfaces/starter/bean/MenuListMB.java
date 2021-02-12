package com.github.adminfaces.starter.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.service.RoleService;
import com.github.adminfaces.starter.util.FacesHelper;

@Named
@ViewScoped
public class MenuListMB {

	@Inject
	private RoleService roleService;

    private List<Role> roleList = new ArrayList<>();
    private List<Role> selectedRoleList = new ArrayList<>();
    
    private List<Role> subRoleList = new ArrayList<>();
    private List<Role> selectedSubRoleList = new ArrayList<>();
    
    @PostConstruct
    public void init()
    {
    	roleList = roleService.findAllByParent(null);
    	subRoleList = roleService.findAllChild();
    	
    }

    public void deleteSelected()
    {
        for (Role selected : selectedRoleList)
        {
        	if ( selected.getParent() == null ) {
        		FacesHelper.addDetailMessage("Menu utama tidak boleh dihapus", FacesMessage.SEVERITY_WARN);
        		return;
        	}
        }
        
        FacesHelper.addDetailMessage("Hapus menu berhasil!");
    }

	public List<Role> getRoleList() {
		return roleList;
	}

	public List<Role> getSelectedRoleList() {
		return selectedRoleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public void setSelectedRoleList(List<Role> selectedRoleList) {
		this.selectedRoleList = selectedRoleList;
	}

	public List<Role> getSubRoleList() {
		return subRoleList;
	}

	public List<Role> getSelectedSubRoleList() {
		return selectedSubRoleList;
	}

	public void setSubRoleList(List<Role> subRoleList) {
		this.subRoleList = subRoleList;
	}

	public void setSelectedSubRoleList(List<Role> selectedSubRoleList) {
		this.selectedSubRoleList = selectedSubRoleList;
	}

	    
    
}
