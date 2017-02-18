package com.raviv.coupons.rest.api.outputs;

import javax.xml.bind.annotation.XmlRootElement;
import com.raviv.coupons.beans.Company;

/**
 * Java bean for service output
 * @author raviv
 *
 */
@XmlRootElement
public class GetCompanyOutput  {
	
	private ServiceStatus	serviceStatus;
	private Company			company;

	
	public GetCompanyOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
		this.company = null;		
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
