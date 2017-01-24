package com.raviv.coupons.rest.api.inputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for create coupon
 * @author raviv
 *
 */
@XmlRootElement
public class CreateCouponInput  {

	/**
	 * Default constructor deserealization
	 */
	public CreateCouponInput() {}

	private	String		couponTitle;
	private	String		couponStartDate;
	private	String		couponEndDate;
	private	int			couponsInStock;
	private	int			couponTypeId;
	private	String		couponMessage;
	private	double		couponPrice;
	private	String		imageFileName;
	
	@Override
	public String toString() {
		return "CreateCouponInput [couponTitle=" + couponTitle + ", couponStartDate="
				+ couponStartDate + ", couponEndDate=" + couponEndDate + ", couponsInStock=" + couponsInStock
				+ ", couponTypeId=" + couponTypeId + ", couponMessage=" + couponMessage + ", couponPrice=" + couponPrice
				+ ", imageFileName=" + imageFileName + "]";
	}


	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getCouponStartDate() {
		return couponStartDate;
	}

	public void setCouponStartDate(String couponStartDate) {
		this.couponStartDate = couponStartDate;
	}

	public String getCouponEndDate() {
		return couponEndDate;
	}

	public void setCouponEndDate(String couponEndDate) {
		this.couponEndDate = couponEndDate;
	}

	public int getCouponsInStock() {
		return couponsInStock;
	}

	public void setCouponsInStock(int couponsInStock) {
		this.couponsInStock = couponsInStock;
	}

	public int getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(int couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public String getCouponMessage() {
		return couponMessage;
	}

	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}

	public double getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	
}
