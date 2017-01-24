package com.raviv.coupons.rest.api.inputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for GetCustomerCouponsQueryInput
 * @author raviv
 *
 */
@XmlRootElement
public class GetCustomerCouponsQueryInput  {

	/**
	 * Default constructor. deserealization
	 */
	public GetCustomerCouponsQueryInput() {}

	private	String		couponTypeId;
	private	String		fromPrice;
	private	String		toPrice;
	
	@Override
	public String toString() {
		return "GetCustomerCouponsQueryInput [couponTypeId=" + couponTypeId + ", fromPrice=" + fromPrice + ", toPrice="
				+ toPrice + "]";
	}

	public String getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(String couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public String getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(String fromPrice) {
		this.fromPrice = fromPrice;
	}

	public String getToPrice() {
		return toPrice;
	}

	public void setToPrice(String toPrice) {
		this.toPrice = toPrice;
	}
	
	
	

}
