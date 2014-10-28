<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<sms:Title pageTitle="Manage Members"/>

		<script type="text/javascript">		
		var msg = '<%=request.getAttribute("msg")%>';
		
		function setMessage(message, type){
			document.getElementById("msgDiv").innerHTML = message;
			document.getElementById("msgDiv").style.color = (type == 'Error') ? 'red' : 'green';
		}
		</script>
	</head>
	<body>
		<sms:HeaderFooter isLoggedIn="true"/>
		<div id="body">
			<div class="blog">
			<h4 id="msgDiv"></h4>
			<script type="text/javascript">
				if(msg != '' && msg != 'null'){
					setMessage(msg, '<%=request.getAttribute("msgType")%>');
				}
			</script>
				<table class="table table-bordered table-striped">
					<tr><th colspan="4">Manage Members</th></tr>
					<tr><th>Action</th><th>Name</th><th>Email Id</th><th>Mobile Number</th></tr>
					<c:forEach var="user" items="${userList}">
					<tr><td align="left">
					<c:if test="${user.adminAccess eq false}">
						<a href="<%=Settings.APP_CONTEXT%>/admin/editAccount.htm?emailId=${user.emailId}"><img src="<%=Settings.APP_CONTEXT%>/images/edit.png" title="Edit Member" alt="Edit Member"></a>&nbsp;&nbsp;
						<a href="<%=Settings.APP_CONTEXT%>/admin/deleteAccount.htm?emailId=${user.emailId}"><img src="<%=Settings.APP_CONTEXT%>/images/delete.png" title="Delete Member" alt="Delete Member"></a>&nbsp;&nbsp;
						<c:choose>
							<c:when test="${user.enabled eq true}">
								<a href="<%=Settings.APP_CONTEXT%>/admin/enableAccount.htm?emailId=${user.emailId}"><span class="badge badge-green" title="Deactivate Member">&nbsp;Active&nbsp;&nbsp;</span></a>
							</c:when>
							<c:otherwise>
								<a href="<%=Settings.APP_CONTEXT%>/admin/enableAccount.htm?emailId=${user.emailId}"><span class="badge badge-red" title="Activate Member">Opt-Out</span></a>
							</c:otherwise>
						</c:choose>
						<c:if test="${user.enabled eq true}">
							&nbsp;&nbsp;<a href="<%=Settings.APP_CONTEXT%>/admin/toTransferAuthority.htm?emailId=${user.emailId}&name=${user.fullName}"><span class="badge badge-yellow" >Transfer Authority</span></a>
							&nbsp;&nbsp;<a href="<%=Settings.APP_CONTEXT%>/admin/toResetPassword.htm?emailId=${user.emailId}"><span class="badge badge-yellow" >Reset Password</span></a>
						</c:if>
					</c:if>
					
					</td>
					<td>${user.fullName}</td><td>${user.emailId}</td><td>${user.mobileNumber}</td></tr>
					</c:forEach>
				</table>
				<br/><br/>
			</div>
		</div>
	</body>
</html>