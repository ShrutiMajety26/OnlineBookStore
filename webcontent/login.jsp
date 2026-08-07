<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>671 Books- Login</title>
</head>
<body>
		<%  String loginFlag=request.getParameter("loginFlag");
			if (loginFlag!=null && loginFlag.equalsIgnoreCase("cust"))
				if (session.getAttribute("userName")!=null)
					response.sendRedirect("customerAccount.jsp");
		%>		
		<h1 align="center">Welcome To 671 Books!</h1>
		<h2 align="center"><img src="images/bookLogo2.png" height="80px" width=250px /></h2>
		<h2 align="center">Login into your account-<%=(loginFlag!=null &&(loginFlag.equalsIgnoreCase("cust"))?"Customer":(loginFlag.equalsIgnoreCase("admin"))?"Administrator":"")%> </h2><br/>		
		<form action="loginServlet?loginFlag=<%=loginFlag%>" method="Post">
			<table style="padding:7px;margin-left:auto; margin-right:auto;border:1px;border-collapse: separate; border-spacing:10px; " >
				<tr >
					<th align="center" colspan="2">
						<font color="red" ><c:out value="${empty afterLoginFlag ? '' : afterLoginFlag}" /></font> 						
					</th>
				</tr>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="userName" id="userName"  required="required" placeholder="Enter Username" title="Enter Username"  autofocus="autofocus" autocomplete="off"/>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="passw" id="passw" required="required" autocomplete="off" placeholder="Enter Password"  title="Enter Password"/>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Login" style="color: white; background-color: #E4881D; width: 80px"/></td>
				</tr>
			</table>
		</form>
</body>
</html>