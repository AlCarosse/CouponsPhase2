package com.raviv.coupons.rest.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.raviv.coupons.beans.Company;
import com.raviv.coupons.beans.Coupon;
import com.raviv.coupons.beans.User;
import com.raviv.coupons.blo.CompanysBlo;
import com.raviv.coupons.blo.UsersBlo;
import com.raviv.coupons.dao.CouponsDao;
import com.raviv.coupons.exceptions.ExceptionHandler;
import com.raviv.coupons.rest.api.outputs.ServiceOutput;
import com.raviv.coupons.utils.LoginSession;


@WebServlet("/UploadCouponImageFileServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class UploadCouponImageFileServlet extends HttpServlet 
{
	static final long serialVersionUID = 1L;

	/**
	 * Name of the directory where uploaded files will be saved, relative to
	 * the web application directory.
	 */
	public static final String ROOT_DIR = "C:/Users/raviv/.babun/cygwin/home/raviv/workspaces/coupons/CouponsPhase2/WebContent/";

	private static final String SAVE_DIR = "images/companies/";

	/**
	 * handles file upload
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceOutput serviceOutput = new ServiceOutput();	
		try 
		{
			// ===============================================
			// Get company ID, we will use it as directory
			// to save coupon image
			// ===============================================
			Integer loginUserId = LoginSession.getLoginUserId(request);

			UsersBlo usersBlo = new UsersBlo();
			User loggedUser = usersBlo.getUserById( loginUserId);

			Company company = null;	
			CompanysBlo companysBlo = new CompanysBlo();
			company =  companysBlo.getCompany( loggedUser );
			
			long companyId = company.getCompanyId();
			
			
			// ===============================================
			// Create company directory to save coupon image
			// ===============================================
			String savePath = ROOT_DIR + SAVE_DIR + File.separator + companyId;

			// creates the company save directory if not exists
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}
			
			// time stamp for file name, that will insure each coupon image get unique name
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
			Date date = new Date();
			String ts = (dateFormat.format(date));
			
			String fileName = null;
			for (Part part : request.getParts()) {
				fileName = extractFileName(part);
				// refines the fileName in case it is an absolute path
				fileName = new File(fileName).getName();
				
				String fileExtention;
				int i = fileName.lastIndexOf('.');
				if (i > 0) {
					fileExtention = fileName.substring(i+1);
					fileName = ts + "." + fileExtention;
				}
				else{
					fileName = ts;
				}
				
				
				part.write(savePath + File.separator + fileName);
				
				String serverImageDir = getImageDirOnServer(request) + File.separator + companyId;
				part.write(serverImageDir + File.separator + fileName);
				
				
			}

			// ===================================================
			// Get the coupon for updating image file name
			// ===================================================
			CouponsDao couponDao = new CouponsDao();			
			Coupon coupon = couponDao.getCouponByCompanyIdForImageUpload (company.getCompanyId());

			// ===================================================
			// Update the coupon with image file name
			// ===================================================
			String imgFileName = SAVE_DIR + company.getCompanyId() + "/"+ fileName;			
			coupon.setImageFileName(imgFileName);
			couponDao.updateCoupon(coupon);

		}
		catch (Throwable t) 
		{
			t.printStackTrace();
			serviceOutput.setServiceStatus(ExceptionHandler.createServiceStatus(t));

		}		
		
		// ===================================================
		// Prepare JSON output
		// ===================================================		
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		JSONObject jsonOutput = new JSONObject();

		try 
		{
			jsonOutput.put("status", 200);
			jsonOutput.put("msg", "OK");

			JSONObject serviceStatus = new JSONObject();
			serviceStatus.put ( "success", "\""   + serviceOutput.getServiceStatus().isSuccess() + "\"" );
			serviceStatus.put ( "errorCode", "\"" + serviceOutput.getServiceStatus().getErrorCode() + "\"" );
			serviceStatus.put ( "errorMessage", "\"" + serviceOutput.getServiceStatus().getErrorMessage() + "\"" );

			jsonOutput.put( "serviceStatus", serviceStatus);

		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}

		// finally output the JSON string		
		out.print(jsonOutput.toString());

	}


	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}

	
	/**
	 * returns the path of the coupon images folder on server,
	 * if folder dorsn't exist, creates it
	 * */
	private String getImageDirOnServer(HttpServletRequest request) {
				
		ServletContext servletContext= request.getServletContext();
		String dirName = "/" + SAVE_DIR;
		
		// gets the real path of the actual folder on the server 
		String dirPath = servletContext.getRealPath(dirName);
		dirPath = dirPath.replace('\\', '/');
		File dir = new File(dirPath);
		
		// checks if the folder exists, if doesn't exist creates the folder
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dirPath;
	}
	
}
