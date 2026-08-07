<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html
	><head>
	</head>
<body>
<form name="orderform" id="orderform" action="orderServlet" method="post">
	<input type="hidden" name="orderId" id="orderId" value="${orderId}">
	<input type="hidden" name="actionType" id="actionType" value="cancel">	
	<table style="width:100%;border-color: #CDDAEC; border-style: solid;" >
		<tr>
			<th style="background-color: 7EB610;width:30px;"><b>Order Id</b>
			</th>
			<th style="background-color: 7EB610;width:30px;"><b>Customer Id</b>
			</th>
			<th style="background-color: 7EB610;width:70px;"><b>Customer Name</b>
			</th>
			<th style="background-color: 7EB610;width:90px; "><b>Date of Invoice</b>
			</th>
			<th style="background-color: 7EB610;width:90px;"><b>Date of Delivery</b>
			</th>
			<th style="background-color: 7EB610;width:100px;"><b>Book Name</b>
			</th>
			<th style="background-color: 7EB610;width:30px;"><b>Quantity</b>
			</th>
			<th style="background-color: 7EB610;width:50px;"><b>Status</b>
			</th>
		</tr>
		<c:forEach items="${orderInfoMap}" var="entry">
			<tr>
				<td>${(entry.value)[0]}</td>
				<td>${(entry.value)[1]}</td>
				<td>${(entry.value)[2]}</td>
				<td>${(entry.value)[3]}</td>
				<td>${(entry.value)[4]}</td>
				<td>${(entry.value)[7]}</td>
				<td>${(entry.value)[6]}</td>
				<td>${(entry.value)[5]}</td>
				<c:set var="cancelOrd" value="${(entry.value)[5]}" />
			</tr>
		</c:forEach>
		<tr><td>&nbsp;</td></tr>
		<tr>
	
			<td colspan="8" align="center"><c:if test="${cancelOrd eq 'Processing'}">
					<input type="submit" value="Cancel Order">
				</c:if>
			</td>
		</tr>
	</table>
</form>
</body>
</html>