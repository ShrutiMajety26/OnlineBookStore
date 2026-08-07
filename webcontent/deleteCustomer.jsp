<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Delete customer</title>
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
		<h1 align="center"><font style="text-decoration: underline;"> Enter customer ID to delete account!!</font></h1>
		<h4 align="center"><font color="red"><%out.println(request.getAttribute("flag")==null?"":request.getAttribute("flag")); %></font></h4>
		<form name="BookForm" id="BookForm" action="./customerServlet?pageFlag=getcust" method="Post" >			
		<table class="centered" style="font-size:14px;border-spacing:11px" >
			<tr>
			    <td>Customer ID<font></font></td>
			    <td><input type="number" name="cid" id="cid" maxlength="4"  pattern=".{04}" title="Enter customer id"/></td> 
			</tr>
			<tr>			
				<td colspan="2" align="center"><input type="submit"  value="Submit" style="width:80px;height:20px;background-color:#E4881D;font-size:13px;color: white;padding:2px;" /></td>
			</tr>
		</table>
	</form>
	<%} %>
</body>
</html>