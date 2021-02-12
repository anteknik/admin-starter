package com.github.adminfaces.starter.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Faces;

import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.model.UserRole;
import com.github.adminfaces.starter.service.RoleService;
import com.github.adminfaces.starter.service.UserLoginService;
import com.github.adminfaces.starter.service.UserRoleService;
import com.github.adminfaces.starter.util.FacesHelper;

@Named
@SessionScoped
public class UserRoleEditMB {
	
	private Long id;  	
	private UserRole userRole;
	private List<Role> parentRoleList = new ArrayList<>();
	private List<Role> roleList = new ArrayList<>();
	private List<Role> selectedRoleList = new ArrayList<>();
    
	private List<UserLogin> userLoginList = new ArrayList<>();
	
	@Inject
	private UserRoleService userRoleService;
	
	@Inject
	private RoleService roleService;
	
	@Inject
	private UserLoginService userLoginService;
	
	
	private boolean editable = false;
	
	@PostConstruct
	public void init()
	{
		if ( isAjaxRequest() ) return;
	}
	
	public void initializer()
	{  	
		//System.out.println("===================" + id);
		
		if ( isAjaxRequest() ) return;		
		
		userRole = new UserRole();
		roleList =  new ArrayList();
		selectedRoleList = new ArrayList();
		
		parentRoleList = roleService.findAllByParentOrderByIndexAsc(null);
		userLoginList = userLoginService.findAll();
		//System.out.println("=======userLoginList.size()========>" + userLoginList.size());
		
		//System.out.println("initializer()==============================");
		if (id != null)
	    {
			userRole = userRoleService.findById(id);
			if ( userRole != null ) {
				roleList = roleService.findAllByParentOrderByIndexAsc(userRole.getRole());
			
				if ( userRole.getUserLogin() != null ) {
					for(UserRole urole : userRole.getUserLogin().getUserRoles()) {
						for(Role role : roleList) {
							if(role.equals(urole.getRole())) selectedRoleList.add(role);
						}
					}
				}
			}
			editable = true;   
			
	    }
	}
		
	private boolean isAjaxRequest() {
	  PartialViewContext partialViewContext = FacesContext.getCurrentInstance().getPartialViewContext();
	  return null != partialViewContext && partialViewContext.isAjaxRequest();
	}

	public String save()
	{ 		
		//new
		if ( isNew() ) {
			UserRole existing = userRoleService.findByUserLoginAndRole(userRole.getUserLogin(), userRole.getRole());			
			if ( existing != null ) {
				FacesHelper.addDetailMessage("Role sudah ada!", FacesMessage.SEVERITY_WARN);
	  			return null;
			}		
			userRoleService.save(userRole);
			saveSelectedRole(userRole.getUserLogin(), selectedRoleList);
			FacesHelper.addDetailMessage("Simpan data role berhasil");
	  	} else {
	  		//userRoleService.save(userRole);
	  		//delete role except parent
	  		userRoleService.deleteByUserLoginAndRoleParent(userRole.getUserLogin(), userRole.getRole());
	  		//hanya update child	  		
	  		saveSelectedRole(userRole.getUserLogin(), selectedRoleList);
	  		FacesHelper.addDetailMessage("Update data role berhasil");
	  	}   	
		Faces.getExternalContext().getFlash().setKeepMessages(true);		
		
	    editable = false;
	    id = null;
	    return "userrole-list.xhtml?faces-redirect=true";
	}
	 
	private void saveSelectedRole(UserLogin userLogin, List<Role> roleList) {
		for(Role role : roleList) {
  			userRoleService.save(new UserRole(null, userLogin, role));
  		}
	}
	
	private boolean isNew() {
		return userRole.getId() == null;
	}
	  		
	public String delete()
	{
		try {
			//delete child 
			userRoleService.delete(userRole);
			userRoleService.deleteByUserLoginAndRoleParent(userRole.getUserLogin(), userRole.getRole());
			//delete user 
			editable = false;
			id = null;
			FacesHelper.addDetailMessage("Hapus data role berhasil");
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			return "user-list.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Hapus data role gagal terjadi kesalahan";
			Throwable t = e.getCause();			
			if ( t instanceof ConstraintViolationException) {
				if ( t.getCause() instanceof org.postgresql.util.PSQLException)
					message += " : " + t.getCause().getMessage();
				else 
					message += " : " + t.getMessage();
			} else {
				message += " : " + e.getMessage();
			}
			
			FacesHelper.addDetailMessage(message,  FacesMessage.SEVERITY_WARN);
			
		}
		return null;
	}

	public String back()
	{
		editable = false;
		id = null;
		return "userrole-list.xhtml?faces-redirect=true";
	}

	public void parentRoleChangeListener() {
		//System.out.println("=============1" + userRole);
		//System.out.println("=============2" + (userRole.getRole() != null ? userRole.getRole().getName() : ""));
		if ( userRole.getRole() != null )
			roleList = roleService.findAllByParent(userRole.getRole());
		else roleList = new ArrayList<Role>();
	}

	//user dengan name admin(super admin) tidak boleh di edit dan delete
	public boolean isUserAdmin() {
		if ( userRole.getUserLogin() != null )
			return "admin".equalsIgnoreCase(userRole.getUserLogin().getUsername()) && isRoleAdministrator();
		
		return false;
	}
	
	private boolean isRoleAdministrator() {
		if ( userRole != null )
			return userRole.getId() == 1;
		
		return false;
	}
	
	public Long getId() {
		return id;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public List<Role> getParentRoleList() {
		return parentRoleList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public List<Role> getSelectedRoleList() {
		return selectedRoleList;
	}

	
	public boolean isEditable() {
		return editable;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public void setParentRoleList(List<Role> parentRoleList) {
		this.parentRoleList = parentRoleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public void setSelectedRoleList(List<Role> selectedRoleList) {
		this.selectedRoleList = selectedRoleList;
	}

	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<UserLogin> getUserLoginList() {
		return userLoginList;
	}

	public void setUserLoginList(List<UserLogin> userLoginList) {
		this.userLoginList = userLoginList;
	}
	
	
	  
}
