<!DOCTYPE html>
<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<html>
<head><sms:Title pageTitle="Login Page"/></head>
<body onload="document.getElementById('frm').submit();">
	<form id="frm" action="<%=Settings.APP_CONTEXT%>/login.jsp" method="post">
		<input type="hidden" name="msgType" id="msgType" value="<%=request.getAttribute("msgType")%>"/>
		<input type="hidden" name="msg" id="msg" value="<%=request.getAttribute("msg")%>"/>
		<input type="submit" value="Continue to Login Page.......">
	</form>
</body>
</html>