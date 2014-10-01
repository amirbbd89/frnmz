package com.frnmz.taglibs;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.tagext.TagSupport;

public class DateCombo extends TagSupport {
	private static final long serialVersionUID = -5960337342029588684L;
	private static final DateFormat DF = new SimpleDateFormat("dd MMM yyyy");
	private String name;
	private String id;

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateCombo() {
		super();
	}

	@SuppressWarnings("deprecation")
	public int doStartTag() {
		StringBuffer htmlTextBuffer = new StringBuffer("<select name=\""+name+"\" id=\""+id+"\">");
		
		htmlTextBuffer.append("<option value=\"0\" selected=\"selected\">Today</option>");
		
		Date dt = new Date();
		for(int i = 1; i <= 7; i++){
			dt.setDate(dt.getDate() - 1);
			htmlTextBuffer.append("<option value=\""+i+"\">"+DF.format(dt)+"</option>");
		}

		try {
			pageContext.getOut().println(htmlTextBuffer.toString());
		} catch (IOException e) {e.printStackTrace();}

		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}	
}