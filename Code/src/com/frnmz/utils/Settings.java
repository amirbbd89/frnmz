package com.frnmz.utils;

public class Settings {
	public static final String APP_CONTEXT;
	public static final String APP_HOST_URL;
	
	public static final String APP_FAVICON_URL = "/images/favicon.ico";
	public static final String APP_LOGO_URL = "/images/frnmzlogo.png";
	public static final String APP_TITLE = "FRIDAY NAMAZ";
	public static final String DEFAULT_PAGE_TITLE = "LOGIN PAGE";
	public static final String APP_ADMIN_MAILID = "mamir2@sapient.com";
	public static final String APP_ADMIN_MAILBOX_TITLE = "FRNMZ ADMIN";
	public static final String APP_FB_URL = "http://www.facebook.com/amirbbd89";
	public static final String APP_TWITTER_URL = "http://www.twitter.com/amirbbd89";
	
	static {
		if(null == System.getenv("VCAP_SERVICES")){
			APP_CONTEXT = "/frnmz";
			APP_HOST_URL = "http://localhost:8080/frnmz";
		} else {
			APP_CONTEXT = "";
			APP_HOST_URL = "http://frnmz.aws.af.cm";
		}
	}
}