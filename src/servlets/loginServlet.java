package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.customerDao;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;    
 
    public loginServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String uname=request.getParameter("userName");
		String pass=request.getParameter("passw");
		System.out.println("login page flag= "+request.getParameter("loginFlag"));
		if (request.getParameter("loginFlag")!=null && request.getParameter("loginFlag").equals("admin"))
		{			
			if (uname==null|| pass==null || !pass.equalsIgnoreCase("admin") || !uname.equalsIgnoreCase("admin") )
			{
				request.setAttribute("afterLoginFlag","Invalid Credentials");
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
				rd.forward(request, response);
			}
			else if (uname!=null && pass!= null)
			{
				if (uname.equals("admin") && pass.equals("admin"))
				{
					RequestDispatcher rd=request.getRequestDispatcher("adminAccount.jsp");  
					rd.forward(request, response);
				}				
				else
				{
					RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
					rd.forward(request, response);
				}					
			}
		}
		else if (request.getParameter("loginFlag")!=null && request.getParameter("loginFlag").equals("cust"))
		{
			System.out.println("in else if ");
			customerDao co=new customerDao(); 
			int custId=co.validateCustomer(uname, pass);
			if (custId!=-1)
			{
				uname=Character.toUpperCase(uname.charAt(0)) + uname.substring(1);
				HttpSession session = request.getSession();
				session.setAttribute("userName", uname);
				session.setAttribute("custId", custId);
				request.setAttribute("EditPageFlag","notNull");
				RequestDispatcher rd = request.getRequestDispatcher("customerAccount.jsp");
				rd.forward(request, response);
			}
			else
			{
				request.setAttribute("afterLoginFlag","Invalid Credentials");
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");  
				rd.forward(request, response);
			}				
		}
		else if (request.getParameter("loginFlag")!=null && request.getParameter("loginFlag").equals("logOut"))
		{
			 HttpSession session = request.getSession(false);
		     System.out.println("userName in logout="+session.getAttribute("userName")+" session= "+session);
		     if(session != null)
		     {
		    	 session.removeAttribute("userName");
		    	 session.invalidate();		    	
		     }
		    RequestDispatcher rd=request.getRequestDispatcher("home.jsp");  
			rd.forward(request, response);
		}
	}
}