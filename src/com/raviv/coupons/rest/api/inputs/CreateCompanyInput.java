package com.raviv.coupons.rest.api.inputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for create ccompany
 * @author raviv
 *
 */
@XmlRootElement
public class CreateCompanyInput  {

	private	String                   	companyName;
	private	String                   	companyEmail;
	private	String                   	userName;
	private	String                   	loginName;
	private	String                   	loginPassword;	

	@Override
	public String toString() {
		return "CreateCompanyInput [companyName=" + companyName + ", companyEmail=" + companyEmail + ", userName="
				+ userName + ", loginName=" + loginName + ", loginPassword=" + loginPassword + "]";
	}

	public CreateCompanyInput() 
	{
		super();
	}

	public CreateCompanyInput(String companyName, String companyEmail) 
	{
		super();
		this.companyName	= companyName;
		this.companyEmail 	= companyEmail;
	}


	
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

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	
	
}
