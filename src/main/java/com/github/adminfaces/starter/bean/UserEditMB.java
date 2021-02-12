package com.github.adminfaces.starter.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Faces;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.adminfaces.starter.model.Role;
import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.model.UserRole;
import com.github.adminfaces.starter.service.RoleService;
import com.github.adminfaces.starter.service.UserLoginService;
import com.github.adminfaces.starter.service.UserRoleService;
import com.github.adminfaces.starter.util.FacesHelper;

@ManagedBean
@ViewScoped
public class UserEditMB {
	
	private UserLogin user;
  
	private Long id;
  	
	private String passwordConfirm;
	
	private List<Role> roleList = new ArrayList<>();
   
	private Role role;
	
	@Autowired
	private UserLoginService userLoginService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;
	
	private boolean editable = false;
	
	@PostConstruct
	public void init()
	{
		user = new UserLogin();
		roleList = roleService.findAll();
	}
	
	public void initializer()
	{  	
		//System.out.println("initializer()==============================");
		if (id != null)
	    {
			user = userLoginService.findById(id);
			editable = true;   
			
			for(UserRole userRole : user.getUserRoles()) {
				role = userRole.getRole();
				break;
			}
	    }
	}
			  
	public boolean isEnabled() {
		return user != null && user.isEnabled() ? true : false;     	
	}
	  
	public void setEnabled(boolean checked) {
	  	if ( user != null) user.setEnabled(checked);
	}
	  
	public String save()
	{	  	
		//new
		if ( !editable ) {
			//raw password
			if ( "".equalsIgnoreCase(user.getPassword()) || getPasswordConfirm().equalsIgnoreCase(user.getPassword()) == false ) {
				//kembalikan id diperlukan pada method initializer();
				id = user.getId();
				return "";
			}
	  		
			//cek apakah ada user dengan username yang sama
			UserLogin userLogin = userLoginService.findByUsername(user.getUsername());
			if ( userLogin != null ) {
				FacesHelper.addDetailMessage("User" + user.getUsername() + " sudah ada", FacesMessage.SEVERITY_WARN);
	  			return null;
			} else {
				//encrypt pwd
				user.setPassword(userLoginService.encryptPassword(user.getPassword()));
	  			user.setUsername(user.getUsername());
	  			userLoginService.save(user);	
	  			FacesHelper.addDetailMessage("Tambah data user berhasil");
			}
	  	} else {
	  		//update
	  		userLoginService.save(user);
	  		FacesHelper.addDetailMessage("Update data user berhasil");
	  	}   	
		Faces.getExternalContext().getFlash().setKeepMessages(true);
		
	    editable = false;
	    id = null;
	    return "user-list.xhtml?faces-redirect=true";
	}
	      
	  
	  
	public void updateUserRole(UserLogin user)
	{    	    
		//System.out.println("role=========" + role);
		if ( role != null ) {
			//delete existing
			userRoleService.deleteAllRolesByUser(user);
			
			//create new one
			UserRole userrole = new UserRole();
			userrole.setRole(role);
			userrole.setUserLogin(user);;
			
			//save
			userRoleService.save(userrole);
			
			//System.out.println(userrole.toString());
		}
	}
	
	public String delete()
	{
		try {
			//delete child 
			userRoleService.deleteAllRolesByUser(user);
			//delete user 
			userLoginService.deleteById(user.getId());
			//userLoginService.delete(user);
			editable = false;
			id = null;
			FacesHelper.addDetailMessage("Hapus data user berhasil");
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			return "user-list.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Hapus user data gagal terjadi kesalahan";
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
		return "user-list.xhtml?faces-redirect=true";
	}
  

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public UserLogin getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setUser(UserLogin user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	
	  
}
