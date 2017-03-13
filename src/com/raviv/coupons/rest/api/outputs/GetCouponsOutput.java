package com.raviv.coupons.rest.api.outputs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.raviv.coupons.beans.Coupon;

/**
 * Java bean for service output
 *
 */
@XmlRootElement
public class GetCouponsOutput  {
	
	private ServiceStatus				serviceStatus;
	private List<Coupon>				coupons;

	
	public GetCouponsOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
		this.coupons = null;		
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	
	
}
