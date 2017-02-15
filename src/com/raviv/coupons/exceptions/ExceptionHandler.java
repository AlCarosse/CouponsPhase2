package com.raviv.coupons.exceptions;


import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.rest.api.outputs.ServiceStatus;
 
public class ExceptionHandler 
{
	
	public static ServiceStatus createServiceStatus(Throwable exception)
	{
		ServiceStatus 	serviceStatus = null;
		ErrorType 		errorType     = null;
		int				errorCode;
		String			errorMessage  = null;
		
    	System.out.println("Just entered into the exceptions mapper");
    	if ( exception instanceof ApplicationException )
    	{
    		ApplicationException e = (ApplicationException) exception;	
    		errorType = e.getErrorType();
    		if ( errorType != null )
    		{
    			errorCode 		= errorType.getInternalErrorCode();
    			//errorMessage 	= e.getMessage();
    			errorMessage = ClientErrorMessage.getInstance().getErrorMessage(errorCode);
    		}
    		else
    		{
        		errorType = ErrorType.GENERAL_ERROR;
        		errorCode 		= errorType.getInternalErrorCode();
        		errorMessage 	= "General failure";    			
    		}
    		serviceStatus = new ServiceStatus(errorCode, errorMessage);
    	}
    	else
    	{
    		errorType = ErrorType.GENERAL_ERROR;
    		errorCode 		= errorType.getInternalErrorCode();
    		errorMessage 	= "General failure";
    		serviceStatus = new ServiceStatus(errorCode, errorMessage);    		
    	}
		
    	System.out.println(serviceStatus);
    	
    	return serviceStatus;
	}
	
}
