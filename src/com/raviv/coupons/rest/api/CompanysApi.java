package com.raviv.coupons.rest.api;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.List;

//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.exceptions.ExceptionHandler;
import com.raviv.coupons.rest.api.inputs.CreateCompanyInput;
import com.raviv.coupons.rest.api.outputs.GetAllCompanysOutput;
import com.raviv.coupons.rest.api.outputs.GetCompanyOutput;
import com.raviv.coupons.rest.api.outputs.ServiceOutput;
import com.raviv.coupons.utils.LoginSession;

@Path("api/companys")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanysApi {

	
	@POST
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput createCompany(@Context HttpServletRequest request, CreateCompanyInput createCompanyInput) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{

			System.out.println(createCompanyInput);

			Integer loginUserId = LoginSession.getLoginUserId(request);

			UsersBlo usersBlo = new UsersBlo();
			CompanysBlo companysBlo = new CompanysBlo();

			/**
			 *  Get the logged user
			 */		
			User loggedUser = usersBlo.getUserById( loginUserId);


			/**
			 *  Create new company
			 */		

			User 	newUser	= new User		( 	createCompanyInput.getUserName()  
					,createCompanyInput.getLoginName() 
					,createCompanyInput.getLoginPassword() );

			Company	company	= new Company	( createCompanyInput.getCompanyName(), createCompanyInput.getCompanyEmail()	);

			companysBlo.createCompany( loggedUser, newUser , company);

		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;

	}

	@DELETE
	@Path("/deleteCompany/companyId/{companyId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput deleteCompany(@Context HttpServletRequest request, @PathParam("companyId") long companyId ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println("companyId : " + companyId);

			Integer loginUserId = LoginSession.getLoginUserId(request);


			UsersBlo usersBlo = new UsersBlo();
			CompanysBlo companysBlo = new CompanysBlo();

			/**
			 *  Get the logged user
			 */		
			User loggedUser = usersBlo.getUserById( loginUserId);


			/**
			 *  Delete company
			 */		

			companysBlo.deleteCompany( loggedUser,  companyId );
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;

	}

	@PUT
	@Path("/updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput updateCompany( @Context HttpServletRequest request, Company company ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println(company);
			Integer loginUserId = LoginSession.getLoginUserId(request);

			UsersBlo usersBlo = new UsersBlo();
			CompanysBlo companysBlo = new CompanysBlo();

			/**
			 *  Get the logged user
			 */		
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Update company
			 */		
			companysBlo.updateCompany( loggedUser , company );
		}		
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;
	}

	@GET
	@Path("/getAllCompanys")
	@Produces(MediaType.APPLICATION_JSON)
	public GetAllCompanysOutput getAllCompanys( @Context HttpServletRequest request ) throws ApplicationException
	{
		GetAllCompanysOutput serviceOutput = new GetAllCompanysOutput();
		try 
		{
			Integer loginUserId = LoginSession.getLoginUserId(request);

			UsersBlo usersBlo = new UsersBlo();
			CompanysBlo companyBl = new CompanysBlo();

			/**
			 *  Get the logged user
			 */		
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Get all companies
			 */		
			List<Company> companys = companyBl.getAllCompanys(loggedUser);
			serviceOutput.setCompanys(companys);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;
	}

	@GET
	@Path("/getCompanyByCompanyId/companyId/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GetCompanyOutput getCompanyByCompanyId( @Context HttpServletRequest request, @PathParam("companyId") Integer companyId ) throws ApplicationException
	{
		GetCompanyOutput serviceOutput = new GetCompanyOutput();		
		try 
		{
			/**
			 *  Get the logged user
			 */		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			Company company = null;	
			CompanysBlo companysBlo = new CompanysBlo();

			/**
			 *  Get company, ADMIN profile
			 */		
			company =  companysBlo.getCompany( loggedUser , companyId );
			serviceOutput.setCompany(company);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;
	}

	@GET
	@Path("/getCompanyByLoggedUser")
	@Produces(MediaType.APPLICATION_JSON)
	public GetCompanyOutput getCompanyByLoggedUser( @Context HttpServletRequest request ) throws ApplicationException
	{
		GetCompanyOutput serviceOutput = new GetCompanyOutput();		
		try 
		{
			/**
			 *  Get the logged user
			 */		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Get company, COMPANY profile 
			 */		
			Company company = null;
			CompanysBlo companysBlo = new CompanysBlo();		
			company =  companysBlo.getCompany( loggedUser );
			serviceOutput.setCompany(company);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return serviceOutput;
	}

}