package com.raviv.coupons.rest.api;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import com.raviv.coupons.rest.beans.LoginDetails;

@Path("api/users")
public class UsersApi {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(LoginDetails loginDetails){
		System.out.println(loginDetails);
	}
	
	@PUT
	public void upadteUser(LoginDetails loginDetails){
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginDetails getUser( @PathParam("id") long userId){
		System.out.println("userId : " + userId);		
								
		return new LoginDetails( userId, "user1" ,"1234");
	}

	@DELETE
	public void deleteUser(long userId){
		
	}
	
	
}
