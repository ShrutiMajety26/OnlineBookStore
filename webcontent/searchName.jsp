<%@page import="dao.bookDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.connectionDao"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="net.sf.json.JSONObject,net.sf.json.JSONArray"%>
<%
		String name = request.getParameter("name");
		String what= request.getParameter("what");
		JSONArray jsonArr = new JSONArray();
		
		JSONObject json=new JSONObject();
        
		ArrayList<String> arr=new bookDao().searchBook(name,what);
		for (int i=0;i<arr.size();i++)
		{
			json.put("name",arr.get(i));
	        json.put("value",arr.get(i));
	        jsonArr.add(json);
		}        
		out.println(jsonArr);
%>