package com.github.adminfaces.starter.bean;

import java.io.IOException;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import org.omnifaces.util.Faces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.service.UserLoginService;
import com.github.adminfaces.starter.util.FacesHelper;

@ManagedBean
@SessionScoped
public class UserMB {
    
    private String password;

    private String newPassword;

    private String confirmNewPassword;
        
    private UserLogin currentUser;

    @Autowired
    private UserLoginService userLoginService;


    @PostConstruct
    public void init()
    {

    }

    public void initializer()
    {
    	password = "";        	
    	currentUser = getCurrentUserLogin();
    }

    public String ubahPassword() throws IOException {
    	try {
    		
    		if (newPassword.equals(confirmNewPassword)) {
    			
    			//check if old password valid
    			if (userLoginService.checkPassword(password, currentUser.getPassword())) {
    				System.out.println("password lama valid===================");
    				currentUser.setPassword(userLoginService.encryptPassword(newPassword));
    				userLoginService.save(currentUser);

    				FacesHelper.addDetailMessage("Password berhasil diubah");
    				Faces.getExternalContext().getFlash().setKeepMessages(true);
    				return "/index.xhtml?faces-redirect=true";
    			} else {
    				//System.out.println("Password sekarang salah===================");
    				FacesHelper.addDetailMessage("Password sekarang salah!", FacesMessage.SEVERITY_WARN);  
    				return null;
    			}
    		} else {
    			System.out.println("Password baru tidak sama===================");
    			FacesHelper.addDetailMessage("Password baru tidak sama!", FacesMessage.SEVERITY_WARN); 
    			return null;
    		}
    	} catch (Exception e) {
    		FacesHelper.addDetailMessage("Terjadi kesalahan : " + e.getMessage(), FacesMessage.SEVERITY_WARN); 
    	}
    	
    	return null;
    }

    public String resetPassword() {
    	try {
	    	String password = "12345678";

	    	currentUser.setPassword(userLoginService.encryptPassword(password));
			userLoginService.save(currentUser);
			
			FacesHelper.addDetailMessage("Reset password : " + password);
			Faces.getExternalContext().getFlash().setKeepMessages(true);
			//Faces.redirect("index.xhtml");			
    	} catch (Exception e) {
    		FacesHelper.addDetailMessage("Gagal mereset password!!", FacesMessage.SEVERITY_WARN); 
    	}
    	
    	return null;
    }
    
    private UserLogin getCurrentUserLogin( ) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userLoginService.findByUsername(authentication.getName());
    }
	public String getPassword() {
		return password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public UserLogin getCurrentUser() {
		return currentUser;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public void setCurrentUser(UserLogin currentUser) {
		this.currentUser = currentUser;
	}

	public String getCurrentUsername() {
		currentUser = getCurrentUserLogin();		
		if ( currentUser != null ) {
			//System.out.println("currentUser.getUsername()===================" + currentUser.getUsername());

			return currentUser.getUsername();
		}
		return "anonymous";
	}


	
}