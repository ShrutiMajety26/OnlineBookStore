<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>671 Books- Admin Site</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		#sides{
		margin:0;
		}
		.left{
		float:left;
		width:20%;
		overflow:hidden;
		margin-left:60px;
		border:2px solid;
		height:400px;
		margin-top:20px; 
		}
		#right{
		float:left;
		width:70%;
		overflow:hidden;
		border:none;
		height:420px; 
		margin-top:20px;
		} 
	</style>	
	<script>
		function showDiv(toggle,hide1,hide2,hide3)
		{			
			//window.location.href="adminAccount.jsp";
			document.getElementById(toggle).style.display = 'block';
			document.getElementById(hide1).style.display = 'none';
			document.getElementById(hide2).style.display = 'none';
			document.getElementById(hide3).style.display = 'none';
		}
	</script>
</head>
<body>
	<div id="page_header">
		<div id="page_title"><h1 ><img src="images/bookLogo2.png" height="58px" width=200px style="margin-left:-25px;" /></h1></div>		
		<div id="header_search" style="background-image: none">			
				<div><h3 align="right"><a href="home.jsp" style="font-size:14px;color:red;margin-bottom:20px">Logout</a></h3></div>			
		</div>
	</div>
	<div id="page_menu">
		<ul id="menu">
			<li><a href="adminAccount.jsp" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Home</a></li>
			<li><a href="#" onclick="showDiv('leftBook','leftCustomer','leftReports','leftOrders')" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Book</a></li>
			<li><a href="#" onclick="showDiv('leftCustomer','leftBook','leftReports','leftOrders')" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Customer</a></li>
			<li><a href="#" onclick="showDiv('leftOrders','leftCustomer','leftBook','leftReports')" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Orders</a></li>			 
			<li><a href="#" onclick="showDiv('leftReports','leftCustomer','leftBook','leftOrders')" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Reports</a></li>						
		</ul>
	</div>
	<div id="sides">
	<div id="leftBook" class="left">	
		<ul style="font-size:20px;height:24px;text-align: center;padding:10px ">
			<li style="padding:10px"><a href="viewBooks.jsp" target="contentFrame">View all Books</a></li>
			<li style="padding:10px"><a href="addBook.jsp" target="contentFrame">Add Book</a></li>
			<li style="padding:10px"><a href="manageBook.jsp" target="contentFrame">Manage Book</a></li>
			<!-- <li style="padding:10px"><a href="manageBook.jsp" target="contentFrame">Delete Book</a></li> -->
		</ul>
	</div>
	<div id="leftCustomer" class="left" style="display: none;">	
		<ul style="font-size:20px;height:24px;text-align: center;padding:10px ">
			<li style="padding:10px"><a href="viewCustomers.jsp" target="contentFrame">View Customers</a></li>
			<!-- <li style="padding:10px"><a href="#">Delete Customer</a></li> -->
			<li style="padding:10px"><a href="#"></a></li>
		</ul>
	</div>
	<div id="leftReports" class="left" style="display: none;">	
		<ul style="font-size:20px;height:24px;text-align: center;padding:10px ">
			<li style="padding:10px"><a href="report.jsp?flag=Sales" target="contentFrame">Sales Report</a></li>
			<li style="padding:10px"><a href="report.jsp?flag=Purchase" target="contentFrame">Purchases Report</a></li>						
		</ul>
	</div>
	<div id="leftOrders" class="left" style="display: none;">	
		<ul style="font-size:20px;height:24px;text-align: center;padding:10px ">
			<li style="padding:10px"><a href="orderServlet" target="contentFrame">Manage customer orders</a></li>
						
		</ul>
	</div>
	<div id="right" >
			<IFRAME name="contentFrame" id="contentFrame"  width=100% height=99% src="addBook.jsp?flag=empty"  style="border:thin;" ><p>Hello</p></IFRAME>
	</div>
	</div>
</body>
</html>