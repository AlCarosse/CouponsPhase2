package com.raviv.coupons.rest.api.outputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for service status
 * @author raviv
 *
 */
@XmlRootElement
public class ServiceStatus  {

	private	boolean                   	success;
	private	long                   		errorCode;
	private	String                   	errorMessage;
	
	
	@Override
	public String toString() {
		return "ServiceStatus [success=" + success + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage
				+ "]";
	}

	public ServiceStatus() 
	{
		super();
		this.success = true;
		this.errorCode = 0;
		this.errorMessage = "";
	}

	public ServiceStatus(long errorCode, String errorMessage) {
		super();
		this.success = false;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	
}
