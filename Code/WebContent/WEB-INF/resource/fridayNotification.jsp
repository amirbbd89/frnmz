<%@page import="com.frnmz.utils.Settings"%>
<%@ taglib uri="/WEB-INF/tags/frnmz.tld" prefix="sms"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${null ne notification}">
	<div class="namazNotification">
		<h3>${notification.subject}</h3>
		<h4>${notification.body}</h4>
	
		<c:if test="${not empty notification.likes}">
		<li>
			<h4>Confirmations</h4>
			<ol>
				<c:forEach var="member" items="${notification.likes}">
				<li><b>${member}</b></li>
				</c:forEach>
			</ol>
		</li>
		</c:if>		
	</div>
</c:if>