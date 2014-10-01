package com.frnmz.utils;

import java.security.MessageDigest;
import java.util.List;

import com.frnmz.model.Record;

public class Utils {
	public static String getMessage(String statusMsg, String email, String password){
		StringBuffer message = new StringBuffer("Assalam Wa Alaikum Wa Rahmatullahu Wa Barqatahu,<br/><br/>");
		
		message.append("<h3>"+statusMsg+"</h3>");
		if(null != password){
			message.append("<br/>Email Id/Username: <b>" + email + "</b><br/>Password: <b>" + password + "</b>");
		} else {
			message.append("<br/>Admin Name: <b>"+email+"</b>");
		}
		
		message.append(Settings.APP_HOST_URL+"<br/><br/>This is system generated email.. Please don't reply.");
		return message.toString();
	}

	public static String getAccountUpdateMessage(String status, int amount, int balance, String name, String toBy, List<Record> recordList){
		StringBuffer message = new StringBuffer("Assalam Wa Alaikum Wa Rahmatullahu Wa Barqatahu,<br/><br/>");
		
		message.append("<h2>"+status+"</h2><b>");
		message.append(amount+" Paid "+toBy + name+"</b>");
		message.append(getRecordTable(balance, recordList));
		
		message.append(Settings.APP_HOST_URL+"<br/><br/>This is system generated email.. Please don't reply.");
		return message.toString();
	}

	public static String getFridayNotificationMessage(String tol, String ttg){
		StringBuffer message = new StringBuffer("Assalam Wa Alaikum Wa Rahmatullahu Wa Barqatahu,<br/><br/>");
		
		message.append("Please confirm your availability for today's <b>Friday Namaz</b> before <b>12:15 PM</b> noon.<br/>");
		message.append("Kindly be present on ground floor in front of the main gate by <b>"+ttg+"</b>. We will move at <b>"+tol+"</b> In-Shaa-ALLAH<br/>");
		message.append("Please inform someone if your are coming late.<br/>");
		
		return message.toString();
	}
	
	public static StringBuffer getFormBodyString(String id, String nameEmail, String hashCode){
		StringBuffer message = new StringBuffer("<br/>Click <a href=\"");

		message.append(Settings.APP_HOST_URL+"/guest/confirmAvailablity.htm?NID="+id+"&FULL_NAME_AND_EMAIL_ID="+nameEmail+"&HID="+hashCode);
		message.append("\">Here</a> to confirm your availability.<br/><br/>");

		return message;
	}

	public static String getContributionRequestMessage(int balance, List<Record> recordList, String name, String mobileNumber, String accNo, String city){
		StringBuffer message = new StringBuffer("Assalam Wa Alaikum Wa Rahmatullahu Wa Barqatahu,<br/><br/>");
		
		message.append("Please contribute to <b>Friday Namaz Fund</b> as we have only <b>"+balance+"</b> left our fund.<br/><br/>");
		message.append("<table border='1'><tr><th colspan='2'>CITIBank Account Details</th></tr><tr><td align='right'>Account Number</td><td>"+accNo+"</td></tr><tr><td align='right'>Bank City</td><td>"+city+"</td></tr></table>");
		message.append(getRecordTable(balance, recordList));
		message.append(getSignature(name, mobileNumber));
		
		return message.toString();
	}
	
	public static StringBuffer getRecordTable(int balance, List<Record> recordList){
		StringBuffer message = new StringBuffer();

		message.append("<br/><br/><table border='1'>");
		message.append("<tr><th colspan='5'>Account Statement</th></tr>");
		message.append("<tr><th>Date</th><th>From / To</th><th>Credit / Debit</th><th>Amount</th><th>Balance</th></tr>");

		for(Record record: recordList){
			message.append("<tr><td>"+record.getDateString()+"</td><td>"+record.getFromTo()+"</td><td>"+record.getCreditDebit()+"</td><td align='right'>"+record.getTransactionAmount()+"</td><td align='right'>"+record.getBalance()+"</td></tr>");
		}

		message.append("<tr><th colspan='4'>Current Balance</th><th align='right'>"+balance+"</th></tr>");
		message.append("</table><br/><br/>");
		
		return message;
	}

	public static StringBuffer getSignature(String fullName, String mobileNumber){
		StringBuffer message = new StringBuffer();

		message.append("--<br/>"+fullName+"<br/>"+mobileNumber+"<br/>");
		message.append(Settings.APP_HOST_URL+"<br/><br/>This is system generated email. Please don't reply.");
		
		return message;
	}
	
	public static String getHash(String inputString, String algorithm) {
		StringBuilder sb = new StringBuilder();
	    try {
			byte[] hash = MessageDigest.getInstance(algorithm).digest(inputString.getBytes());
			
			for (int i = 0; i < hash.length; ++i) {
		        String hex = Integer.toHexString(hash[i]);
		        if (hex.length() == 1) {
		            sb.append(0);
		            sb.append(hex.charAt(hex.length() - 1));
		        } else {
		            sb.append(hex.substring(hex.length() - 2));
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return sb.toString();
	}
}