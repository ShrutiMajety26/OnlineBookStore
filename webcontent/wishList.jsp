<%@page import="javax.script.ScriptException"%>
<%@page import="sun.font.Script"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>671 Books- Customer Wish List</title>
<%
	response.addHeader("Cache-Control", "no-store");
%>
</head>
<body>
	<form name="wishListForm" id="wishListForm" action="wishListServlet?pageFlag=remove" method="Post">
		<div id="books_search" style="height: 40px;">
			<h2 style="height:22px; padding-top: 4px; font-family: tahoma, sans-serif; background-image: none; color: white;" 	align="center">
				<span style="text-decoration: none;">Your Wish List</span>
			</h2>
		</div>		
		<div class="maincol_box" style="height:410px;margin-top:-15px;" >
			<div id="bestsellers" style="height: 410px; background-image: none;margin-top:-1px;">
				<div class="content" style="width: 360px; height: 410px; border:5px;overflow-y: auto;" id="mainDiv">
					<c:if test="${not empty flag}"><br/><h3 align="center"><font color=red style="font-size: 12px; text-align: center; font-weight: bolder;"><c:out value="${flag}"></c:out></font></h3></c:if>
					<table style="padding: 10px;overflow:auto" > 
						<c:forEach var="aList" items="${wishListArray}" varStatus="varStat">
							<tr>
								<td style="border: 0px;" valign="middle"><img src="searchServlet?bookId=${aList[0]}" height=89 width=65 style="margin-top: 10px"></td>
								<td>
									<table style="font-size: 12px; color: 5C5E5F; font-weight: bold; border: 0px; width:260px;overflow: visible;">
										<tr>
											<td height="30%"style="color: #7EB610; text-decoration: underline;"	colspan="2"><b><c:out value="${aList[1]}" /></b></td>
										</tr>
										<tr>
											<td style="border: 0px;" colspan="2">ISBN: <c:out value="${aList[0]}" /></td>
										</tr>
										<tr>
											<td class="price" style="float: left;">Price: $<c:out
													value="${aList[3]}" />
											</td>
										</tr>
										<tr>
											<td colspan="2">Author: <c:out value="${aList[2]}" />
											</td>
										</tr>
										<tr>
											<td align="left"><table><tr><td>
												<form action="wishListServlet?pageFlag=remove" method="Post">
													<input type="hidden" name="bid" value="${aList[0]}" />
													<input type="submit" value="Remove" name="remove" style="color: white; background-color: #E4881D; width: 80px;" />
													
												</form></td></tr></table>
											</td>
											<td align="left">
												<form action="cartServlet?pageFlag=add&checkWishList=yes" method="Post" style="margin:0px;">
													<input type="hidden" name="bid" value="${aList[0]}" /> 
													<input type="submit" value="Add to Cart" name="AddtoCart" style="color: white; background-color: #E4881D; width: 80px" />
												</form>
											</td>
										</table>
									</td>
								</tr>
							</c:forEach>
							<tr><td align="center" colspan="2"><br/><br/><input type="button" value="Continue Shopping" style="color: white; background-color: #E4881D;width:150px" onclick="window.location.href='customerAccount.jsp'" /></td></tr>
						</table>
					</div>
				</div>
			</div>
		</form>
		<div align="center" style="margin-top:-30px;">
			<!-- <p><strong><a href="customerAccount.jsp">Continue Shopping</a></strong></p> -->
		
		</div>
</body>
</html>