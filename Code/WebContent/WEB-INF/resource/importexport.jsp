<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<sms:Title pageTitle="Import/Export"/>
	    <script type="text/javascript">
			var msg = '<%=request.getAttribute("msg")%>';
			function setMessage(message, type){
				document.getElementById("msgDiv").innerHTML = message;
				document.getElementById("msgDiv").style.color = (type == 'Error') ? 'red' : 'green';
			}
			
			function validateForm(){
				var fileName = document.getElementById("excelFile").value;
				if("" == fileName){
					setMessage("Please select a file.", "Error");
					return false;
				} else {
					var lastIndex = fileName.lastIndexOf(".xls");
					if(fileName.length-4 != lastIndex){
						setMessage("Please select a Excel file.", "Error");
						return false;
					}
				}
				return true;
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
					<h3>Export Data</h3>
					<ul><li><form method="post" action="<%=Settings.APP_CONTEXT%>/admin/onExcelExport.htm">
						<input type="submit" value="Export Data To Excel">
					</form></li></ul>
				</div>
					
				<div class="sidebar">
					<h3>Import Data</h3>
					<ul><li><form method="post" action="<%=Settings.APP_CONTEXT%>/admin/onExcelImport.htm" enctype="multipart/form-data">
						<table>
							<tr><th align="right">Select Excel File</th><th><input type="file" id="excelFile" name="excelFile"></th></tr>
							<tr><th></th><th><input type="submit" value="Import Data" onclick="return validateForm()"></th></tr>
						</table>
					</form></li></ul>
				</div>
				<br/><br/>
			</div>
		</div>
	</body>
</html>