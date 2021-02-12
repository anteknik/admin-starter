package com.github.adminfaces.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.adminfaces.starter.service.UserLoginService;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserLoginService userLoginService; 
	
	private BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
	    String username = token.getName();
	    UserDetails user = userLoginService.loadUserByUsername(username);
	    if(user == null) {
	    	throw new UsernameNotFoundException("Invalid username");
	    }
	    // Database Password already encrypted:
	    String password = user.getPassword();
	
	    boolean passwordsMatch = bCryptPasswordEncoder().matches(token.getCredentials().toString(), password);
	
	    if(!passwordsMatch) {
	    	throw new BadCredentialsException("Invalid password");
	    }
	    
	    UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	    return userAuthToken;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {

		return (UsernamePasswordAuthenticationToken.class.equals(authentication));

	}
	
}