package com.raviv.coupons.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ClientErrorMessage {

	private Map<Integer,String> mapErrorMessages;
	
	private static ClientErrorMessage singletonInstance;

	static 	{ singletonInstance = new ClientErrorMessage(); }

	private ClientErrorMessage()
	{
		this.mapErrorMessages = new HashMap<Integer,String>();

	 	 //CUSTOMER_NAME_ALREADY_EXISTS	( 100)
		mapErrorMessages.put(100, "CUSTOMER NAME ALREADY EXISTS");

		//USER_LOGIN_NAME_ALREADY_EXISTS( 110)
		mapErrorMessages.put(110, "User already taken");
		
		//COUPON_NAME_ALREADY_EXISTS		( 200)
		mapErrorMessages.put(200, "COUPON NAME ALREADY EXISTS");

		//INVALID_EMAIL					( 300)
		mapErrorMessages.put(300, "Invalid email");

		//GENERAL_ERROR					( 400)
		mapErrorMessages.put(400, "Action failed");

		//DAO_CREATE_ERROR				( 500)
		mapErrorMessages.put(500, "ACTION FAILED");

		//DAO_GET_ERROR					( 600)
		mapErrorMessages.put(600, "ACTION FAILED");

		//DAO_UPDATE_ERROR				( 700)
		mapErrorMessages.put(700, "ACTION FAILED");

		//DAO_DELETE_ERROR				( 800)
		mapErrorMessages.put(800, "ACTION FAILED");

		//LOGIN_ERROR					( 900)
		mapErrorMessages.put(900, "Login failed");

		//COUPON_OUT_OF_STOCK_ERROR		(1000)
		mapErrorMessages.put(1000, "Coupon is out of stock");

		//DUPLICATE_COUPON_FOR_CUSTOMER	(1100)
		mapErrorMessages.put(1100, "Coupon already exists");

		//INVALID_USER_PROFILE_ID		(1200)
		mapErrorMessages.put(1200, "Invalid user profile");

		//BLO_GET_ERROR					(1300)
		mapErrorMessages.put(1300, "ACTION FAILED");

		//COUPON_IS_NOT_FOR_SALE			(1400)
		mapErrorMessages.put(1400, "Coupon is not for sale");

	}
	
	public static ClientErrorMessage 	getInstance()  {
		return singletonInstance;
	}


	public  String 	getErrorMessage(int errorCode)  
	{
		return mapErrorMessages.get(errorCode);
	}


}
