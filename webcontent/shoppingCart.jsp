<%@page import="java.util.ArrayList"%>
<%@page import="javax.script.ScriptException"%>
<%@page import="sun.font.Script"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	 <meta charset="UTF-8">
		<title>671 Books- Shopping cart</title>
		<%response.addHeader("Cache-Control", "no-store"); %>
		<script type="text/javascript">
			function changeTotal(id,isbn)
			{			
					var total=0;				
					var qty=0;
					for (var i=0;i<document.getElementsByName("bookPrice").length;i++)
					{
						var e=document.getElementById("item"+i);
						qty=e.options[e.selectedIndex].value;			
						var price=parseFloat(document.getElementById("book"+i).value);						
						total+=parseFloat(qty*price);
						total= Math.round(total*100)/100;
					}
					document.getElementById("tot").value=total;
					var e=document.getElementById("item"+id);
					qty=e.options[e.selectedIndex].value;
					if (window.XMLHttpRequest)
					{// code for IE7+, Firefox, Chrome, Opera, Safari
						xmlhttp=new XMLHttpRequest();
					}
					else
					{// code for IE6, IE5
						xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}
					xmlhttp.onreadystatechange=function()
					{
						if (xmlhttp.readyState==4 && xmlhttp.status==200)
					    {
					    	document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
					    }
					}
					//alert("qty sent= "+qty);
					xmlhttp.open("POST","cartServlet?pageFlag=updateQuantity&qty="+qty+"&bid="+isbn,true);
					xmlhttp.send();
				}
			function paymentPage()
			{
				document.getElementById("cartForm").method = "get";
				document.getElementById("cartForm").action = "paymentServlet";
				document.getElementById("cartForm").submit();				
			}
		</script>
</head>
<body>
	<h1 align="center"><%=(session.getAttribute("userName")==null)?"":(session.getAttribute("userName").toString().concat("'s Shopping Cart"))%></h1>	
	<form name= "cartForm" id="cartForm" action="cartServlet?pageFlag=remove" method="Post" >	
	<div>
		<table style="font-size:12px;margin:0 auto;vertical-align:top;border-collapse:separate;border-spacing:1px" >
			<tr>
				<td style=" background-color: #0F6BB2;">&nbsp;&nbsp; </td>
				<td style="font-size:16px;font-weight: bold; vertical-align: middle;; text-align: center; background-color: #0F6BB2;color: white;">Book Title &nbsp;</td>
				<td style="font-size:16px;font-weight: bold; vertical-align: middle; text-align: center; background-color: #0F6BB2;color: white;">Author &nbsp;</td>
				<td style="font-size:16px;font-weight: bold; vertical-align: middle; text-align: center; background-color: #0F6BB2;color: white;">Price &nbsp;</td>
				<td style="font-size:16px;font-weight: bold;width:80px;height:30px ;vertical-align:middle;text-align:center;background-color:#0F6BB2;color:white;">Quantity &nbsp;</td>
				<td style="background-color: #0F6BB2;">&nbsp;</td>
			</tr>
			<tr><td colspan=6 align="center"><h2 align="center" ><font color="red"><%out.println(request.getAttribute("flag")==null?"":request.getAttribute("flag")); %></font></h2></td></tr>
			<%
			if (request.getAttribute("cartArray")!=null)
			{
				ArrayList<ArrayList<String>> arr=(ArrayList<ArrayList<String>>)request.getAttribute("cartArray");
				if (arr.size()>0)
				{%>			
				<c:set var="sum" value="${0}"/> 
				<c:forEach var="aList"  items="${cartArray}" varStatus="varStat">				
				<tr>
					<td><img src="searchServlet?bookId=${aList[0]}" width=60px height=60px border="1"/>
					<td style="width:200px;padding:3px; font-size:14px"><c:out value="${aList[1]}"/></td>
					<td style="width:80px;padding:3px;font-size:14px"><c:out value="${aList[2]}"/></td>
					<td style="width:80px;padding:3px;font-size:14px">$<input type="number" style="width:60px;" id="book${varStat.index}" name="bookPrice" readonly="readonly"value="${aList[3]}" />
					
					<c:set value="${aList[5]}" var="sum"> </c:set>
					<td style="width:50px;padding:3px;font-size:14px">
					<select id="item${varStat.index}" onchange="changeTotal(${varStat.index},${aList[0]})">
						<c:forEach var="selList" varStatus="stat" begin="1" end="${aList[4]}" >
							<option value="${stat.index}" >${stat.index}</option>
						</c:forEach>
					</select>
					<script>
						//alert(document.getElementById("item${varStat.index}").selectedIndex);
							document.getElementById("item${varStat.index}").selectedIndex="${aList[6]}";
							//alert(document.getElementById("item${varStat.index}").selectedIndex);							
					</script>
					
					<td>
							<form  action="cartServlet?pageFlag=remove" method="Post" >
               					<input type="hidden" name="bid" value="${aList[0]}" />
                				<input type="submit" value="Remove" name="remove" style="color: white; background-color: #E4881D;width:80px"/>
                			</form>
      				</td>			
				</tr>
				
			</c:forEach>
		</table>
	</div>
	</form>
	<div align="center">
		<p>&nbsp;</p>
		<p><font color="blue" style="font-weight: bold;">Total Amount:</font>&nbsp;&nbsp; $&nbsp;
		<input type="number" style="width:70px;" id="tot" name="tot" readonly="readonly"  value="${sum}"></p>
		<p>
			<c:url var="url" value="/cartStore.jsp?page=checkout" />
			<input type="button" value="Continue Shopping" style="color: white; background-color: #E4881D;width:150px" onclick="window.location.href='customerAccount.jsp'" />
				<input type="button" value="Checkout" style="color: white; background-color: #E4881D;width:150px" onclick="paymentPage();" />
		</p>
	</div>
	<%}}
	else{%></table>
	<div align="center" style="margin-top:80px;"><input type="button" value="Continue Shopping" style="color: white; background-color: #E4881D;width:150px" onclick="window.location.href='customerAccount.jsp'"/></div>
	<%} %>
</body>
</html>