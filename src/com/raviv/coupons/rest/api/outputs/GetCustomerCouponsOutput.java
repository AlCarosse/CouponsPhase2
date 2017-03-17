package com.raviv.coupons.rest.api.outputs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.CustomerCoupon;

/**
 * Java bean for service output
 *
 */
@XmlRootElement
public class GetCustomerCouponsOutput  {
	
	private ServiceStatus				serviceStatus;
	private List<CustomerCoupon>		customerCoupons;

	
	public GetCustomerCouponsOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
		this.customerCoupons = null;		
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<CustomerCoupon> getCustomerCoupons() {
		return customerCoupons;
	}

	public void setCustomerCoupons(List<CustomerCoupon> customerCoupons) {
		this.customerCoupons = customerCoupons;
	}

}
