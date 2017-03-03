package com.raviv.coupons.rest.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CustomersBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.exceptions.ExceptionHandler;
import com.raviv.coupons.rest.api.inputs.CreateCustomerInput;
import com.raviv.coupons.rest.api.outputs.GetAllCustomersOutput;
import com.raviv.coupons.rest.api.outputs.GetCustomerOutput;
import com.raviv.coupons.rest.api.outputs.ServiceOutput;
import com.raviv.coupons.utils.LoginSession;

@Path("api/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomersApi {

	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput createCustomer(@Context HttpServletRequest request, CreateCustomerInput createCustomerInput) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println(createCustomerInput);

			Integer loginUserId = LoginSession.getLoginUserId(request);

			UsersBlo usersBlo = new UsersBlo();
			CustomersBlo customersBlo = new CustomersBlo();

			/**
			 *  Get the logged user
			 */		
			User loggedUser = usersBlo.getUserById( loginUserId);


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

	@DELETE
	@Path("/deleteCustomer/customerId/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput deleteCustomer(@Context HttpServletRequest request, @PathParam("customerId") long customerId ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println("customerId : " + customerId);

			UsersBlo usersBlo = new UsersBlo();
			CustomersBlo customersBlo = new CustomersBlo();

			/**
			 *  Get the logged user
			 */
			Integer loginUserId = LoginSession.getLoginUserId(request);
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Delete customer
			 */		
			customersBlo.deleteCustomer( loggedUser,  customerId );
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;
	}

	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput updateCustomer( @Context HttpServletRequest request, Customer customer ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println(customer);

			/**
			 *  Get the logged user
			 */
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Update customer
			 */
			CustomersBlo customersBlo = new CustomersBlo();
			customersBlo.updateCustomer( loggedUser , customer );
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;
	}

	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllCustomersOutput getAllCustomers( @Context HttpServletRequest request ) throws ApplicationException
	{		
		GetAllCustomersOutput serviceOutput = new GetAllCustomersOutput();
		try 
		{
			/**
			 *  Get the logged user
			 */
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Get all customers
			 */
			CustomersBlo customersBl = new CustomersBlo();
			List<Customer> customers = customersBl.getAllCustomers(loggedUser);
			serviceOutput.setCustomers(customers);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;
	}

	@GET
	@Path("/getCustomer/customerId/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GetCustomerOutput getCustomer( @Context HttpServletRequest request, @PathParam("customerId") long customerId ) throws ApplicationException
	{
		GetCustomerOutput serviceOutput = new GetCustomerOutput();		
		try 
		{
			/**
			 *  Get the logged user
			 */
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Get customer
			 */
			CustomersBlo customersBlo = new CustomersBlo();
			Customer customer = customersBlo.getCustomer( loggedUser , customerId );
			serviceOutput.setCustomer(customer);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;
	}

}
