package com.raviv.coupons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Cookies 
{
	
	public static final String LOGIN_USER_ID = "LOGIN_USER_ID";
	
	
	public static Integer getLoginUserId(HttpServletRequest request)
	{
		
		// =============================================
		// Find login user id coockie
		// =============================================

		Cookie[] cookies = request.getCookies();

		Integer loginUserId  = null;
		String strValue = null;
		if (cookies!=null)
		{
			for (Cookie cookie : cookies)
			{				
				if (cookie.getName().equals(LOGIN_USER_ID) )
				{
					strValue = cookie.getValue();
					loginUserId = new Integer (strValue);
					return loginUserId;
				}
			}
		}
		
		return null;

	}
	
	
	

}
