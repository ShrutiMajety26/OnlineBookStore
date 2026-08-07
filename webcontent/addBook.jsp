<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<script>
			function changeFlag(buttonName)
			{
				document.getElementById("pageFlag").value=buttonName;
			}
		 	function checkMaxlength (object) 
			{
		 		var newVal=0;
			    /* if (object.value.length > 2)
			    	object.value = object.value.slice(0,1);
			     */
			    if (object.value.length>1)
				{
				//  newVal=object.value.substr(0,1);
				// alert(newVal);
				  document.getElementById("quantity").value=document.getElementById("quantity").value.substr(0,1);;
				}

			}
		</script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add book to inventory</title>
		<style type="text/css">
			table.centered { 
				width:580px; 
				top:80px; 
				left:45%; 
				margin-left:120px;
			}
		</style>
	</head>
	<body><%
		if (request.getParameter("flag")!=null  && request.getParameter("flag").equalsIgnoreCase("empty"))
		{%>
			<br/><h2 align="center">Welcome 671 Books Administrator!!</h2><%
		}
		else
		{%>
			<h1 align="center"><font style="text-decoration: underline;"><c:choose><c:when test="${not empty bookDetails}">Edit Book</c:when><c:otherwise>Add Book</c:otherwise> </c:choose></font></h1>
			<h4 align="center"><font color="red"><%out.println(request.getAttribute("flag")==null?"":request.getAttribute("flag")); %></font></h4>
			<form name="BookForm" id="BookForm" action="bookServlet" method="post" enctype="multipart/form-data" >
				<c:if test="${not empty bookDetails}">
					<%	session.setAttribute("modifyBookFlag","true"); %>
				</c:if>
				<c:forEach var="book" items="bookDetails" >						
					<table class="centered" style="font-size:14px;border-spacing:11px"  >
					<tr>
						<c:if test="${not empty bookDetails}">
						<td rowspan=10>
							<img src="searchServlet?bookId=${bookDetails[0]}" height=150 width=150 style="margin-top:10px" alt="Image not Available">
						</td>
						</c:if>					
						<td>
						<table style="width:99%;;border-collapse:separate;border-spacing:7px" >
						<tr>
						    <td>Book Name<font color="red">*</font></td>
						    <td><input type="text" name="bookname" id="bookname" required="required" maxlength="100" value="${bookDetails[1]}"/>
						    	<input type="hidden" name="pageFlag" id="pageFlag" required="required" value=""/>
						    </td> 
						  </tr>
						<tr>
						    <td>ISBN<font color="red">*</font></td>
						    <td>
						    	<c:choose>
						    		<c:when test="${not empty bookDetails}">
						    			<input type="tel" name="isbn" id="isbn" maxlength="13"  required="required" pattern=".{13,13}"  value="${bookDetails[0]}" title="ISBN-13 digit no" readonly="readonly">
						    		</c:when>
						    		<c:otherwise>
						    			<input type="tel" name="isbn" id="isbn" maxlength="13"  required="required" pattern=".{13,13}"  value="${bookDetails[0]}" title="ISBN-13 digit no" >
						    		</c:otherwise>
						    	</c:choose>					    		
						    </td>						     
						</tr>
						<tr>
							<td>Price($)<font color="red">*</font></td>
							<td><input type="text" name="price" id="price" maxlength="5" required="required" pattern="^((\d+)|(\d{1,3})(\,\d{3}|)*)(\.\d{2}|)$" value="${bookDetails[2]}"
								placeholder="Price from 000.0 to 999.9" title="Price from 000.0 to 999.9" /></td>
						</tr>		
						<tr>
							<td>Author Name<font color="red">*</font></td>
							<td ><input type="text" name="author" id="author" required="required" maxlength="100" value="${bookDetails[3]}"/></td>
						</tr>
						<tr>
							<td>Publisher<font color="red">*</font></td>
							<td><input type="text" name="publisher" id="publisher" required="required" maxlength="50" value="${bookDetails[4]}"/></td>
						</tr>
						<tr>
							<td>Published year<font color="red">*</font></td>
							<td><input type="text" name="publishyear" style="width:160px;" value="${bookDetails[5]}" id="publishyear" maxlength="4" required="required" title="year in format(yyyy)" pattern="[1-2][0-9][0-9][0-9]"  min=1900 max=2015 /></td>
						</tr>
						<tr>
							<td>Genre<font color="red">*</font></td>
							<td><input type="text" name="genre" id="genre" required="required"  maxlength="25" value="${bookDetails[6]}"/></td>
						</tr>
						<tr>
							<td>Quantity<font color="red">*</font></td>
							<td><input type="number" name="quantity"  style="width:160px;" id="quantity" min=1 max=99 maxlength="2" required="required" value="${bookDetails[7]}"  placeholder="Enter value between 1 to 99"  title= "Enter value between 1 to 99"/></td>
						</tr>
						<tr>
							<td>Cover Page<font color="red"></font></td>
							<td><input type="file" name="cover1" id="cover" ></td>
						</tr>
						<tr>
							<!-- <td colspan="2" align="center"><input type="submit"  value="Submit" style="width:80px;height:20px;background-color:#E4881D;font-size:13px;color: white;padding:2px;" /></td> -->
							<c:choose>
						    		<c:when test="${not empty bookDetails}">
						    			<td><input type="submit"  value="Update" style="width:80px;height:20px;background-color:#E4881D;font-size:13px;color: white;padding:2px;" onclick="changeFlag('add')"/></td>
						    			<td><input type="submit"  value="Delete book" style="width:80px;height:20px;background-color:#E4881D;font-size:13px;color: white;padding:2px;" onclick="changeFlag('delete')"/></td>
						    		</c:when>
						    		<c:otherwise>
						    			<td colspan="2" align="center"><input type="submit"  value="Submit" style="width:80px;height:20px;background-color:#E4881D;font-size:13px;color: white;padding:2px;" onclick="changeFlag('add')" /></td>
						    		</c:otherwise>
						    	</c:choose>
						</tr></table>
					</td></tr>
					</table>
				</c:forEach>
			</form><%
		} %>
	</body>
</html>