package com.github.adminfaces.starter.util;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class ElHelper {
	public static String trim(String val, int length) {
		//System.out.println("val===========================" + val);
		if (val != null) return val.length() > length ? (val.substring(0, length) + "...") : val;		
		return "";
	}
	
	public static char toAlphabet(int num) {
		//System.out.println(num + "===================>" +(char)num);
		return (char)num;
	}
}
