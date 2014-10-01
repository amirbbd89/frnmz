package com.frnmz.taglibs;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import com.frnmz.model.UserInfo;

public class MemberCombo extends TagSupport {
	private static final long serialVersionUID = -5960337342029588684L;
	private String name;
	private String id;
	private List<UserInfo> userList;

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}

	public MemberCombo() {
		super();
	}

	public int doStartTag() {
		StringBuffer htmlTextBuffer = new StringBuffer("<select name=\""+name+"\" id=\""+id+"\">");
		
		htmlTextBuffer.append("<option value=\"\" selected=\"selected\">Select</option>");
		
		for(UserInfo userInfo : userList){
			htmlTextBuffer.append("<option value=\""+userInfo.getFullName()+"|"+userInfo.getEmailId()+"\">"+userInfo.getFullName()+"</option>");
		}
		
		htmlTextBuffer.append("<option value=\"Last Admin|\">From Last Admin</option></select>");

		try {
			pageContext.getOut().println(htmlTextBuffer.toString());
		} catch (IOException e) {e.printStackTrace();}

		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}	
}