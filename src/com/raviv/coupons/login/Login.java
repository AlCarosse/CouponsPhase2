package com.raviv.coupons.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Loginn")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// simulating success login
		// http 200 or 204 is success
		// Other status failure
		// 401 unithorized
		// response.setStatus(401);
	
		String strUser = request.getParameter("user");
		String strPassword = request.getParameter("password");
		
		UsersBlo usersBlo = new UsersBlo();
		
		System.out.println("Login...");
		
		User user = null;
		try {
			// ======================================
			// Login ...
			// ======================================
			user = usersBlo.login( strUser , strPassword );
			
			System.out.println(user);
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
		if ( user != null )
		{
			// Create session
			request.getSession();
			System.out.println(user);
			response.setStatus(200);
		}
		else
		{
			response.setStatus(401);
			System.out.println(user);
		}
		
		
		
	
	}



}
