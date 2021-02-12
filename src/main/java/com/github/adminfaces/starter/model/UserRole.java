package com.github.adminfaces.starter.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "user_roles")
@EntityListeners(AuditingEntityListener.class)
public class UserRole extends BaseEntityLongID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -87016307351937952L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserLogin userLogin;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	
	public UserRole() {}
	
	public UserRole(Long id, UserLogin userLogin, Role role) {
		this.id = id;
		this.userLogin = userLogin;
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	public List<String> getSubRoleNames() {
		List<String> nameList = new LinkedList<String>();
		for(UserRole item : this.userLogin.getUserRoles()) {
			if ( item.getRole() != null ) {
				if ( this.getRole().equals(item.getRole().getParent())) {
					nameList.add(item.getRole().getName());
				}
			}
		}
		
		return nameList;
	}
	
	public String getDelimitedSubRoleNames() {
		return StringUtils.collectionToDelimitedString(getSubRoleNames(), ", ");
	}
	
	@Override
	public String toString() {
		return "id=" + id + ",user=" + ( userLogin != null ? userLogin.getUsername() : "null") + ",role=" + (role != null ? role.getName() : "null");
	}

	@Override
	public boolean equals(Object o) {
		 if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        UserRole _o = (UserRole) o;

	        return id.equals(_o.id);
	}
}
