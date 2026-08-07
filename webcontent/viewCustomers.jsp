<%@page import="dao.connectionDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>

<% Class.forName("oracle.jdbc.driver.OracleDriver"); %>

<HTML>
    <HEAD>
        <TITLE>The tableName Database Table </TITLE>
    </HEAD>

    <BODY>
        <H1 align="center"> Registered Customers </H1>

        <%             
			Connection connection=new connectionDao().createConnection();
            Statement statement = connection.createStatement() ;
            ResultSet resultset = 
            statement.executeQuery("select C_ID,CNAME,GENDER,PHONE,EMAIL from CUSTOMER order by cname") ; 
        %>

        <TABLE class="centered" BORDER="1" align="center">
            <tr>
            	<th bgcolor="7EB610" style="background-color:7EB610;"><b>Customer ID</b>
                <th bgcolor="7EB610" style="background-color:7EB610;"><b>Customer Name</b></th>
                <th bgcolor="7EB610" style="background-color:7EB610;"><b>Gender</b></th>
                <th bgcolor="7EB610" style="background-color:7EB610;"><b>Phone Number</b></th>
                <th bgcolor="7EB610" style="background-color:7EB610;"><b>Email-ID</b></th>
                
            </tr>
            <% while(resultset.next()){ %>
            <tr>
                <td> <%= resultset.getString(1) %></td>
                <td> <%= resultset.getString(2) %></td>
                <td> <%= resultset.getString(3) %></td>
                <td> <%= resultset.getString(4) %></td>
                <td> <%= resultset.getString(5) %></td>
                
            </tr>
            <% } %>
        </TABLE>
    </BODY>
</HTML>