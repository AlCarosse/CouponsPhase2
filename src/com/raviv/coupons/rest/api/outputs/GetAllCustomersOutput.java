package com.raviv.coupons.rest.api.outputs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.raviv.coupons.beans.Customer;

/**
 * Java bean for service output
 * @author raviv
 *
 */
@XmlRootElement
public class GetAllCustomersOutput  {
	
	private ServiceStatus				serviceStatus;
	private List<Customer>				customers;

	
	public GetAllCustomersOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
		this.customers = null;		
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	
}
