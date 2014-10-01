package com.frnmz.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frnmz.dao.GenericDAO;
import com.frnmz.dao.NotificationDAO;
import com.frnmz.dao.RecordDAO;
import com.frnmz.dao.UserDAO;
import com.frnmz.model.Notification;
import com.frnmz.model.Record;
import com.frnmz.model.UserInfo;
import com.frnmz.services.Mailer;
import com.frnmz.utils.Utils;

@SuppressWarnings("all")
@Controller
public class LoginController{	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private RecordDAO recordDAO;
	
	@Autowired
	private NotificationDAO notificationDAO;
		
	@Autowired
	private Mailer mailer;
	
	private ModelAndView mav; 
	public LoginController(){
		mav = new ModelAndView();
	}
	
	@RequestMapping(value="/login/onLoginFailed.htm")
	public ModelAndView onLoginFailed(HttpServletRequest request){
		request.setAttribute("msgType", "Error");
		if(request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") instanceof DisabledException){
			request.setAttribute("msg", "! Login Failed : Account "+request.getSession().getAttribute("SPRING_SECURITY_LAST_USERNAME")+" is disabled");
		}else{
			request.setAttribute("msg", "! Login Failed : Invalid credentials");
		}
		
		mav.setViewName("loginView");
		return mav;
	}
	
	@RequestMapping(value="/login/onLogoutSuccess.htm")
	public ModelAndView onLogoutSuccess(HttpServletRequest request){
		request.setAttribute("msgType", "Success");
		request.setAttribute("msg", "Logged Out Successfully.");
		
		mav.setViewName("loginView");
		return mav;
	}
	
	@RequestMapping(value="/login/toForgotPassword.htm")
	public ModelAndView toForgotPassword(HttpServletRequest request){
		request.setAttribute("mode", "FORGOT_PASSWORD");
		mav.setViewName("forgotChangePassword");
		return mav;
	}
	
	@RequestMapping(value="/login/sendPassword.htm")
	public ModelAndView sendPassword(HttpServletRequest request){
		String email = request.getParameter("email");
		UserInfo userInfo = userDao.getUserInfoByEmail(email);
		request.setAttribute("msgType", "Error");
		request.setAttribute("mode", "FORGOT_PASSWORD");
		mav.setViewName("forgotChangePassword");
		if(userInfo != null && StringUtils.isNotBlank(userInfo.getPassword())){
			String status = "Password Recovery";
			String htmlMessage = Utils.getMessage(status, email, userInfo.getPassword());
			boolean isMailSent = mailer.sendEmail(email, "", "", "", status, htmlMessage, null,"");
			if(isMailSent){
				request.setAttribute("msgType", "Success");
				request.setAttribute("msg", "Please check your Mailbox for password");
				mav.setViewName("loginView");
			}else{
				request.setAttribute("msg", "Server Error Please Try Again Later.");
			}
		}else{
			request.setAttribute("msg", "Invalid Email Id");
		}
		
		return mav;
	}

	@RequestMapping(value="/guest/toViewFundDetails.htm")
	public ModelAndView toViewFundDetails(HttpServletRequest request){
		int balance = 0;
		List<Record> allRecords = recordDAO.getAllRecord();
		if(CollectionUtils.isNotEmpty(allRecords)){
			Collections.sort(allRecords);
			balance = allRecords.get(0).getBalance();
			Collections.reverse(allRecords);
		}
		mav.addObject("balance",balance);
		mav.addObject("recordList",allRecords);
		mav.setViewName("statement");
		return mav;
	}

	@RequestMapping(value="/guest/toViewMembers.htm")
	public ModelAndView toViewMembers(HttpServletRequest request){
		mav.addObject("userList",userDao.getAllUsers());
		mav.setViewName("listUsers");
		return mav;
	}

	@RequestMapping(value="/guest/toViewNotificationDetails.htm")
	public ModelAndView toViewNotificationDetails(HttpServletRequest request){
		Notification notification = notificationDAO.getNotificationByDate(new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));
		mav.addObject("notification", notification);
		mav.setViewName("fridayNotification");
		return mav;
	}

	@RequestMapping(value="/guest/confirmAvailablity.htm")
	public ModelAndView confirmAvailablity(HttpServletRequest request){
		mav.addObject("msgType", "Error");
		mav.addObject("msg", "Link expired");
		
		Notification notification = notificationDAO.getNotification(request.getParameter("NID"));
		String currDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		
		if(null != notification && currDate.equals(notification.getDate())){
			String nameEmail = request.getParameter("FULL_NAME_AND_EMAIL_ID");
			String hid = request.getParameter("HID");
			if(StringUtils.isNotBlank(nameEmail) && StringUtils.isNotBlank(hid)){
				String hashCode = Utils.getHash(nameEmail, "sha1");
				if(hid.equals(hashCode)){
					if(!notification.getLikes().contains(nameEmail)){
						notification.getLikes().add(nameEmail);
						boolean bool = genericDAO.updateObject(notification);
						if(bool){
							mav.addObject("msg", "Thanks for the confirmation");
							mav.addObject("msgType", "Success");
						} else {
							mav.addObject("msg", "Server busy. Please try later");
						}
					}
				}
			}
		}
		
		mav.setViewName("redirect:/");
		return mav;
	}
}