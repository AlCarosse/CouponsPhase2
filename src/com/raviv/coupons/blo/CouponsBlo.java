package com.raviv.coupons.blo;


import java.io.File;
import java.util.List;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.CustomerCoupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.dao.CustomerCouponDao;
import com.raviv.coupons.dao.interfaces.ICouponsDao;
import com.raviv.coupons.dao.interfaces.ICustomerCouponDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.rest.api.UploadCouponImageFileServlet;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Coupons business logic
 * 
 * @author raviv
 *
 */
public class CouponsBlo {

	public CouponsBlo()
	{
	}

	public  void 			createCoupon( User loggedUser , Coupon coupon ) throws ApplicationException 
	{
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();
		// Inject transaction manager to DAO via constructors
		ICouponsDao		couponsDao	= new CouponsDao ( jdbcTransactionManager );

		// =====================================================
		// Verify duplicate coupon title
		// =====================================================
		if ( couponsDao.isExistsCouponTitle( coupon.getCouponTitle() ) )
		{
			throw new ApplicationException(ErrorType.COUPON_TITLE_ALREADY_EXISTS, "Dupliacte coupon title : " + coupon.getCouponTitle() );			
		}


		try
		{				
			// =====================================================
			// Create new coupon
			// =====================================================
			coupon.setCreatedByUserId( loggedUser.getUserId() );
			coupon.setUpdatedByUserId( loggedUser.getUserId() );
			couponsDao.createCoupon(coupon);

			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("CouponsBlo : createCoupon created coupon");
			System.out.println(coupon);
		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================
			jdbcTransactionManager.rollback();
			throw (e); 
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
	}

	public  void 			deleteCoupon( User loggedUser , long couponId) throws ApplicationException 
	{		
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();
		// Inject transaction manager to DAO via constructors
		ICouponsDao couponsDao	= new CouponsDao( jdbcTransactionManager );

		Coupon coupon = couponsDao.getCoupon(couponId);
		String imageFileName = UploadCouponImageFileServlet.ROOT_DIR +  coupon.getImageFileName();

		try
		{
			// =====================================================
			// Delete coupon and related customer coupons
			// CUSTOMER_COUPON has FK to COUPONS using coupon id, with delete Cascade
			// =====================================================			
			couponsDao.deleteCoupon(couponId);

			// =====================================================
			// Delete coupon image file from hard drive
			// =====================================================			
			File imageFile = new File(imageFileName);
			if ( imageFile.exists() )
			{
				imageFile.delete();
			}   

			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("CouponsBlo : deleteCoupon deleted couponId : " + couponId );
		}
		catch (Exception e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================
			jdbcTransactionManager.rollback();
			throw (e); 
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	

	}

	public  void 			updateCoupon( User loggedUser , Coupon inputCoupon) throws ApplicationException 
	{
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructors
		ICouponsDao couponsDao	= new CouponsDao( jdbcTransactionManager );

		try
		{
			// =====================================================
			// Get coupon from data layer
			// =====================================================			
			Coupon coupon;
			long couponId = inputCoupon.getCouponId();
			coupon = couponsDao.getCoupon( couponId );

			if ( coupon == null )
			{
				throw new ApplicationException(ErrorType.BLO_GET_ERROR, "Failed to get coupon with id : " + couponId + " " + "" );
			}

			// =====================================================
			// Set coupon: end date , price
			// =====================================================

			// Prepare inputs
			coupon.setUpdatedByUserId( loggedUser.getUserId()    );

			coupon.setCouponEndDate  ( inputCoupon.getCouponEndDate() );
			coupon.setCouponPrice    ( inputCoupon.getCouponPrice()   );


			// =====================================================
			// Update coupon in data layer
			// =====================================================			
			couponsDao.updateCoupon(coupon);

			// =====================================================
			// Commit transaction
			// =====================================================

			jdbcTransactionManager.commit();


			PrintUtils.printHeader("updateCoupon updated coupon");
			System.out.println(coupon);

		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================

			jdbcTransactionManager.rollback();

			throw (e); 

		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
	}

	public  List<Coupon>	getCompanyCoupons( User loggedUser ) throws ApplicationException 
	{
		PrintUtils.printHeader ("CouponsBlo: getCompanyCoupons");
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Get company by user id
		// =====================================================		
		CompanysBlo companysBlo = new CompanysBlo();
		Company 	company 	= companysBlo.getCompany( loggedUser );
		long 		companyId 	= company.getCompanyId();

		// =====================================================
		// Get company coupons from data layer
		// =====================================================				
		CouponsDao couponsDao = new CouponsDao();
		List<Coupon> couponsList 	= couponsDao.getCouponsByCompanyId(companyId);

		for ( Coupon coupon : couponsList )
		{
			System.out.println(coupon);
		}

		return couponsList;
	}

	public  List<Coupon>	getCompanyCouponsQuery( User loggedUser , DynamicQueryParameters dynamicQueryParameters) throws ApplicationException 
	{
		PrintUtils.printHeader ("CouponsBlo: getCompanyCouponsQuery");
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCompanyProfileId(loggedUser);

		// =====================================================
		// Get company by user id
		// =====================================================		
		CompanysBlo companysBlo = new CompanysBlo();
		Company 	company 	= companysBlo.getCompany( loggedUser );
		long 		companyId 	= company.getCompanyId();

		// =====================================================
		// Get company coupons from data layer
		// =====================================================				
		CouponsDao couponsDao = new CouponsDao();
		List<Coupon> couponsList 	= couponsDao.getCouponsByCompanyIdAndDynamicFilter( companyId , dynamicQueryParameters);
		for ( Coupon coupon : couponsList )
		{
			System.out.println(coupon);
		}
		return couponsList;
	}

	public	synchronized void	buyCoupon( User loggedUser , long couponId ) throws ApplicationException 
	{
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		//======================================================
		// Get customer details by logged user
		//======================================================	
		CustomersBlo	customersBlo	= new CustomersBlo();
		Customer		customer 		= customersBlo.getCustomer(loggedUser);

		// =====================================================
		// Get coupon for sale from data layer
		// Effective and in stock
		// =====================================================
		CouponsDao	couponsDao			= new CouponsDao();
		Coupon		coupon = couponsDao.getCouponForSale(couponId);
		if ( coupon == null )
		{
			throw new ApplicationException(ErrorType.COUPON_IS_NOT_FOR_SALE, null , "Coupon is not for sale couponId : " + couponId);
		}

		// =====================================================
		// Check if customer was buying this coupon already.
		// =====================================================
		ICustomerCouponDao		customerCouponDao;
		customerCouponDao	= new CustomerCouponDao ();

		CustomerCoupon	customerCoupon = customerCouponDao.getCustomerCoupon( customer.getCustomerId(), couponId );
		if ( customerCoupon != null ) 
		{
			throw new ApplicationException(ErrorType.DUPLICATE_COUPON_FOR_CUSTOMER, null
					, "CustomerBlo : buyCoupon Failed. Duplicate coupon for customer, couponId : " + couponId  );
		}

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructors
		customerCouponDao	= new CustomerCouponDao ( jdbcTransactionManager );

		try
		{				
			// =====================================================
			// Create new customer coupon with the new coupon id
			// =====================================================
			customerCoupon = new CustomerCoupon ( customer.getCustomerId(), couponId );
			customerCoupon.setCreatedByUserId( loggedUser.getUserId() );
			customerCoupon.setUpdatedByUserId( loggedUser.getUserId() );
			// data layer action
			customerCouponDao.createCustomerCoupon(customerCoupon);

			// =====================================================
			// Update coupons stock and take one coupon away
			// =====================================================
			int  couponsInStock = coupon.getCouponsInStock();
			couponsInStock--;
			coupon.setCouponsInStock( couponsInStock );
			coupon.setUpdatedByUserId( loggedUser.getUserId() );
			// data layer action
			couponsDao.updateCoupon(coupon);

			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();

			PrintUtils.printHeader("CustomerBlo : buyCoupon success.");
			//System.out.println(coupon);
		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================

			jdbcTransactionManager.rollback();

			throw (e); 

		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
	}

	public  List<CustomerCoupon>getCustomerCoupons( User loggedUser ) throws ApplicationException 
	{
		PrintUtils.printHeader ("CustomerCouponBlo: getCustomerCoupons");
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		//==============================================
		// Get customer details with the user id
		//==============================================	
		CustomersBlo 	customersBlo	= new CustomersBlo();
		Customer		customer 		= customersBlo.getCustomer(loggedUser);

		//==============================================
		// Get customer coupons from data layer
		//==============================================	
		CustomerCouponDao customerCouponDao = new CustomerCouponDao();
		long 					customerId		= customer.getCustomerId();
		List<CustomerCoupon>	customerCoupons	= customerCouponDao.getCustomerCouponsByCustomerId(customerId);

		for ( CustomerCoupon customerCoupon : customerCoupons )
		{
			System.out.println(customerCoupon);
		}
		return customerCoupons;
	}

	public  List<CustomerCoupon>getCustomerCouponsQuery( User loggedUser , DynamicQueryParameters dynamicQueryParameters ) throws ApplicationException 
	{
		PrintUtils.printHeader ("CustomerCouponBlo: getCustomerCouponsQuery");
		// =====================================================
		// Verify customer profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		//======================================================
		// Get customer details with the user id
		//======================================================	
		CustomersBlo 	customersBlo	= new CustomersBlo();
		Customer		customer 		= customersBlo.getCustomer(loggedUser);

		//======================================================
		// Get customer coupons from data layer
		//======================================================
		CustomerCouponDao customerCouponDao = new CustomerCouponDao();
		long customerId 		= customer.getCustomerId();
		List<CustomerCoupon>	customerCoupons= customerCouponDao.getCustomerCouponsByCustomerIdAndDynamicFilter( customerId , dynamicQueryParameters);

		for ( CustomerCoupon customerCoupon : customerCoupons )
		{
			System.out.println(customerCoupon);
		}

		return customerCoupons;
	}

	public  List<Coupon>	getCouponsForSaleByCustomerId( User loggedUser , DynamicQueryParameters dynamicQueryParameters) throws ApplicationException 
	{
		PrintUtils.printHeader ("CouponsBlo: getCouponsForSaleByCustomerId");
		// =====================================================
		// Verify company profile id
		// =====================================================
		ProfileIdVerifier.verifyCustomerProfileId(loggedUser);

		// =====================================================
		// Get customer by user id
		// =====================================================		
		CustomersBlo customersBlo = new CustomersBlo();
		Customer 	 customer 	  = customersBlo.getCustomer( loggedUser );
		long 		 customerId   = customer.getCustomerId();

		// =====================================================
		// Get customer coupons from data layer
		// =====================================================				
		CouponsDao couponsDao = new CouponsDao();
		List<Coupon> couponsList 	= couponsDao.getCouponsForSaleByCustomerIdAndDynamicFilter( customerId, dynamicQueryParameters);
		for ( Coupon coupon : couponsList )
		{
			System.out.println(coupon);
		}
		return couponsList;
	}

	
}
