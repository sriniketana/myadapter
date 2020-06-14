package com.ibm.mobilefoundation.adapter;

import java.security.Principal;

public class MobileFoundationUser implements Principal {

    private String scope = ""; 

    public MobileFoundationUser(String scope) {
        this.scope = scope;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "srihari";
    }

    public String getScope () { 
        return this.scope; 
    }

    
}