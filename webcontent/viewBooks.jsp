<%@page import="dao.connectionDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %>
<HTML>
    <HEAD><TITLE>view All books in store </TITLE></HEAD>
    <BODY>
        <H1 align="center">Books Available </H1><%             
		Connection connection=new connectionDao().createConnection();
       	Statement statement = connection.createStatement() ;
        ResultSet resultset = 
        statement.executeQuery("select ISBN,TITLE,AUTHOR,BOOK_QTY from BOOK order by title") ; 
        %>
        <TABLE  BORDER="1">
            <tr>
            	<th bgcolor="7EB610" style="background-color:7EB610;"><b>Front page</b></th>
                <th bgcolor="7EB610" style="background-color:7EB610;"><b>ISBN</b></th>
                <th bgcolor="7EB610"><b>Book Name</b></th>
                <th bgcolor="7EB610"><b>Author Name</b></th>
                <th bgcolor="7EB610"><b>Quantity</b></th>               
            </tr>
            <% while(resultset.next()){ %>
            <tr>
            	<td><img src="searchServlet?bookId=<%=resultset.getString(1) %>" height=50 width=50 style="margin-top:10px "></td>
                <td> <%= resultset.getString(1) %></td>
                <td> <%= resultset.getString(2) %></td>
                <td> <%= resultset.getString(3) %></td>
                <td> <%= resultset.getString(4) %></td>                
            </tr>
            <% } %>
        </TABLE>
    </BODY>
</HTML>