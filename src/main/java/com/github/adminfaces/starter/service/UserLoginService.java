package com.github.adminfaces.starter.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.adminfaces.starter.model.UserLogin;
import com.github.adminfaces.starter.model.UserLoginRepository;
import com.github.adminfaces.starter.model.UserRole;
import com.github.adminfaces.starter.model.UserRoleRepository;

@Service
@Transactional
public class UserLoginService implements UserDetailsService, Serializable {
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4963978503045054862L;
	
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	private BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
  
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserLogin user = userLoginRepository.findByUsername(username);
	    if (user == null) throw new UsernameNotFoundException(username);
	
	    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	    for (UserRole userRole : user.getUserRoles()){
	        grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
	    }
	    
	    logger.info("====loadUserByUsername===========" 
	    		+ "\rUSER=" + user.getUsername() 
	    		+ ", PWD=" + user.getPassword()
	    		+ ", ROLE=" +StringUtils.collectionToCommaDelimitedString(grantedAuthorities));
	    
	    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    
	}

	public List<UserLogin> findAll() {
		return userLoginRepository.findAll();
	}
	
	public UserLogin findById(Long id) {
		return userLoginRepository.getOne(id);
	}
	
	public UserLogin findByUsername(String username) {
		return userLoginRepository.findByUsername(username);
	}
	public void createUser(UserLogin userLogin) {
		userLogin.setPassword(encryptPassword(userLogin.getPassword()));
		List<UserRole> userRoles = userRoleRepository.findAllByUserLogin(userLogin);		
		
		if ( userRoles != null )
			userLogin.setUserRoles(new HashSet<>(userRoles));
		
		userLoginRepository.save(userLogin);
		
	}

	public String encryptPassword(String rawPassword) {
		return bCryptPasswordEncoder.encode(rawPassword);
	}
	
	public void updateUser(UserDetails user) {
		userLoginRepository.save((UserLogin)user);
		
	}

	public void save(UserLogin user) {
		userLoginRepository.save(user);
		
	}

	public void delete(UserLogin user) {
		userLoginRepository.delete(user);
	}
	
	public void deleteById(Long id) {
		userLoginRepository.deleteById(id);
	}
	
	public void deleteUser(String username) {
		UserDetails user = loadUserByUsername(username);
		
		if ( user != null )
			userLoginRepository.delete((UserLogin)user);
		
	}

	
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();

		if (currentUser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context "
							+ "for current user.");
		}

		String username = currentUser.getName();

		logger.debug("Changing password for user '" + username + "'");

		// If an authentication manager has been set, re-authenticate the user with the
		// supplied password.
		if (authenticationManager != null) {
			logger.debug("Reauthenticating user '" + username
					+ "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					username, oldPassword));
		}
		else {
			logger.debug("No authentication manager set. Password won't be re-checked.");
		}

		UserDetails user = loadUserByUsername(username);

		if (user == null) {
			throw new IllegalStateException("Current user doesn't exist in database.");
		}

		((UserLogin)user).setPassword(newPassword);
		
		userLoginRepository.save((UserLogin)user);
		
	}

	public boolean userExists(String username) {
		UserDetails user = loadUserByUsername(username);
		return user != null;
	}
	
	public boolean checkPassword(String password, String encrypted){
        return bCryptPasswordEncoder().matches(password, encrypted);
    }

}