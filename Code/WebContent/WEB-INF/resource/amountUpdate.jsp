<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<%
		String action = request.getParameter("action");
		String formAction = Settings.APP_CONTEXT;
		String title;
		if("DEBIT".equals(action)){
			title = "Cab Payment";
			formAction = formAction.concat("/admin/onUpdateCabPayment.htm");
		} else {
			title = "Add Contribution";
			formAction = formAction.concat("/admin/onAddMemberContribution.htm");
		}
		%>
		<sms:Title pageTitle="<%=title%>"/>
		<script type="text/javascript">		
		var action = '<%=action%>';
		
		function setMessage(message, type){
			document.getElementById("msgDiv").innerHTML = message;
			document.getElementById("msgDiv").style.color = (type == 'Error') ? 'red' : 'green';
		}
		
		function validateForm(){
			if(isBlank(document.getElementById("amount").value)){
				setMessage('Please fill Amount', 'Error');
				return false;
			}
			
			if(action == "CREDIT" && isBlank(document.getElementById("member").value)){
				setMessage('Please select member', 'Error');
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
				<div class="sidebar">
					<h3><%=title%></h3>
					<ul><li>
					<form action="<%=formAction%>" method="post">
					<input type="hidden" name="balance" id="balance" value="${balance}">
					<table>
					<tr><td>Dated</td><td><sms:DateCombo name="date" id="date"></sms:DateCombo></td></tr>
					
					<%if("CREDIT".equals(action)){%>
						<tr><td>Contributed By</td><td><sms:MemberCombo id="member" name="member" userList="${userList}"/></td></tr>
					<%}%>
					<tr><td>Amount</td><td><input type="text" id="amount" name="amount" value=""></td></tr>
					<tr><td></td><td><input type="submit" value="Submit" onclick="return validateForm()"></td></tr>
					</table>
					</form>
					</li></ul>
				</div>
				<br/><br/>
			</div>
		</div>
	</body>
</html>