<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<sms:Title pageTitle="Welcome Page"/>
	    <script type="text/javascript">
			var msg = '<%=request.getAttribute("msg")%>';
			function setMessage(message, type){
				document.getElementById("msgDiv").innerHTML = message;
				document.getElementById("msgDiv").style.color = (type == 'Error') ? 'red' : 'green';
			}
			
			function validateForm(){
				if(isBlank(document.getElementById("ttg").value)){
					setMessage('Please fill Time to Gather', 'Error');
					return false;
				}
				
				if(isBlank(document.getElementById("tol").value)){
					setMessage('Please fill Time of Leaving', 'Error');
					return false;
				}
				
				return true;
			}
			
			function validateForm2(){
				if(isBlank(document.getElementById("accNo").value)){
					setMessage('Please fill CITIBank Account No', 'Error');
					return false;
				}
				
				if(isBlank(document.getElementById("city").value)){
					setMessage('Please fill CITIBank Account City', 'Error');
					return false;
				}
				
				return true;
			}
			
			function isBlank(val){
				if((val == null) || (val == '') || (val =='null')){
					return true;
				}
				return false;
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

				<c:if test="${userInfo.adminAccess}">
					<div class="sidebar">
						<h3>Namaz Notification</h3>
						<ul><li>
							<form method="post" action="<%=Settings.APP_CONTEXT%>/admin/onSendNotification.htm">
								<table>
									<tr><th align="right">Time to Gather</th><th><input type="text" id="ttg" name="ttg"></th></tr>
									<tr><th align="right">Time of Leaving</th><th><input type="text" id="tol" name="tol"></th></tr>
									<tr><th></th><th><input type="checkbox" checked="checked" name="toAll" id="toAll">All Member</th></tr>
									<tr><th></th><th><input type="submit" value="Send Namaz Notification" onclick="return validateForm()"></th></tr>
								</table>
							</form>
						</li></ul>
					</div>
					
					<div class="sidebar">
						<h3>Contribution Request</h3>
						<ul><li>
							<form method="post" action="<%=Settings.APP_CONTEXT%>/admin/onSendNotification.htm">
								<table>
									<tr><th align="right">A/C Number</th><th><input type="text" id="accNo" name="accNo"></th></tr>
									<tr><th align="right">City</th><th><input type="text" id="city" name="city"></th></tr>
									<tr><th></th><th><input type="checkbox" name="toOnlyDefaulter" id="toOnlyDefaulter">Only Defaulters</th></tr>
									<tr><th></th><th><input type="submit" value="Send Contribution Request" onclick="return validateForm2()"></th></tr>
								</table>
							</form>
						</li></ul>
					</div>
					
					<table class="table table-bordered table-striped">
						<tr><th colspan="3">Defaulter</th></tr>
						<tr><th>Name</th><th>EmailId</th><th>Mobile Number</th></tr>
						<c:forEach var="user" items="${defaulters}">
						<tr><td>${user.fullName}</td><td>${user.emailId}</td><td>${user.mobileNumber}</td></tr>
						</c:forEach>
					</table>
				</c:if>
				
				<table class="table table-bordered table-striped">
					<tr><th colspan="5">Account Details</th></tr>
					<tr><th>Date</th><th>From / To</th><th>Credit / Debit</th><th>Amount</th><th>Balance</th></tr>
					<c:forEach var="record" items="${recordList}">
					<tr><td>${record.dateString}</td><td>${record.fromTo}</td><td>${record.creditDebit}</td><td align="right">${record.transactionAmount}</td><td align="right">${record.balance}</td></tr>
					</c:forEach>
					<tr><th colspan="4">Current Balance</th><th align="right">${balance}</th></tr>
				</table>
				<br/><br/>
			</div>
		</div>
	</body>
</html>