package com.raviv.coupons.rest.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/rest/api/*")
public class LoginFilter implements Filter {


	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest	httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse	httpServletResponse = (HttpServletResponse) response;
		
		// Check if http request came  with session, if not we get null;
		HttpSession	httpSession = httpServletRequest.getSession(false);
		
		if ( httpSession == null )
		{ // user not logged in
			System.out.println("filter failed");
			httpServletResponse.setStatus(401);
		}
		else
		{	// user logged in , continue to next filter.
			System.out.println("filter success");
			chain.doFilter(request, response);			
		}
		
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
