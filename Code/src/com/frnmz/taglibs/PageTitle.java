package com.frnmz.taglibs;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;

import com.frnmz.utils.Settings;

public class PageTitle extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String pageTitle;
	public PageTitle() {
		super();
		setPageTitle("");
	}
	
    public int doStartTag() {
    	StringBuffer htmlTextBuffer = new StringBuffer("<title>");

    	if(!"".equals(pageTitle)){
    		htmlTextBuffer.append(pageTitle+":");
    	}

    	htmlTextBuffer.append(Settings.APP_TITLE+"</title>");
    	htmlTextBuffer.append("<link rel=\"shortcut icon\" type=\"image/ico\" href=\""+Settings.APP_CONTEXT+Settings.APP_FAVICON_URL+"\">");
    	htmlTextBuffer.append("<link rel=\"icon\" type=\"image/ico\" href=\""+Settings.APP_CONTEXT+Settings.APP_FAVICON_URL+"\">");
    	htmlTextBuffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+Settings.APP_CONTEXT+"/css/style.css\">");

    	try {
    		pageContext.getOut().println(htmlTextBuffer.toString());
    	} catch (IOException e) {e.printStackTrace();}

    	return SKIP_BODY;
    }

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
}