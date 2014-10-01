package com.frnmz.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frnmz.dao.UserDAO;
import com.frnmz.model.Notification;
import com.frnmz.model.UserInfo;
import com.frnmz.services.Mailer;

@Controller
public class TestController {
	@Autowired
	MongoOperations mongoOperations;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private Mailer mailer;
	
	@RequestMapping(value="/test/TESTCON.htm")
	public void testConnection(HttpServletResponse response, @RequestParam(required = false) String showTables) throws Exception{
		String outputMsg = "";
		try {
			mongoOperations.dropCollection(Notification.class);
			Set<String> names = mongoOperations.getCollectionNames();
			outputMsg = "<b style='color:green'>MONGODB Connected Successfully";
			
			if(StringUtils.isNotBlank(showTables) && "YES".equalsIgnoreCase(showTables)){
				outputMsg = outputMsg.concat("<br/>Tables: "+names);
			}
		} catch (Exception e) {
			outputMsg = "<b style='color:red'>MONGODB Connection Failed Please Check Connection Setting</b>";
		}
		
		response.getWriter().write(outputMsg);
	}
	
	@RequestMapping(value="/test/SHOWENVVAR.htm")
	public void showEnvironmentVars(HttpServletRequest request, HttpServletResponse response) throws Exception{
		StringBuffer outBuffer = new StringBuffer("<table border='1'>"
				+ "<tr><th align='left'>Environment Variable Name</th><th align='left'>Environment Variable Value</th></tr>");
		try {
			Map<String, String> envMap = System.getenv();
			
			for(Entry<String, String> envEntry : envMap.entrySet()){
				outBuffer.append("<tr><td align='left'>"+envEntry.getKey()+"</td><td align='left'>"+envEntry.getValue()+"</td></tr>");
			}
		} catch (Exception e) {}
		outBuffer.append("</table>");
		List<UserInfo> allActiveUsers = userDao.getAllActiveUsers();
		outBuffer.append(allActiveUsers.size()+"User in system");
		outBuffer.append(allActiveUsers.get(0)+"User in system");
		
		response.getWriter().write(outBuffer.toString());
	}
	
	@RequestMapping(value="/test/SHOWMONGODETAILS.htm")
	public void showMongoDetails(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		StringBuffer outBuffer = new StringBuffer("<table border='1'>"
				+ "<tr><th align='left'>Environment Variable Name</th><th align='left'>Mongo Variable Value</th></tr>");
		try {
			String host;
			int port;
			String username;
			String password;
			String dbname;
			
			if(null != System.getenv("VCAP_SERVICES")){
				JSONObject credentialObj = new JSONObject(System.getenv("VCAP_SERVICES")).getJSONArray("mongodb2-2.4.8").getJSONObject(0).getJSONObject("credentials");

				host = credentialObj.getString("hostname");
				port = credentialObj.getInt("port");
				username = credentialObj.getString("username");
				password = credentialObj.getString("password");
				dbname = credentialObj.getString("db");
			} else {
				host = "localhost";
				port = 27017;
				username = "";
				password = "";
				dbname = "sms";
			}

			outBuffer.append("<tr><td align='left'>Host</td><td align='left'>"+host+"</td></tr>");
			outBuffer.append("<tr><td align='left'>Port</td><td align='left'>"+port+"</td></tr>");
			outBuffer.append("<tr><td align='left'>UserName</td><td align='left'>"+username+"</td></tr>");
			outBuffer.append("<tr><td align='left'>Password</td><td align='left'>"+password+"</td></tr>");
			outBuffer.append("<tr><td align='left'>DBName</td><td align='left'>"+dbname+"</td></tr>");
			
		} catch (Exception e) {
			outBuffer.append("<tr><td align='left'>Error</td><td align='left'>"+e.getMessage()+"  "+e.getLocalizedMessage()+"</td></tr>");
		}
		outBuffer.append("</table>");
		
		response.getWriter().write(outBuffer.toString());
	}
	
	@RequestMapping(value="/test/sendFake.htm")
	public void testSend(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		String toEmails = request.getParameter("to");
//		String from = request.getParameter("from");
//		String subject = request.getParameter("subject");
//		String message = request.getParameter("message");
//		String fromName = request.getParameter("fromName");
		
		//boolean bool = mailer.sendFake(toEmails, from, fromName, subject, message);
		
		response.getWriter().write("Sevice is Down");
	}
}