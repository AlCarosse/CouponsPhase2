package com.raviv.coupons.rest.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.CustomersBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.exceptions.ExceptionHandler;
import com.raviv.coupons.rest.api.inputs.CreateCompanyInput;
import com.raviv.coupons.rest.api.inputs.CreateCustomerInput;
import com.raviv.coupons.rest.api.outputs.ServiceOutput;
import com.raviv.coupons.rest.api.outputs.StandartOutput;

@Path("/register")
public class RegisterApi 
{
	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceOutput createCustomer(@Context HttpServletRequest request, @Context HttpServletResponse response, CreateCustomerInput createCustomerInput)
	{
		ServiceOutput serviceOutput = new ServiceOutput();
		
		try 
		{
			System.out.println(createCustomerInput);
		
			UsersBlo usersBlo = new UsersBlo();
			CustomersBlo customersBlo = new CustomersBlo();
			
			/**
			 *  Get the admin user
			 */		
			User loggedUser = usersBlo.getAdminUser();
	
			/**
			 *  Create new customer
			 */		
			User 	newUser	= new User		( 	createCustomerInput.getUserName()  
												,createCustomerInput.getLoginName() 
												,createCustomerInput.getLoginPassword() );
			
			Customer	customer	= new Customer	( createCustomerInput.getCustomerName()	);
						
			customersBlo.createCustomer ( loggedUser, newUser, customer );
			
		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		
		return serviceOutput;
		
	}

	@POST
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(@Context HttpServletRequest request, CreateCompanyInput createCompanyInput) throws ApplicationException
	{
		System.out.println(createCompanyInput);
				
		UsersBlo usersBlo = new UsersBlo();
		CompanysBlo companysBlo = new CompanysBlo();
		
		/**
		 *  Get admin user
		 */		
		User loggedUser = usersBlo.getAdminUser();


		/**
		 *  Create new company
		 */		

		User 	newUser	= new User		( 	createCompanyInput.getUserName()  
											,createCompanyInput.getLoginName() 
											,createCompanyInput.getLoginPassword() );
		
		Company	company	= new Company	( createCompanyInput.getCompanyName(), createCompanyInput.getCompanyEmail()	);
				
		companysBlo.createCompany( loggedUser, newUser , company);
	}
	
}
