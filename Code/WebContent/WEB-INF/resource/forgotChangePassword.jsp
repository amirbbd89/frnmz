<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<!DOCTYPE html>
<html>
	<head>
	<%
		String title = "Forgot Password";
		boolean isChange = false;
		if((request.getAttribute("mode") != null) && request.getAttribute("mode").equals("CHANGE_PASSWORD")){
			title = "Change Password";
			isChange = true;
		}
	%>
		
		<sms:Title pageTitle="<%=title%>"/>
		<script type="text/javascript">
		var msg = '<%=request.getAttribute("msg")%>';
		
		function setMessage(message, type){
			document.getElementById("msgDiv").innerHTML = message;
			document.getElementById("msgDiv").style.color = (type == 'Error') ? 'red' : 'green';
		}
		
		function validateEmail(){
			if(isBlank(document.getElementById("email").value)){
				setMessage('Please fill valid Email Id', 'Error');
				return false;
			}
			
			return true;
		}
		
		function validatePassword(){
			if(isBlank(document.getElementById("password").value)){
				setMessage('Please fill valid Current Password', 'Error');
				return false;
			}
			
			if(isBlank(document.getElementById("newpassword").value)){
				setMessage('Please fill valid New Password', 'Error');
				return false;
			}
			
			if(isBlank(document.getElementById("repeat").value)){
				setMessage('Please confirm New Password', 'Error');
				return false;
			}
			
			if(document.getElementById("newpassword").value != document.getElementById("repeat").value){
				document.getElementById("repeat").value = '';
				setMessage('New Password mismatch', 'Error');
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
		<sms:HeaderFooter isLoggedIn="<%=isChange%>"/>
		<div id="body">
			<div class="blog">
				<h4 id="msgDiv"></h4>
				<script type="text/javascript">
					if(msg != '' && msg != 'null'){
						setMessage(msg, '<%=request.getAttribute("msgType")%>');
					}
				</script>
				<div class="sidebar">
					<h3><%=title%></h3>
					<ul><li>
						<%if(isChange){%>
						<form action="<%=Settings.APP_CONTEXT%>/admin/onChangePassword.htm" method="post">
							<table style="width:100%">
								<tr align="center">
									<td align="right">Full Name</td>
									<td align="left"><b><%=session.getAttribute("fullName")%></b></td>
								</tr>
								<tr align="center">
									<td align="right">Email Id</td>
									<td align="left"><b><%=session.getAttribute("emailId")%></b></td>
								</tr>
								<tr align="center">
									<td align="right">Current Password</td>
									<td align="left"><input type="password" name="password" id="password"/></td>
								</tr>
								<tr align="center">
									<td align="right">New Password</td>
									<td align="left"><input type="password" name="newpassword" id="newpassword"/></td>
								</tr>
								<tr align="center">
									<td align="right">Confirm Password</td>
									<td align="left"><input type="password" name="repeat" id="repeat"/></td>
								</tr>
								<tr align="center">
									<td></td>
									<td align="left"><input type="submit" value="Change Password" onclick="return validatePassword()" style="width:144px"/></td>
								</tr>
							</table>
						</form>
						<%}else{%>
						<form action="<%=Settings.APP_CONTEXT%>/login/sendPassword.htm" method="post">
							<table style="width:100%">
								<tr align="center">
										<td align="right">Enter Your Email</td>
									<td align="left"><input type="email" name="email" id="email"/></td>
								</tr>
								<tr align="center">
									<td></td>
									<td align="left"><input type="submit" value="Send Password" onclick="return validateEmail()" style="width:144px"/></td>
								</tr>
							</table>
						</form>
						<%}%>
					</li></ul>
				</div>
				<br/><br/>
			</div>
		</div>
	</body>
</html>