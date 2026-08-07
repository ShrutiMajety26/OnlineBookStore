<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Payment Details</title>
</head>
<body>
	<div align="center"><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
	<div id="page_title"><h1><img src="images/bookLogo2.png" height="58px" width=200px /></h1></div>
	<p>
		<c:out value="${flag} "></c:out>
		<form method="get" action="customerAccount.jsp" >
		<input type="submit" value="Home" />
		</form>
	</p>
	</div>
</body>
</html>