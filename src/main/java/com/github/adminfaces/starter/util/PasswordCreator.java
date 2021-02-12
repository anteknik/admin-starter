package com.github.adminfaces.starter.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordCreator {
	
	private static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
	
	//public static void main(String[] args) {
		//System.out.println("===========BCRYPT==========");
		//d033e22ae348aeb5660fc2140aec35850c4da997
		//System.out.println("admin=>" + passwordEncoder().encode("admin"));
		
	//}

}
