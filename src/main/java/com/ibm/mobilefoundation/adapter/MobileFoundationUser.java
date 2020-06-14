package com.ibm.mobilefoundation.adapter;

import java.security.Principal;
import java.util.Map;

import javax.json.JsonObject;

public class MobileFoundationUser implements Principal {

    private String scope = ""; 
    private Map<String, Object> introspectionData = null ; 

    public MobileFoundationUser(Map<String, Object> introspectionDataMap) {
        this.introspectionData = introspectionDataMap;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "srihari";
    }

    public String getScope () { 
        return this.scope; 
    }

    public String introspectionDataString(){ 
        String retval = ""; 
        for (String  key: introspectionData.keySet()){
            retval += key + ":" + introspectionData.get(key) + "\n"; 
        }
        return retval; 
    }
    
}