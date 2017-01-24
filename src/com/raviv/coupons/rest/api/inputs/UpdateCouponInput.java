package com.raviv.coupons.rest.api.inputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for update coupon
 * @author raviv
 *
 */
@XmlRootElement
public class UpdateCouponInput  {

	/**
	 * Default constructor. deserealization
	 */
	public UpdateCouponInput() {}

	private	long		couponId;
	private	String		couponEndDate;
	private	double		couponPrice;

	@Override
	public String toString() {
		return "UpdateCouponInput [couponId=" + couponId + ", couponEndDate=" + couponEndDate + ", couponPrice="
				+ couponPrice + "]";
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getCouponEndDate() {
		return couponEndDate;
	}

	public void setCouponEndDate(String couponEndDate) {
		this.couponEndDate = couponEndDate;
	}

	public double getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}
	

	
	
}
