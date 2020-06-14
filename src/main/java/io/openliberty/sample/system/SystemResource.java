// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.sample.system;

import javax.ws.rs.core.Response;

import com.ibm.mobilefoundation.adapter.MobileFoundationSecurityContext;
import com.ibm.mobilefoundation.adapter.OAuthSecurity;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@RequestScoped
@Path("/properties")
public class SystemResource {

	@Context
	private MobileFoundationSecurityContext secContext ; 

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Timed(name = "getPropertiesTime", description = "Time needed to get the JVM system properties")
	@Counted(absolute = true, description = "Number of times the JVM system properties are requested")
	@OAuthSecurity(scope="accessRestricted")
	public Response getProperties() {
		String response = "";
		if ( secContext != null) {
			response = secContext.getScope(); 
		}
	    return Response.ok(response).build();
	}


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Timed(name = "getPropertiesTime", description = "Time needed to get the JVM system properties")
	@Counted(absolute = true, description = "Number of times the JVM system properties are requested")
	@Path("/helloworld")
	public Response getHelloWorld() {
	    return Response.ok("Hello World").build();
	}
}
