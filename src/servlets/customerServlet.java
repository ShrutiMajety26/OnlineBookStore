package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.customerDao;

@WebServlet("/customerServlet")
public class customerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;      
    
    public customerServlet() {
        super();
       
    }	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String pageFlag=request.getParameter("pageFlag");
		customerDao co=new customerDao();
		System.out.println("pageFlag in servlet= "+pageFlag);
		if (pageFlag.equalsIgnoreCase("add"))
		{
			String uname=request.getParameter("uname").trim();
			String PNo=request.getParameter("PNo").trim();
			String gender=request.getParameter("gender")!=null?request.getParameter("gender").trim():null;
			String mailid=request.getParameter("mailid").trim();
			String addr=request.getParameter("addr").trim();
			String pass=request.getParameter("pass").trim();						
			int cnt=co.addCustomer(uname, PNo, gender, mailid, addr, pass);
		
			if (cnt==1)// first method to sen dback to jsp
				request.setAttribute("flag", "Customer Registered!");
			else 
				request.setAttribute("flag", "Customer not added!");
			RequestDispatcher rd=request.getRequestDispatcher("addCustomer.jsp");  
			rd.forward(request, response);//method may be include or forward  
		}
		else if (pageFlag.equalsIgnoreCase("edit"))
		{	
			HttpSession session = request.getSession();
			ArrayList<String> arr=co.getCustomer(session.getAttribute("custId")==null?0:Integer.parseInt(session.getAttribute("custId").toString()));	
			
			if (arr==null)
				request.setAttribute("flag", "Customer not exsits!!");
			else
				request.setAttribute("customerArray", arr);
			request.setAttribute("EditPageFlag", null);
			RequestDispatcher rd=request.getRequestDispatcher("customerAccount.jsp");  
			rd.forward(request, response);//method may be include or forward			
		}
		else if (pageFlag.equalsIgnoreCase("editSave"))
		{
			HttpSession session = request.getSession();
			int cid= Integer.parseInt(session.getAttribute("custId").toString());
			String uname=request.getParameter("uname");
			double PNo=Double.parseDouble(request.getParameter("PNo"));
			String gender=request.getParameter("gender")!=null?request.getParameter("gender").trim():null;
			String mailid=request.getParameter("mailid");
			String addr=request.getParameter("addr");
			String pass=request.getParameter("pass");	
			System.out.println("the values are:"+cid+" "+uname +" " +PNo+" " +gender+" " +mailid+" " +addr+" " +pass);
			int cnt=co.editCustomer(cid,uname, PNo, gender, mailid, addr, pass);
					
			if (cnt==1)// first method to sen dback to jsp
				request.setAttribute("flag", "Details edited successfully");
			else 
				request.setAttribute("flag", "Detail not added!");
			ArrayList<String> arr=co.getCustomer(cid);	
			
			if (arr==null)
				request.setAttribute("flag", "Customer not exsits!!");
			else
				request.setAttribute("customerArray", arr);
			request.setAttribute("EditPageFlag", null);
			RequestDispatcher rd=request.getRequestDispatcher("customerAccount.jsp");  
			rd.forward(request, response);//method may be include or forward	
			
		//	RequestDispatcher rd=request.getRequestDispatcher("customerAccount.jsp?EditPageFlag=null");  
			//rd.forward(request, response);//method may be include or forward  
		}
		else if (pageFlag != null && pageFlag.equalsIgnoreCase("getcust")) 
		{
			int cid = Integer.parseInt(request.getParameter("cid"));				
			ArrayList<String> arr = co.getCustomer(cid);
			RequestDispatcher rd;
			if (arr == null)
			{
				request.setAttribute("flag", "There is no customer with this ID!");
				rd = request.getRequestDispatcher("deleteCustomer.jsp");
			}
			else
			{
				request.setAttribute("customerDetails", arr);
				rd = request.getRequestDispatcher("customerDetails.jsp");
			}			
			rd.forward(request, response);
		}
		else if (pageFlag != null && pageFlag.equalsIgnoreCase("delete")) 
		{
				int cid = 0;
				if(request.getParameter("cid")!=null && !request.getParameter("cid").equalsIgnoreCase(""))
					cid = Integer.parseInt(request.getParameter("cid"));
				int cnt = co.delete(cid);
				String pageName = "deleteCustomer.jsp";
				if (cnt == 1)
					request.setAttribute("flag", "Customer is deleted!");
				else
					request.setAttribute("flag", "Customer is not deleted!");
			
			RequestDispatcher rd = request.getRequestDispatcher(pageName);
			rd.forward(request, response);
		}
	}
}