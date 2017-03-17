package com.raviv.coupons.rest.api;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.CustomerCoupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.DynamicQueryParameters;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.dao.CompanysDao;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.exceptions.ExceptionHandler;
import com.raviv.coupons.rest.api.inputs.CreateCouponInput;
import com.raviv.coupons.rest.api.inputs.GetCouponsQueryInput;
import com.raviv.coupons.rest.api.inputs.GetCustomerCouponsQueryInput;
import com.raviv.coupons.rest.api.inputs.UpdateCouponInput;
import com.raviv.coupons.rest.api.outputs.GetCouponsOutput;
import com.raviv.coupons.rest.api.outputs.ServiceOutput;
import com.raviv.coupons.utils.LoginSession;
import com.raviv.coupons.utils.PrintUtils;
import com.raviv.coupons.utils.YyyyMmDd;

@Path("api/coupons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CouponsApi {

	@POST
	@Path("/createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput createCoupon(@Context HttpServletRequest request, CreateCouponInput createCouponInput) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println(createCouponInput);

			//==============================================
			// Get the logged user details with the user id
			//==============================================		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId );

			//==============================================
			// Get company details with the user id
			//==============================================		
			CompanysDao companysDao = new CompanysDao();
			Company company = companysDao.getCompanyByUserId( loginUserId );
			if ( company == null )
			{
				throw new ApplicationException(ErrorType.GENERAL_ERROR
						, "Failed to get company with userId : " + loginUserId );
			}
			PrintUtils.printHeader("Company deatils : ");		
			System.out.println(company);					
			long companyId = company.getCompanyId();

			//==============================================
			// Create new coupon
			//==============================================		
			Coupon 	coupon;
			coupon	= new Coupon (	  companyId
					, createCouponInput.getCouponTitle()
					, new YyyyMmDd ( createCouponInput.getCouponStartDate() )
					, new YyyyMmDd ( createCouponInput.getCouponEndDate() )
					, createCouponInput.getCouponsInStock()
					, createCouponInput.getCouponTypeId()
					, createCouponInput.getCouponMessage()	
					, createCouponInput.getCouponPrice()
					, createCouponInput.getImageFileName()
					) ;
			CouponsBlo couponsBlo = new CouponsBlo();		
			couponsBlo.createCoupon(loggedUser,coupon);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;
	}

	@DELETE
	@Path("/deleteCoupon/couponId/{couponId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput deleteCoupon(@Context HttpServletRequest request, @PathParam("couponId") long couponId ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println("couponId : " + couponId);

			/**
			 *  Get the logged user
			 */
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Delete coupon
			 */		
			CouponsBlo couponsBlo = new CouponsBlo();
			couponsBlo.deleteCoupon( loggedUser ,  couponId );
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;
	}

	@PUT
	@Path("/updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	public ServiceOutput updateCoupon( @Context HttpServletRequest request, UpdateCouponInput updateCouponInput ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println(updateCouponInput);
			/**
			 *  Get the logged user
			 */		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Update coupon
			 */				
			long 		couponId 		= updateCouponInput.getCouponId();
			YyyyMmDd 	couponEndDate 	= new YyyyMmDd( updateCouponInput.getCouponEndDate() );
			double  	couponPrice  	= updateCouponInput.getCouponPrice();

			Coupon 		coupon = new Coupon( couponId , couponEndDate , couponPrice);

			CouponsBlo 	couponsBlo = new CouponsBlo();
			couponsBlo.updateCoupon( loggedUser, coupon );

		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;
	}

	@GET
	@Path("/getCompanyCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public GetCouponsOutput getCompanyCoupons( @Context HttpServletRequest request ) throws ApplicationException
	{
		GetCouponsOutput getCouponsOutput = new GetCouponsOutput();
		try 
		{

			/**
			 *  Get the logged user
			 */		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 *  Get company coupons
			 */	
			CouponsBlo couponsBlo = new CouponsBlo();
			List<Coupon> coupons = couponsBlo.getCompanyCoupons(loggedUser);
			getCouponsOutput.setCoupons(coupons);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			getCouponsOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return getCouponsOutput;
	}

	@POST
	@Path("/getCompanyCouponsQuery")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
	public GetCouponsOutput getCompanyCouponsQuery( @Context HttpServletRequest request, GetCouponsQueryInput getCouponsQueryInput ) throws ApplicationException
	{
		GetCouponsOutput getCouponsOutput = new GetCouponsOutput();
		try 
		{

			System.out.println(getCouponsQueryInput);

			/**
			 *  Get the logged user
			 */		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 * Get company coupons with dynamicQueryParameters
			 */		

			DynamicQueryParameters dynamicQueryParameters = new DynamicQueryParameters();

			String couponTypeId = getCouponsQueryInput.getCouponTypeId();
			if ( couponTypeId != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.COUPON_TYPE_ID	, couponTypeId );
			}

			String fromPrice = getCouponsQueryInput.getFromPrice();
			if ( fromPrice != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.FROM_PRICE	, fromPrice );
			}

			String toPrice = getCouponsQueryInput.getToPrice();
			if ( toPrice != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.TO_PRICE	, toPrice );
			}

			String fromDate = getCouponsQueryInput.getFromDate();
			if ( fromDate != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.FROM_DATE	, fromDate );
			}

			String toDate = getCouponsQueryInput.getToDate();
			if ( toDate != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.TO_DATE	, toDate );
			}

			CouponsBlo couponsBlo = new CouponsBlo();
			List<Coupon> coupons = couponsBlo.getCompanyCouponsQuery(loggedUser, dynamicQueryParameters);
			getCouponsOutput.setCoupons(coupons);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			getCouponsOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return getCouponsOutput;
	}

	@GET
	@Path("/buyCoupon/couponId/{couponId}")
	public ServiceOutput buyCoupon(@Context HttpServletRequest request, @PathParam("couponId") long couponId ) throws ApplicationException
	{
		ServiceOutput serviceOutput = new ServiceOutput();		
		try 
		{
			System.out.println("buyCoupon : couponId : " + couponId);

			/**
			 *  Get the logged user
			 */
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 * Buy coupon
			 */
			CouponsBlo couponsBlo = new CouponsBlo();
			couponsBlo.buyCoupon( loggedUser , couponId );

		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}
		return serviceOutput;

	}

	@GET
	@Path("/getCustomerCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerCoupon> getCustomerCoupons( @Context HttpServletRequest request ) throws ApplicationException
	{
		/**
		 *  Get the logged user
		 */		
		Integer loginUserId = LoginSession.getLoginUserId(request);
		UsersBlo usersBlo = new UsersBlo();
		User loggedUser = usersBlo.getUserById( loginUserId);

		/**
		 *  Get company coupons
		 */	
		CouponsBlo couponsBlo = new CouponsBlo();
		return couponsBlo.getCustomerCoupons( loggedUser );
	}

	@POST
	@Path("/getCustomerCouponsQuery")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
	public List<CustomerCoupon> getCustomerCouponsQuery( @Context HttpServletRequest request, GetCustomerCouponsQueryInput getCustomerCouponsQueryInput ) throws ApplicationException
	{
		System.out.println(getCustomerCouponsQueryInput);

		/**
		 *  Get the logged user
		 */		
		Integer loginUserId = LoginSession.getLoginUserId(request);
		UsersBlo usersBlo = new UsersBlo();
		User loggedUser = usersBlo.getUserById( loginUserId);

		/**
		 * Get customer coupons with dynamicQueryParameters
		 */		

		DynamicQueryParameters dynamicQueryParameters = new DynamicQueryParameters();

		String couponTypeId = getCustomerCouponsQueryInput.getCouponTypeId();
		if ( couponTypeId != null )
		{
			dynamicQueryParameters.add(DynamicQueryParameters.COUPON_TYPE_ID	, couponTypeId );
		}

		String fromPrice = getCustomerCouponsQueryInput.getFromPrice();
		if ( fromPrice != null )
		{
			dynamicQueryParameters.add(DynamicQueryParameters.FROM_PRICE	, fromPrice );
		}

		String toPrice = getCustomerCouponsQueryInput.getToPrice();
		if ( toPrice != null )
		{
			dynamicQueryParameters.add(DynamicQueryParameters.TO_PRICE	, toPrice );
		}

		CouponsBlo couponsBlo = new CouponsBlo();
		return couponsBlo.getCustomerCouponsQuery( loggedUser , dynamicQueryParameters );
	}

	@POST
	@Path("/getCouponsForSaleByCustomerId")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)
	public GetCouponsOutput getCouponsForSaleByCustomerId( @Context HttpServletRequest request, GetCouponsQueryInput getCouponsQueryInput ) throws ApplicationException
	{
		GetCouponsOutput getCouponsOutput = new GetCouponsOutput();
		try 
		{

			System.out.println(getCouponsQueryInput);

			/**
			 *  Get the logged user
			 */		
			Integer loginUserId = LoginSession.getLoginUserId(request);
			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			/**
			 * Get customer coupons for sale with dynamicQueryParameters
			 */		

			DynamicQueryParameters dynamicQueryParameters = new DynamicQueryParameters();

			String couponTypeId = getCouponsQueryInput.getCouponTypeId();
			if ( couponTypeId != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.COUPON_TYPE_ID	, couponTypeId );
			}

			String fromPrice = getCouponsQueryInput.getFromPrice();
			if ( fromPrice != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.FROM_PRICE	, fromPrice );
			}

			String toPrice = getCouponsQueryInput.getToPrice();
			if ( toPrice != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.TO_PRICE	, toPrice );
			}

			String fromDate = getCouponsQueryInput.getFromDate();
			if ( fromDate != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.FROM_DATE	, fromDate );
			}

			String toDate = getCouponsQueryInput.getToDate();
			if ( toDate != null )
			{
				dynamicQueryParameters.add(DynamicQueryParameters.TO_DATE	, toDate );
			}

			CouponsBlo couponsBlo = new CouponsBlo();
			List<Coupon> coupons = couponsBlo.getCouponsForSaleByCustomerId(loggedUser, dynamicQueryParameters);
			getCouponsOutput.setCoupons(coupons);
		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			getCouponsOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));
		}		
		return getCouponsOutput;
	}

	
}
