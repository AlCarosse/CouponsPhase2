package com.raviv.coupons.blo;

/*
import java.util.List;
import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.beans.CustomerCoupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.CustomerCouponDao;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;
*/

/**
 * 
 * Customer Coupon business logic
 * 
 * 
 * @author raviv
 *
 */
public class CustomerCouponBlo {

	//private	CustomerCouponDao		customerCouponDao;
	
	public CustomerCouponBlo()
	{
		//this.customerCouponDao = new CustomerCouponDao();
	}
	/*
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
		long 					customerId		= customer.getCustomerId();
		List<CustomerCoupon>	customerCoupons	= this.customerCouponDao.getCustomerCouponsByCustomerId(customerId);

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
		long customerId 		= customer.getCustomerId();
		List<CustomerCoupon>	customerCoupons= customerCouponDao.getCustomerCouponsByCustomerIdAndDynamicFilter( customerId , dynamicQueryParameters);
										
		for ( CustomerCoupon customerCoupon : customerCoupons )
		{
			System.out.println(customerCoupon);
		}
		
		return customerCoupons;
	}
*/
}
