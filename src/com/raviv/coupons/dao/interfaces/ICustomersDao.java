package com.raviv.coupons.dao.interfaces;

import com.raviv.coupons.beans.Customer;
import com.raviv.coupons.exceptions.ApplicationException;

public interface ICustomersDao {

	public void 		createCustomer(Customer 	customer	) throws ApplicationException;

	public Customer 	getCustomer	  (long 		customerId	) throws ApplicationException;

	public void 		updateCustomer(Customer 	customer	) throws ApplicationException;

	public void 		deleteCustomer(long			customerId	) throws ApplicationException;

	public boolean		isDuplicateCustomerNameExists(long customerId, String customerName) throws ApplicationException;

	public boolean		isCustomerNameExists(String customerName) throws ApplicationException;

}
