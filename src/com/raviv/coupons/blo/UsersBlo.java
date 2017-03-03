package com.raviv.coupons.blo;

import com.raviv.coupons.beans.User;
import com.raviv.coupons.dao.UsersDao;
import com.raviv.coupons.dao.interfaces.IUsersDao;
import com.raviv.coupons.dao.utils.JdbcTransactionManager;
import com.raviv.coupons.enums.ErrorType;
import com.raviv.coupons.exceptions.ApplicationException;
import com.raviv.coupons.utils.PrintUtils;

/**
 * 
 * Users business logic
 *
 */
public class UsersBlo {
	
	private UsersDao usersDao;

	public UsersBlo()
	{
		usersDao = new UsersDao();
	}
	
	public  User login(String loginName, String loginPassword) throws ApplicationException 
	{
		//==================================================
		// Get user from data layer
		//==================================================
		User 		user;				
		user = this.usersDao.getUserByLoginNameLoginPassword( loginName, loginPassword );
		if ( user == null )
		{
			throw new ApplicationException(ErrorType.LOGIN_ERROR, null
					, "Login error. loginName : " + loginName + ",  loginPassword : " + loginPassword );
		}
		PrintUtils.printHeader("UsersBlo : User logged in");		
		System.out.println(user);
		
		return user;
	}

	public  User getUserById(int userId) throws ApplicationException 
	{
		//==================================================
		// Get user from data layer
		//==================================================
		User 		user;				
		user = this.usersDao.getUser(userId);
		if ( user == null )
		{
			throw new ApplicationException(ErrorType.GENERAL_ERROR
					, "UsersBlo : getUserById error. userId : " + userId  );
		}
		//PrintUtils.printHeader("UsersBlo : User logged in");		
		//System.out.println(user);
		
		return user;
	}

	public  User getAdminUser() throws ApplicationException 
	{
		//==================================================
		// Get admin user from data layer
		//==================================================
		User 		user;				
		user = this.usersDao.getUser(1);
		if ( user == null )
		{
			throw new ApplicationException(ErrorType.GENERAL_ERROR
					, "UsersBlo : getUserById error. userId : " + 1  );
		}
		//PrintUtils.printHeader("UsersBlo : User logged in");		
		//System.out.println(user);
		
		return user;
	}
	
	public  void deleteUser( User loggedUser , long userId) throws ApplicationException 
	{
		// =====================================================
		// Verify admin profile id
		// =====================================================
		ProfileIdVerifier.verifyAdminProfileId(loggedUser);

		// =====================================================
		// Start transaction by creating JdbcTransactionManager
		// =====================================================		
		JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager();

		// Inject transaction manager to DAO via constructor
		IUsersDao usersDao	= new UsersDao( jdbcTransactionManager );
		
		try
		{
			// =====================================================
			// Delete user
			// =====================================================			
			usersDao.deleteUser(userId);
			
			// =====================================================
			// Commit transaction
			// =====================================================
			jdbcTransactionManager.commit();
			PrintUtils.printHeader("deleteUser deleted userId : " + userId );
			
		}
		catch (ApplicationException e)
		{
			// =====================================================
			// Rollback transaction
			// =====================================================

			jdbcTransactionManager.rollback();
			
			throw (e); 
			
		}
		finally
		{
			jdbcTransactionManager.closeConnection();
		}	
				
	}// deleteUser

	
}