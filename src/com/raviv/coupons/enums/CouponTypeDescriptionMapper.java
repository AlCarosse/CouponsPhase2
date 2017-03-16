package com.raviv.coupons.enums;

import java.util.HashMap;
import java.util.Map;

public class CouponTypeDescriptionMapper {

	private Map<Integer,String> map;
	
	private static CouponTypeDescriptionMapper singletonInstance;

	static 	{ singletonInstance = new CouponTypeDescriptionMapper(); }

	private CouponTypeDescriptionMapper()
	{
		this.map = new HashMap<Integer,String>();

		map.put(CouponType.HOLIDAY.getCouponType(), "HOLIDAY");
		map.put(CouponType.RESTAURANT.getCouponType(), "RESTAURANT");
		map.put(CouponType.ENTERTAINMENT.getCouponType(), "ENTERTAINMENT");
		map.put(CouponType.TRAVELLING.getCouponType(), "TRAVELLING");

	}
	
	public static CouponTypeDescriptionMapper 	getInstance()  {
		return singletonInstance;
	}

	public  String 	getCouponTypeDescription(int couponType)  
	{
		return map.get(couponType);
	}

}
