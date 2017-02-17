package com.raviv.coupons.rest.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.Cookies;

@Path("/login")
public class LoginApi {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request, @Context HttpServletResponse response,  User userInput ){

		UsersBlo usersBlo = new UsersBlo();
		
		System.out.println("Jersey Login...");
		
		
		// =============================================
		// Check login coockie
		// =============================================
		Cookie[] cookies = request.getCookies();
		//String value = null;
		if (cookies!=null)
		{
			for (Cookie cookie : cookies)
			{
				/*
				if (cookie.getName().equals("loginToken") )
					value = cookie.getValue();
					*/
				System.out.println( cookie.getName() + " : " + cookie.getValue() );
			}
		}
		
		User user = null;
		try {
			// ======================================
			// Login ...
			// ======================================
			user = usersBlo.login( userInput.getLoginName() , userInput.getLoginPassword() );
						
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		if ( user != null )
		{
			// SUCCESS - user found
			// Create session
			request.getSession();
			
			request.getSession(false).setAttribute("LOGIN_USER_ID", user.getUserId() );
			
			System.out.println("200");
			response.setStatus(200);
			
			long userId = user.getUserId();
			Cookie cookie = new Cookie( Cookies.LOGIN_USER_ID ,  String.valueOf( userId )  );
			response.addCookie(cookie);
		}
		else
		{
			// Failes - user not found - null
			System.out.println("401");
			response.setStatus(401);
			/*
			Cookie cookie;
			cookie = new Cookie( Cookies.LOGIN_USER_ID ,  String.valueOf( 0 )  );
			response.addCookie(cookie);

			HttpSession session = request.getSession(false);
			if (session != null)
			{
				session.invalidate();
			}
			*/
			user = new User();
		}
		System.out.println(user);

		return user;
	}

	@DELETE
	public void deleteUser(long userId){
		
	}
	
	
}
