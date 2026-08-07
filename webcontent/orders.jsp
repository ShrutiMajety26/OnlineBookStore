<script>
	function showOrderInfo(orderId) 
	{
		document.getElementById("orderId").value = orderId;
		document.getElementById("orderform").submit();
	}
</script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form name="orderform" id="orderform" action="./orderServlet" method="post">
	<input type="hidden" name="orderId" id="orderId"> <input type="hidden" name="actionType" id="actionType" value="getInfo">
	<h4 align="center">
		<font color="red">
			<% 	out.println(request.getAttribute("flag") == null ? "" : request.getAttribute("flag"));%>
		</font>
	</h4>
	
	<table style="width: 100%; border-width: 1px; border-color: #CDDAEC; border-style: solid;">
		<tr>
			<th  style="background-color:7EB610; "><b>Order Id</b></th>
			<th  style="background-color:7EB610;"><b>Customer Id</b>
			</th>
			<th  style="background-color:7EB610;"><b>Customer Name</b>
			</th>
			<th  style="background-color:7EB610;"><b>Date of Invoice</b>
			</th>
			<th  style="background-color:7EB610;"><b>Status</b>
			</th>
		</tr>
		<c:forEach items="${ordersList}" var="entry">
			<tr>
				<td align="center">
				<a href="#" onclick="showOrderInfo('${(entry.value)[0]}');">${(entry.value)[0]}</a></td>
				<td align="center">${(entry.value)[1]}</td>
				<td align="center">${(entry.value)[2]}</td>
				<td align="center">${(entry.value)[3]}</td>
				<td align="center">${(entry.value)[4]}</td>
			</tr>
		</c:forEach>
	</table>
</form>