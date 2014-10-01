package com.frnmz.services;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.frnmz.model.UserInfo;
import com.frnmz.utils.Settings;

@Component
public class Mailer {
	private static final String SMTP_HOST_NAME = System.getenv("SENDGRID_SMTP_HOST");
    private static final String SMTP_AUTH_USER = System.getenv("SENDGRID_USERNAME");
    private static final String SMTP_AUTH_PWD  = System.getenv("SENDGRID_PASSWORD");
    
    public boolean sendEmail(String toEmails, String ccEmails, String bccEmails, String replyToEmails, String subject, String message, List<String> files, String filesLocation){
    	return sendEmail(getInternetAddressList(toEmails), getInternetAddressList(ccEmails), getInternetAddressList(bccEmails), getInternetAddressList(replyToEmails), subject, message, files, filesLocation);
    }
    
    public boolean sendEmail(List<UserInfo> userList, String ccEmails, String bccEmails, String replyToEmails, String subject, String message, List<String> files, String filesLocation) {
    	return sendEmail(getInternetAddressList(userList), getInternetAddressList(ccEmails), getInternetAddressList(bccEmails), getInternetAddressList(replyToEmails), subject, message, files, filesLocation);
	}
    
    public boolean sendEmail(InternetAddress[] toEmails, InternetAddress[] ccEmails, InternetAddress[] bccEmails, InternetAddress[] replyToEmails, String subject, String message, List<String> files, String filesLocation){
    	try{
    		Properties properties = new Properties();	        

    		properties.put("mail.smtp.port", 587);
    		properties.put("mail.smtp.host", SMTP_HOST_NAME);
    		properties.put("mail.smtp.starttls.enable", "true");
    		properties.put("mail.smtp.startssl.enable","true");
    		properties.put("mail.smtp.auth", "true");

    		Authenticator auth = new MyAuthenticator(SMTP_AUTH_USER, SMTP_AUTH_PWD);
    		Session session = Session.getDefaultInstance(properties,auth);
    		MimeMessage mimeMessage = new MimeMessage(session);
    		Multipart body = new MimeMultipart();
    		MimeBodyPart messagePart = new MimeBodyPart();

    		if(files != null){
    			for(int f=0; f < files.size(); f++) {
    				MimeBodyPart part = new MimeBodyPart();

    				FileDataSource ds = new FileDataSource(filesLocation + File.separator + files.get(f)) {
    					public String getContentType() {
    						return "application/octet-stream";
    					}
    				};

    				part.setDataHandler(new DataHandler(ds));
    				part.setFileName(files.get(f));
    				body.addBodyPart(part);
    			}
    		}

    		messagePart.setContent(MimeUtility.encodeText(message),"text/html");

    		body.addBodyPart(messagePart);

    		mimeMessage.setFrom(new InternetAddress(Settings.APP_ADMIN_MAILID, Settings.APP_ADMIN_MAILBOX_TITLE));
    		mimeMessage.addRecipients(Message.RecipientType.TO, toEmails);
    		mimeMessage.addRecipients(Message.RecipientType.CC, ccEmails);
    		mimeMessage.addRecipients(Message.RecipientType.BCC, bccEmails);
    		mimeMessage.setReplyTo(replyToEmails);
    		mimeMessage.setSubject(subject);
    		mimeMessage.setText(message);
    		mimeMessage.setSentDate(new Date());
    		mimeMessage.setContent(body);
    		mimeMessage.saveChanges();

    		Transport.send(mimeMessage);
    		System.out.println("Message SEND ==> SUCCESS");
    		return true;
    	}catch (Exception e) {
    		System.out.println("Message NOT SEND ==>  ERROR");
    		e.printStackTrace();
    		return false;
    	}
    }
    

    
    public boolean sendFake(String toEmails, String from, String fromName, String subject, String message){
    	try{
    		Properties properties = new Properties();	        

    		properties.put("mail.smtp.port", 587);
    		properties.put("mail.smtp.host", SMTP_HOST_NAME);
    		properties.put("mail.smtp.starttls.enable", "true");
    		properties.put("mail.smtp.startssl.enable","true");
    		properties.put("mail.smtp.auth", "true");

    		Authenticator auth = new MyAuthenticator(SMTP_AUTH_USER, SMTP_AUTH_PWD);
    		Session session = Session.getDefaultInstance(properties,auth);
    		MimeMessage mimeMessage = new MimeMessage(session);
    		Multipart body = new MimeMultipart();
    		MimeBodyPart messagePart = new MimeBodyPart();


    		messagePart.setContent(MimeUtility.encodeText(message),"text/html");

    		body.addBodyPart(messagePart);

    		mimeMessage.setFrom(new InternetAddress(from, fromName));
    		mimeMessage.addRecipients(Message.RecipientType.TO, getInternetAddressList(toEmails));
    		mimeMessage.setReplyTo(getInternetAddressList(from));
    		mimeMessage.setSubject(subject);
    		mimeMessage.setText(message);
    		mimeMessage.setSentDate(new Date());
    		mimeMessage.setContent(body);
    		mimeMessage.saveChanges();

    		Transport.send(mimeMessage);
    		System.out.println("Message SEND ==> SUCCESS");
    		return true;
    	}catch (Exception e) {
    		System.out.println("Message NOT SEND ==>  ERROR"+e.getMessage());
    		return false;
    	}
    }

	public InternetAddress[] getInternetAddressList(String addresses){
		InternetAddress[] addressList;

		if(isNotBlank(addresses)){
			StringTokenizer addressTokenizer = new StringTokenizer(addresses,"\\|");
			addressList = new InternetAddress[addressTokenizer.countTokens()];
			int index = 0;
			while (addressTokenizer.hasMoreTokens()) {
				try {
					addressList[index++] = new InternetAddress(addressTokenizer.nextToken());
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
		} else {
			 addressList = new InternetAddress[0];
		}

		return addressList;
	}
	
	public InternetAddress[] getInternetAddressList(List<UserInfo> userList){
		InternetAddress[] addressList;

		if(CollectionUtils.isNotEmpty(userList)){
			addressList = new InternetAddress[userList.size()];
			int index = 0;
			for (UserInfo userInfo : userList) {
				try {
					addressList[index++] = new InternetAddress(userInfo.getEmailId());
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
		} else {
			 addressList = new InternetAddress[0];
		}

		return addressList;
	}

	public boolean isNotBlank(String address){
		if((address == null) || address.trim().equals("") || address.trim().equalsIgnoreCase("null")){
			return false;
		}
		return true;
	}
}

class MyAuthenticator extends Authenticator {
	String email;    
	String password;

	MyAuthenticator(String email, String password){
		this.email = email;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {  
		return new PasswordAuthentication(email, password);  
	}  
}