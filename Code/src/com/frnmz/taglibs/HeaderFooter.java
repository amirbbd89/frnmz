package com.frnmz.taglibs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.frnmz.model.UserInfo;
import com.frnmz.utils.Settings;

public class HeaderFooter extends TagSupport {
	private static final long serialVersionUID = 7521214713555219036L;
	private UserInfo userInfo;
	
	public HeaderFooter() {
		super();
	}
	
	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
    public int doStartTag() {
    	if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken){
			if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
				userInfo =  (UserInfo)((HttpServletRequest)pageContext.getRequest()).getSession(true).getAttribute("userInfo");
			}
		}
    	
    	StringBuffer htmlTextBuffer = new StringBuffer("<div id=\"header\"><div class=\"section\">");
    	htmlTextBuffer.append("<div class=\"logo\"><a href=\""+Settings.APP_CONTEXT+"/\">"+Settings.APP_TITLE+"</a></div>");
		
    	htmlTextBuffer.append("<ul class=\"menu\">");
    	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/\">Home</a></li>&nbsp;");
    	
    	if(null != userInfo){
    		htmlTextBuffer.append("<li><a href=\"#\">Members</a><ul class=\"sub-menu\">");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toCreateProfile.htm\">Add New</a></li>");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/onManageUsers.htm\">Manage Members</a></li></ul></li>&nbsp;");
    		
    		htmlTextBuffer.append("<li><a href=\"#\">Account</a><ul class=\"sub-menu\">");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toCreditDebit.htm?action=CREDIT\">Add Contribution</a></li>");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toCreditDebit.htm?action=DEBIT\">Cab Payment</a></li></ul></li>&nbsp;");
    		
    		htmlTextBuffer.append("<li><a href=\"#\">My Profile</a><ul class=\"sub-menu\">");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toEditProfile.htm\">Edit Profile</a></li>");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/admin/toChangePassword.htm\">Change Password</a></li>");
    		htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/j_spring_security_logout\">Logout</a></li></ul></li>");
        } else {
        	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/guest/toViewFundDetails.htm\">Fund Details</a></li>&nbsp;");
        	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/guest/toViewMembers.htm\">Members</a></li>&nbsp;");
        	htmlTextBuffer.append("<li><a href=\""+Settings.APP_CONTEXT+"/contactUs.jsp\">Contact Us</a></li>");
        }
    	
    	htmlTextBuffer.append("</ul></div></div>");
    	htmlTextBuffer.append("<div id=\"footer\"><div style=\"float:left;\">&nbsp;&nbsp;&copy;&nbsp;Designed & Developed By Mohammad Aamir</div><div style=\"float:right;\"><a  href=\"mailto:amirbbd89@gmail.com\" id=\"facebook\">#</a><a  href=\"mailto:amirbbd89@gmail.com\" id=\"twitter\">#</a><a  href=\"mailto:amirbbd89@gmail.com\" id=\"googleplus\">#</a>&nbsp;&nbsp;&nbsp;</div></div>");
    	try {
    		pageContext.getOut().println(htmlTextBuffer.toString());
		} catch (IOException e) {e.printStackTrace();}
    	
    	return SKIP_BODY;
    }

	public int doEndTag() {
		return EVAL_PAGE;
	}
}