package com.github.adminfaces.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationProvider authProvider;

	/*
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			// rest Login
			http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic().and().csrf()
					.disable();
		}
	}*/

	@Bean
	@Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	      return super.authenticationManagerBean();
	}  
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// form login
		http.authorizeRequests()
		.antMatchers("/", "/login.xhtml", "/javax.faces.resource/**")
			.permitAll()
		.anyRequest()
			.fullyAuthenticated()
		.and()
			.formLogin()
			.loginPage("/login.xhtml")
			.defaultSuccessUrl("/index.xhtml")
			.failureUrl("/login.xhtml?authfailed=true").permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login.xhtml")
			.logoutUrl("/logout.xhtml")
			//.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
		.and()
			.csrf().disable();


		// allow to use ressource links like pdf
		http.headers().frameOptions().sameOrigin();
	}

}