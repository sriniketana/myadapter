package com.ibm.mobilefoundation.adapter;

import java.io.IOException;
import java.net.ResponseCache;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import com.ibm.mfp.java.token.validator.AuthenticationError;
import com.ibm.mfp.java.token.validator.TokenValidationException;
import com.ibm.mfp.java.token.validator.TokenValidationManager;
import com.ibm.mfp.java.token.validator.TokenValidationResult;

//@PreMatching
@Provider
@OAuthSecurity
public class MobileFoundationAuthFilter implements ContainerRequestFilter {

    TokenValidationManager tokenValidator = null; 
    String mfURl = "http://mobilefoundation.rahultestocp-c33bf0f22ab59313b3628c493e016b88-0000.us-south.containers.appdomain.cloud/mfp/api"; 
    public static final String AUTH_HEADER = "Authorization";

    @Context ResourceInfo resourceInfo ;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try { 
            URI mfpUrl = new URI (mfURl) ;
            tokenValidator = new TokenValidationManager(mfpUrl, "test", "test");
            String authCredentials = requestContext.getHeaderString(AUTH_HEADER);
            System.out.println("Auth header is " + requestContext.getHeaderString(AUTH_HEADER));

            String scope = "RegisteredClient"; 
            if (resourceInfo.getResourceMethod().getAnnotation(OAuthSecurity.class) != null){
                scope = resourceInfo.getResourceMethod().getAnnotation(OAuthSecurity.class).scope();
                System.out.println("OAuthSecurity scope is " + scope) ;
            }

            try {
                TokenValidationResult tokenValidationRes = tokenValidator.validate(authCredentials, scope);
                if (tokenValidationRes.getAuthenticationError() != null) {
                    // Error
                    AuthenticationError error = tokenValidationRes.getAuthenticationError();
                    ResponseBuilder builder = Response.status(error.getStatus())
                        .header("WWW-Authenticate", error.getAuthenticateHeader());
                    requestContext.abortWith(builder.build());

                } else if (tokenValidationRes.getIntrospectionData() != null) {
                    // Success
                    String model  = tokenValidationRes.getIntrospectionData().getScope();
                    
                    requestContext.setSecurityContext(new SecurityContext(){
                    
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
                        public Principal getUserPrincipal() {
                            // TODO Auto-generated method stub
                            return new MobileFoundationUser(tokenValidationRes.getIntrospectionData().getData());
                        }
                    
                        @Override
                        public String getAuthenticationScheme() {
                            // TODO Auto-generated method stub
                            return null;
                        }
                    });

                }
            } catch (TokenValidationException e) {
                e.printStackTrace();
                requestContext.abortWith(Response.status(500).build());
            }
        }catch(Exception ex){ 
            ex.printStackTrace();
        }
    }

    
}