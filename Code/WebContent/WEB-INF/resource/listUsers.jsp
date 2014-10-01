<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<sms:Title pageTitle="Members"/>
	</head>
	<body>
		<sms:HeaderFooter/>
		<div id="body">
			<div class="blog">
				<table class="table table-bordered table-striped">
					<tr><th colspan="4">Members</th></tr>
					<tr><th></th><th>Name</th><th>Email Id</th><th>Mobile Number</th></tr>
					<c:forEach var="user" items="${userList}">
						<tr><td align="right">
							<c:choose>
								<c:when test="${user.enabled eq true}">
									<span class="badge badge-green">&nbsp;Active&nbsp;&nbsp;</span>
									<c:if test="${user.adminAccess eq true}">
										<img src="<%=Settings.APP_CONTEXT%>/images/admin.png" title="Admin" alt="Admin">
									</c:if>
								</c:when>
								<c:otherwise>
									<span class="badge badge-red">Opt-Out</span>
								</c:otherwise>
							</c:choose>
							&nbsp;&nbsp;
						</td><td>${user.fullName}</td><td>${user.emailId}</td><td>${user.mobileNumber}</td></tr>
					</c:forEach>
				</table>
				<br/><br/>
			</div>
		</div>
	</body>
</html>