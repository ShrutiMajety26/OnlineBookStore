<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Update a book from inventory</title>
		<style type="text/css">
			table.centered { 
				width:400px; 
				top:80px; 
				left:55%; 
				margin-left:220px;				
			}
		</style>
	</head>
	<body>
		<% if (request.getParameter("flag")!=null  && request.getParameter("flag").equalsIgnoreCase("empty"))
			{%>
				<br/><h2 align="center">Welcome Admin!!</h2>
			<%}
		else
		{%>
		<h1 align="center"><font style="text-decoration: underline;">Customer Details</font></h1>				
				<form action="customerServlet?pageFlag=delete" method="Post" name="customerForm" id="customerForm">
					<table class="centered" style="font-size:14px;border-spacing:11px" >				
						
						<c:forEach var="customer" items="customerDetails" >
						<tr >
							<td align="left">UserName<font></font></td>
							<td align="left"><input type="text" name="uname" id="uname" value="${customerDetails[1]}" readonly="readonly"/></td>
						</tr>
						<tr>
							<td align="left">Phone Number<font></font></td>
							<td align="left"><input type="tel" name="PNo" id="PNo" value="${customerDetails[2]}" readonly="readonly"/></td>
						</tr>
						<tr>
							<td align="left">Gender<font></font></td>
							<td align="left"><input type="text" name="gender" id="gender" value="${customerDetails[3]}" required="required" /></td>
						</tr>
						<tr>
							<td align="left">Email<font></font></td>
							<td align="left"><input type="email" name="mailid" id="mailid" value="${customerDetails[4]}" readonly="readonly"/></td>
						</tr>
						<tr>
							<td align="left">Address<font></font></td>
							<td align="left"><textarea name="addr" id="addr" rows=4  readonly="readonly" maxlength=50 cols=22>${customerDetails[5]}</textarea></td>
						</tr>
						
           					 </c:forEach>
          				  
						<tr>
							<td colspan="2" align="center" ><input type="submit" value="Delete Customer" style="width:80px;height:22px;background-color:#E4881D;font-size:13px;color: white;padding:2px" /></td>
						</tr>			
					</table>
				</form>
				<%} %>
	</body>
</html>