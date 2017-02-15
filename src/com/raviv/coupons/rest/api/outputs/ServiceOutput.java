package com.raviv.coupons.rest.api.outputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for service output
 * @author raviv
 *
 */
@XmlRootElement
public class ServiceOutput  {
	
	private ServiceStatus				serviceStatus;
	

	public ServiceOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
