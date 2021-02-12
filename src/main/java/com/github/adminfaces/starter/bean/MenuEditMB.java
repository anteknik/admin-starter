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
import com.github.adminfaces.starter.service.RoleService;
import com.github.adminfaces.starter.util.FacesHelper;

@Named
@SessionScoped
public class MenuEditMB {
	
	private Long id;  
	
	private String caller;  	
	private Role role;
	private List<Role> parentRoleList = new ArrayList<>();
	
	@Inject
	private RoleService roleService;
	
	
	private boolean editable = false;
	
	@PostConstruct
	public void init()
	{
		if ( isAjaxRequest() ) return;
			
		
	}
	
	public void initializer()
	{  	
		//System.out.println("menu===================" + id);
		
		if ( isAjaxRequest() ) return;		
				
		role = new Role();
		
		
		parentRoleList = roleService.findAllByParent(null);
		
		//System.out.println("initializer()==============================");
		if (id != null)
	    {
			role = roleService.findById(id);			
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
			Role existing = roleService.findByName(role.getName());			
			if ( existing != null ) {
				FacesHelper.addDetailMessage("Menu sudah ada!", FacesMessage.SEVERITY_WARN);
	  			return null;
			}		
			roleService.save(role);
			FacesHelper.addDetailMessage("Simpan data Menu berhasil");
	  	} else {
	  		//System.out.println("role-name======" + role.getName());
	  		roleService.save(role);	  		
	  		FacesHelper.addDetailMessage("Update data Menu berhasil");
	  	}   	
		Faces.getExternalContext().getFlash().setKeepMessages(true);		
		
		role = new Role();
	    editable = false;
	    id = null;
	    return getRedirectPage();
	}
	
	private boolean isNew() {
		return role.getId() == null;
	}
	  		
	public String delete()
	{
		try {
			//delete child 
			roleService.delete(role);
			//delete user 
			editable = false;
			id = null;
			FacesHelper.addDetailMessage("Hapus data Menu berhasil");
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			return getRedirectPage();
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Hapus data Menu gagal terjadi kesalahan";
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
		//System.out.println("back-to=============" +getRedirectPage());
		editable = false;
		id = null; 
		return getRedirectPage();
	}

	private String getRedirectPage() {
		//System.out.println("tipe==============================================="+caller);
		return ("parent".equalsIgnoreCase(caller) ? "menu-list.xhtml" : "submenu-list.xhtml") + "?faces-redirect=true";
	}

	public Long getId() {
		return id;
	}

	public String getCaller() {
		return caller;
	}

	public Role getRole() {
		return role;
	}

	public List<Role> getParentRoleList() {
		return parentRoleList;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setParentRoleList(List<Role> parentRoleList) {
		this.parentRoleList = parentRoleList;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
