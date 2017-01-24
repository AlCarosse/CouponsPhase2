package com.raviv.coupons.tests.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CouponsBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.exceptions.ApplicationException;

public class SystemCustomerBlo3010BuyCouponTest {

	public static void main(String[] args) throws ApplicationException {
		UsersBlo usersBlo = new UsersBlo();
		CouponsBlo couponsBlo = new CouponsBlo();
		
		/**
		 *  Login as customer
		 */		
		//User loggedUser = usersBlo.login("admin", "1234");
		//User loggedUser = usersBlo.login("comp1", "1234");
		User loggedUser = usersBlo.login("cust4", "1234");

		/**
		 * Buy coupon
		 */
		//customerCouponBlo.buyCoupon( loggedUser , 9 );

		couponsBlo.buyCoupon( loggedUser , 10 );

		couponsBlo.buyCoupon( loggedUser , 11 );


	}

}
