<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<sms:Title pageTitle="Fund Details"/>
	</head>
	<body>
		<sms:HeaderFooter/>
		<div id="body">
			<div class="blog">
				<table class="table table-bordered table-striped">
				<tr><th colspan="5">Fund Details</th></tr>
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