<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Edit book in the inventory</title>
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
		<h1 align="center"><font style="text-decoration: underline;"> Manage Book</font></h1>
		<h4 align="center"><font color="red"><%out.println(request.getAttribute("flag")==null?"":request.getAttribute("flag")); %></font></h4>
		<form name="BookForm" id="BookForm" action="./bookServlet?pageFlag=manage" method="Post" >			
		<table class="centered" style="font-size:14px;border-spacing:11px" >
			<tr>
			    <td>ISBN<font></font></td>
			    <td><input type="number" name="isbn" id="isbn" maxlength="13"  pattern=".{13}" title="ISBN-13 digit no"/></td> 
			</tr>			
			<tr>
			    <td>Book Name<font></font></td>
			    <td><input type="text" name="bookname" id="bookname" /></td> 
			  </tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"  value="Submit" style="width:80px;height:20px;background-color:#E4881D;font-size:13px;color: white;padding:2px;" /></td>
			</tr>
		</table>
	</form>
	<%} %>
</body>
</html>