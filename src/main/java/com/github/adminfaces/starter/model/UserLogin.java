package com.github.adminfaces.starter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user_logins")
@EntityListeners(AuditingEntityListener.class)
public class UserLogin extends BaseEntityLongID implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1385920904765688705L;

		
	@Column(name="password", nullable = false)
	private String password;
	
	@Transient
	private String passwordConfirm;
	
	@Column(name="username", nullable = false)
	private String username;
	
	@Column(name="email", nullable = true)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "userLogin")
	@OrderBy("role.id ASC") 
	private Set<UserRole> userRoles;
	
	@Column(name="account_non_expired", nullable = false, columnDefinition="int4 DEFAULT 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean accountNonExpired;
	
	@Column(name="account_non_locked", nullable = false, columnDefinition="int4 DEFAULT 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean accountNonLocked;
	
	@Column(name="credentials_non_expired", nullable = false, columnDefinition="int4 DEFAULT 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean credentialsNonExpired;
	
	@Column(name="enabled", nullable = false, columnDefinition="int4 DEFAULT 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;

	
	public UserLogin() {}
	
	public UserLogin(String username, String password, Set<GrantedAuthority> grantedAuthorities) {
		this.password = password;
		this.username = username; 
		//this.userRoles = grantedAuthorities;
	}
	
	/**
	 * 
	 * @param id
	 * @param password
	 * @param username
	 * @param accountNonExpired
	 * @param accountNonLocked
	 * @param credentialsNonExpired
	 * @param enabled
	 */
	public UserLogin(Long id, String username, String password, String email,
			boolean accountNonExpired, boolean accountNonLocked, 
			boolean credentialsNonExpired, boolean enabled) {
		this.id = id;
		this.password = password;
		this.username = username; 
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked; 
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public String getEmail() {
		return email;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public Role getParentRole( ) {
		for(UserRole item : userRoles) {
			if (item.getRole().getParent() == null) return item.getRole();
		}
		
		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList(getUserRoles());
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLogin _o = (UserLogin) o;

        return id.equals(_o.id);
    }


	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
