<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>671 Books-Edit Customer</title>
		<style type="text/css">
			#books_search h2 {
				background-color : #7EB610;
				border-bottom : #A2B565 1px solid;
				color: white; 
			}
		</style>
			<link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body style="">				
		<div class="maincol_box" style="height:405px">
			<div id="bestsellers" style="height:405px" >
			<div class="content" align="center" style="width:360px;height:405px;border:5px;" id="mainDiv"><font style="color:red ;font-family:tahoma, sans-serif;font-size :11px;font-weight : bold; color:black;" ><c:out value="${flag}"  /></font>				
				<form action="customerServlet?pageFlag=editSave" method="Post" name="customerForm" id="customerForm">
					<table style="height:384px;width:320px ;margin:0 auto;border-collapse: separate; border-spacing:5px;font-size:11px;color:#5C5E5F;font-weight: bolder;" >					
						<tr >
							<td align="left">UserName<font color="red">*</font></td>
							<td align="left"><input value="${customerArray[1]}" style="height:15px;;border-color:#5C5E5F;font-family : tahoma, sans-serif;font-size:9px;font-weight:bold; color:black;" type="text" name="uname" id="uname" required="required" pattern="\w{4,}" placeholder="Enter username" title="Atleast 4 alphanumeric characters"/></td>
						</tr>
						<tr>
							<td align="left">Phone Number<font color="red">*</font></td>
							<td align="left"><input value="${customerArray[2]}" style="height:15px;;border-color:#5C5E5F;font-family : tahoma, sans-serif;font-size:9px;font-weight:bold; color:black;" type="tel" name="PNo" id="PNo" maxlength="10" min=0 required="required" pattern="\d{10}" placeholder="Enter 10 digit phone number"/></td>
						</tr>
						<tr>
							<td align="left">Gender<font color="red">*</font></td>
							<td align="left">
								<input type="radio" name="gender" id="genderM" value="M" required="required" <c:if test="${customerArray[3] == 'M'}">CHECKED</c:if> /> Male&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="gender" id="genderF" value="F" <c:if test="${customerArray[3] == 'F'}">CHECKED</c:if>/>Female</td>
						</tr>
						<tr>
							<td align="left">Email<font color="red">*</font></td>
							<td align="left"><input value="${customerArray[4]}" style="height:15px;;border-color:#5C5E5F;font-family : tahoma, sans-serif;font-size:9px;font-weight:bold; color:black;" type="email" name="mailid" id="mailid" placeholder="Enter Email Id" required="required"/></td>
						</tr>
						<tr>
							<td align="left">Address<font color="red">*</font></td>
							<td align="left"><textarea  style="border-color:#5C5E5F;font-family:tahoma, sans-serif;font-size:9px;font-weight:bold; color:black;" name="addr" id="addr" rows=4 required placeholder="  Enter Address" maxlength=50 cols=22>${customerArray[5]}</textarea></td>
						</tr>
						<tr>
							<td align="left">Password<font color="red">*</font></td>
							<td align="left"><input style="height:15px;;border-color:#5C5E5F;font-family : tahoma, sans-serif;font-size:9px;font-weight:bold;color:black; " value="${customerArray[6]}" type="password" name="pass" id="pass" required="required" title="Atleast one number ,one lowercase,one uppercase and 6 charcaters" placeholder="Enter a valid password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}"/></td>
						</tr>
						<tr>
							<td colspan="2" align="center" ><input type="submit" value="Submit" style="width:80px;height:22px;background-color:#E4881D;font-size:13px;color: white;padding:2px" /></td>
						</tr>			
					</table>
				</form>
			</div></div>
		</div>
	</body>
</html>