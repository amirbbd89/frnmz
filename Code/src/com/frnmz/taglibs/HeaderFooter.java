package com.frnmz.taglibs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import com.frnmz.model.UserInfo;
import com.frnmz.utils.Settings;

public class HeaderFooter extends TagSupport {
	private static final long serialVersionUID = 7521214713555219036L;
	private boolean isLoggedIn;
	
	public HeaderFooter() {
		super();
	}
	
	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
    public int doStartTag() {
    	StringBuffer htmlTextBuffer = new StringBuffer("<div id=\"header\"><div class=\"section\">");
    	htmlTextBuffer.append("<div class=\"logo\"><a href=\""+Settings.APP_CONTEXT+"/\">"+Settings.APP_TITLE+"</a></div>");
		
    	htmlTextBuffer.append("<ul class=\"menu\">");
    	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/\">Home</a></li>&nbsp;");
    	
    	if(isLoggedIn){
    		UserInfo userInfo =  (UserInfo)((HttpServletRequest)pageContext.getRequest()).getSession(true).getAttribute("userInfo");

    		if(userInfo.getAdminAccess()){
    			htmlTextBuffer.append("<li><a href=\"#\">Members</a><ul class=\"sub-menu\">");
    			htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toCreateProfile.htm\">Add New</a></li>");
    			htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/onManageUsers.htm\">Manage Members</a></li></ul></li>&nbsp;");

    			htmlTextBuffer.append("<li><a href=\"#\">Account</a><ul class=\"sub-menu\">");
    			htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toCreditDebit.htm?action=CREDIT\">Add Contribution</a></li>");
    			htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toCreditDebit.htm?action=DEBIT\">Cab Payment</a></li></ul></li>&nbsp;");
    		} else {
    			htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/guest/toViewMembers.htm\">Members</a></li>&nbsp;");
    		}
    		htmlTextBuffer.append("<li><a href=\"#\">My Profile</a><ul class=\"sub-menu\">");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toEditProfile.htm\">Edit Profile</a></li>");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toChangePassword.htm\">Change Password</a></li>");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/j_spring_security_logout\">Logout</a></li></ul></li>&nbsp;");
    	} else {
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/guest/toViewFundDetails.htm\">Fund Details</a></li>&nbsp;");
        	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/guest/toViewMembers.htm\">Members</a></li>&nbsp;");
        	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/contactUs.jsp\">Contact Us</a></li>");
    	}
        
    	
    	htmlTextBuffer.append("</ul></div></div>");
    	htmlTextBuffer.append("<div id=\"footer\"><div style=\"float:left;\">&nbsp;&nbsp;&copy;&nbsp;Designed & Developed By Mohammad Aamir</div><div style=\"float:right;\"><a target=\"details\" href=\""+Settings.APP_FB_URL+"\" id=\"facebook\">#</a><a target=\"details\" href=\""+Settings.APP_TWITTER_URL+"\" id=\"twitter\">#</a><a href=\"mailto:amirbbd89@gmail.com\" id=\"googleplus\">#</a>&nbsp;&nbsp;&nbsp;</div></div>");
    	try {
    		pageContext.getOut().println(htmlTextBuffer.toString());
		} catch (IOException e) {e.printStackTrace();}
    	
    	return SKIP_BODY;
    }

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}