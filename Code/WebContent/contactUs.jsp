<%@page import="com.frnmz.utils.Settings"%>
<%@page import="org.springframework.security.authentication.UsernamePasswordAuthenticationToken"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<sms:Title pageTitle="Contact Us"/>
	</head>
	<body>
		<%
			boolean isLoggedIn = false;
			if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken){
				if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
					isLoggedIn = true;
				}
			}
		%>
		<sms:HeaderFooter isLoggedIn="<%=isLoggedIn%>"/>
		<div id="body">
			<div class="blog">
				<div class="sidebar">
					<h3>Contact Us</h3>
					<ul>
						<li>
						<table>
						<tr><th align="right">Name:</th><td>Mohammad Aamir</td></tr>
						<tr><th align="right"><br/>Mobile No:</th><td><br/>+91-7531 079 343</td></tr>
						<tr><th align="right"><br/>Email Id:</th><td><br/>amirbbd89@gmail.com</td></tr>
						</table>
						</li>
					</ul>
				</div>
				<br/><br/>
			</div>
		</div>
	</body>
</html>