package com.raviv.coupons.rest.api.inputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for getCompanyCouponsQuery
 * @author raviv
 *
 */
@XmlRootElement
public class GetCouponsQueryInput  {

	/**
	 * Default constructor. deserealization
	 */
	public GetCouponsQueryInput() {}

	private	String		couponTypeId;
	private	String		fromPrice;
	private	String		toPrice;
	private	String		fromDate;
	private	String		toDate;
	
	
	@Override
	public String toString() {
		return "GetCouponsQueryInput [couponTypeId=" + couponTypeId + ", fromPrice=" + fromPrice + ", toPrice="
				+ toPrice + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
