<%@page import="javax.script.ScriptException"%>
<%@page import="sun.font.Script"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>		
		<title>671 Books- Customer Orders</title>
		<style type="text/css">
			#books_search h2 {
			background-color: #7EB610;
			border-bottom: #A2B565 1px solid;
			color: white;
		}	
		</style>
		<link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
<body>
	<div id="books_search" style="height:40px; "> 
		<h2 style="height:22px;padding-top:4px;font-family : tahoma, sans-serif;background-image:none; " align="center"><span style="text-decoration:none;" >Your Orders</span></h2></div>
		<div class="maincol_box" style="height: 405px">
			<div id="bestsellers" style="height: 405px;background-image: none">
				<div class="content" style="width: 360px; height: 405px; border: 5px;overflow-y: auto;" id="mainDiv">
					<form action="customerServlet?pageFlag=add" method="Post" 	name="customerForm" id="customerForm">
					<div align="center"><font style="color: red; font-family: tahoma, sans-serif; font-size:14px; font-weight: bold;text-align: center;" ><c:out value="${flag}" /></font></div>
					<table style="width:345px; font-size: 12px; margin: 0 auto; vertical-align: top; border-collapse: separate; border-spacing: 1px;border:thin;padding:2px;">
						<c:forEach var="aList" items="${shipArray}">
							<tr>
								<td width="60px"><font color="red" style="font-weight: bold;text-decoration: underline;">Order No:</font></td>
								<td><font color="#7EB610" style="font-weight: bold;"><c:out value="${aList[0]}" /></font></td>
								</tr>
								<tr>
									<td width="50px"><font color="333333" style="font-weight: bold;" >Title:</font> </td>
									<td colspan=5 width=80px><c:out value="${aList[1]}" /></td>
								</tr>
								<tr>
									<td width="140px" colspan="2"><font color="333333" style="font-weight: bold;">Order Date:</font>&nbsp;<c:out value="${aList[4]}" /></td>
									<td width="100px" colspan="3"><font color="333333" style="font-weight: bold;">&nbsp;&nbsp;Delivery Date:</font>&nbsp;<c:out value="${aList[5]}" /></td>
								</tr>
								<tr>
									<td><font color="333333" style="font-weight: bold;"> Price:&nbsp;</font>$<c:out value="${aList[2]}" /></td>
									<td width="90px"><font color="333333" style="font-weight: bold;">&nbsp;Quantity:</font> &nbsp;<c:out value="${aList[3]}" /></td>
									<td colspan=4>&nbsp;&nbsp;<font color="333333" style="font-weight: bold;">Status:</font> <c:out value="${aList[7]}" /></td>
								</tr>								
							<tr><td colspan=6>&nbsp;</td></tr>
						</c:forEach>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>