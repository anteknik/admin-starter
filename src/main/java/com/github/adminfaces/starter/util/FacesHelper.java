package com.github.adminfaces.starter.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;

import org.omnifaces.util.Messages;


public class FacesHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void addDetailMessage(String message)
    {
        addDetailMessage(message, null);
    }

    public static void addMessage(String message)
    {
        addMessage(message, null);
    }
    
    public static void addDetailMessage(String message, FacesMessage.Severity severity)
    {

        FacesMessage facesMessage = Messages.create("").detail(message).get();
        //default
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        if ( severity != null )
        {
            facesMessage.setSeverity(severity);
        }        
        Messages.add(null, facesMessage);
        
    }
    
    public static void addMessage(String message, FacesMessage.Severity severity)
    {

        FacesMessage facesMessage = Messages.create(message).get();
        //default
        facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        if ( severity != null )
        {
            facesMessage.setSeverity(severity);
        }        
        Messages.add(null, facesMessage);
        
    }

}