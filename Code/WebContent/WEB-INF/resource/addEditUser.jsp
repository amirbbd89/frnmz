<%@page import="com.frnmz.model.UserInfo"%>
<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<!DOCTYPE html>
<html>
	<head>
		<%
		String title = "Add Member";
		String formAction = Settings.APP_CONTEXT + "/admin/onSaveProfile.htm";
		boolean isEdit = false;
		UserInfo userInfo = new UserInfo();
		if((request.getAttribute("mode") != null) && request.getAttribute("mode").equals("EDIT_PROFILE")){
			if((request.getAttribute("editMode") != null) && request.getAttribute("editMode").equals("MM")){
				title = "Edit Member";
				
			} else {
				title = "Edit Profile";
			}
			userInfo = (UserInfo) request.getAttribute("USER_INFO");
			isEdit = true;
		}
		%>
		<sms:Title pageTitle="<%=title%>"/>

		<script src="<%=Settings.APP_CONTEXT%>/js/jquery.min.js"></script>
		<script type="text/javascript">		
		var msg = '<%=request.getAttribute("msg")%>';
		
		function setMessage(message, type){
			document.getElementById("msgDiv").innerHTML = message;
			document.getElementById("msgDiv").style.color = (type == 'Error') ? 'red' : 'green';
		}
		
		function validateForm(){
			if(isBlank(document.getElementById("emailId").value)){
				setMessage('Please fill valid Email', 'Error');
				return false;
			}
			
			if(isBlank(document.getElementById("fullName").value)){
				setMessage('Please fill Full Name', 'Error');
				return false;
			}
			
			if(isBlank(document.getElementById("mobileNumber").value)){
				setMessage('Please fill Mobile Number', 'Error');
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
				<div class="sidebar">
					<h3><%=title%></h3>
					<ul><li>
						<form id="form1" action="<%=formAction%>" method="post">
							<input type="hidden" name="mode" value="<%=request.getAttribute("mode")%>"/>
							<input type="hidden" name="editMode" value="<%=request.getAttribute("editMode")%>"/>
							<table>
								<tr>
									<td align="right">Email Id:</td>
									<td><input type="text" name="emailId" id="emailId" <%if(isEdit){%> value="<%=userInfo.getEmailId()%>" readonly="readonly"<%}%>/></td>
								</tr>
								<tr>
									<td align="right">Full Name:</td>
									<td><input type="text" name="fullName" id="fullName" <%if(isEdit){%> value="<%=userInfo.getFullName()%>"<%}%>/></td>
								</tr>
								<tr>
									<td align="right">Mobile No:</td>
									<td><input type="text" name="mobileNumber" id="mobileNumber" <%if(isEdit){%> value="<%=userInfo.getMobileNumber()%>"<%}%>/></td>
								</tr>
								<tr>
									<td></td>
									<td><br/><input type="submit" value="Submit" onclick="return validateForm()" style="width:70px"/>&nbsp;&nbsp;<input type="reset" value="Reset" style="width:65px"/></td>
								</tr>
							</table>
						</form>
					</li></ul>
				</div>
				<br/><br/>
			</div>
		</div>
	</body>
</html>