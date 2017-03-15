package com.raviv.coupons.tests;

import java.sql.Timestamp;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		long  l = Long.parseLong("1489442400000") ;
		
		Timestamp	 ts = new Timestamp(l);
		
		
		System.out.println(ts);

	}

}
