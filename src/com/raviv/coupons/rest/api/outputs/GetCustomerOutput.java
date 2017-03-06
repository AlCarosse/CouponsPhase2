package com.raviv.coupons.rest.api.outputs;

import javax.xml.bind.annotation.XmlRootElement;
import com.raviv.coupons.beans.Customer;

/**
 * Java bean for service output
 * @author raviv
 *
 */
@XmlRootElement
public class GetCustomerOutput  {
	
	private ServiceStatus	serviceStatus;
	private Customer		customer;

	
	public GetCustomerOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
		this.customer = null;		
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
