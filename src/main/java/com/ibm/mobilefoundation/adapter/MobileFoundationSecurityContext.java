package com.ibm.mobilefoundation.adapter;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class MobileFoundationSecurityContext implements SecurityContext {

    

    @Override
    public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSecure() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        // TODO Auto-generated method stub
        return null;
    }
    
}