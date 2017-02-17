package com.raviv.coupons.rest.api.outputs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.raviv.coupons.beans.Company;

/**
 * Java bean for service output
 * @author raviv
 *
 */
@XmlRootElement
public class GetAllCompanysOutput  {
	
	private ServiceStatus				serviceStatus;
	private List<Company>				companys;

	
	public GetAllCompanysOutput() 
	{
		super();
		this.serviceStatus = new ServiceStatus();
		this.companys = null;		
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<Company> getCompanys() {
		return companys;
	}

	public void setCompanys(List<Company> companys) {
		this.companys = companys;
	}
	
	
}
