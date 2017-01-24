package com.raviv.coupons.rest.api.inputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for create customer
 * @author raviv
 *
 */
@XmlRootElement
public class CreateCustomerInput  {

	private	String                   	customerName;
	private	String                   	userName;
	private	String                   	loginName;
	private	String                   	loginPassword;	


	
	@Override
	public String toString() {
		return "CreateCustomerInput [customerName=" + customerName + ", userName=" + userName + ", loginName="
				+ loginName + ", loginPassword=" + loginPassword + "]";
	}


	public CreateCustomerInput() {}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
