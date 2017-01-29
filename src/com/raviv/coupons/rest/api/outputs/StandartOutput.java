package com.raviv.coupons.rest.api.outputs;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Java bean for standart output
 * @author raviv
 *
 */
@XmlRootElement
public class StandartOutput  {

	private	boolean                   	success;
	private	String                   	errorMessage;
	
	@Override
	public String toString() {
		return "StandartOutput [success=" + success + ", errorMessage=" + errorMessage + "]";
	}

	public StandartOutput() 
	{
		super();
		this.success = true;
		errorMessage = null;
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
	
}
