<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<title>Sales report</title>
	<script type="text/javascript">
    function updateTextInput(val) {
      document.getElementById('textInput').value=val; 
    }   
    var start = document.getElementById('startMonthYear');
    var end = document.getElementById('endMonthYear');

    start.addEventListener('change', function() {
        if (start.value)
            end.min = start.value;
    }, false);
    end.addEventLiseter('change', function() {
        if (end.value)
            start.max = end.value;
    }, false);
  </script>
</head>
<body>
	<h2 style="margin:0 auto;" align="center"><%=request.getParameter("flag")==null?"":request.getParameter("flag")%> Report By Month and Year</h2><br/>	
	<%if (request.getParameter("flag")!=null && request.getParameter("flag").equals("Sales"))
	{ %>
	<form action="reportServlet?flag=<%=request.getParameter("flag")%>" method="post" >
	<table style="margin:0 auto;border-collapse:separate;border-spacing:11px" >
		<tr>
			<td>Enter Month and year</td>
			<td><input type="month" min="2015-01" max="2015-04" lang="en-GB" placeholder="Choose a date" id="startMonthYear" title="choose a date" name="startMonthYear" required="required"/></td>
			
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr align="center">
			<td align="center" colspan="2">
				<input type="submit" value="Get PDF" />
			</td>			
		</tr>
	</table>
	</form>
	
	<h3 align="center">OR</h3>
	<form action="reportServlet?pageFlag=chartDisplay" method="post">
		<table style="margin:0 auto;border-collapse:separate;border-spacing:11px"><tr align="center"><td align="center"><input type="submit" value="Get Bar Chart" ></td></tr></table>
	</form> <%}
	else
	{%>
		<form action="reportServlet?flag=<%=request.getParameter("flag")%>" method="post" >
	<table style="margin:0 auto;border-collapse:separate;border-spacing:11px" >
		<tr>
			<td>Start Date</td>
			<td><input type="date" min="2015-01-01" max="2015-04-20" lang="en-GB" placeholder="Choose a date" id="startMonthYear" title="choose a date" name="startMonthYear" required="required"/></td>		
			<td>End Date</td>
			<td><input type="date" min="2015-01-01" max="2015-04-20" placeholder="Choose a date" title="choose a date"  id="endMonthYear" name="endMonthYear" required="required"/></td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr align="center">
			<td align="center" colspan="4">
				<input type="submit" value="Get PDF" />
			</td>			
		</tr>
	</table>
	</form>
	<%}%>
</body>
</html>