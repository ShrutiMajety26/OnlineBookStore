package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SendMailSSL;
import dao.bookDao;

@WebServlet("/searchServlet")
public class searchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public searchServlet()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			//System.out.println("i have come to get method of search servlet with flga= "+request.getParameter("pageFlag"));
			ArrayList<ArrayList<String>> arr=null;
			if (request.getParameter("pageFlag")!=null && request.getParameter("pageFlag").equalsIgnoreCase("newRelease"))
			{
				//System.out.println("fetching new releases");				
				arr = new bookDao().getBestSellers("newRelease");
				request.setAttribute("searchList",arr);
				//System.out.println("new releases in search servlet = "+arr);	
			}	
			else if (request.getParameter("pageFlag")!=null && request.getParameter("pageFlag").equalsIgnoreCase("bestsellers"))
			{
				//System.out.println("fetching bestsellers");
				arr = new bookDao().getBestSellers("bestSellers");
				request.setAttribute("searchList",arr);
				//System.out.println("bestSellersList in search servlet = "+arr);				
			}
			else if (request.getParameter("pageFlag")!=null && request.getParameter("pageFlag").equalsIgnoreCase("subscribe"))
			{
				String emailToSubscribe=request.getParameter("subEmail").trim();
				System.out.println("emailToSubscribe= "+emailToSubscribe);
				new SendMailSSL().sendMessage("somya_5666@yahoo.co.in",emailToSubscribe, "671 Books Subscription!!", "You are subscribed to 671 books!");			
			}
			else
			{
				//System.out.println(" in get to retrieve image bookid= "+request.getParameter("bookId"));
				byte[] imgData;			
				long bookId=request.getParameter("bookId")==null?0:Long.parseLong(request.getParameter("bookId"));
				imgData = new bookDao().getPhoto(bookId );
				response.setContentType("image/jpg");
				response.setContentLength(imgData==null?0:imgData.length);
				if (imgData!=null)
					response.getOutputStream().write(imgData);
			}			
		}
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if (request.getParameter("pageFlag")!=null && request.getParameter("pageFlag").equalsIgnoreCase("bestsellers"))		
			doGet(request, response);		
		else
		{
			ArrayList<ArrayList<String>> arr=null;
			try 
			{
				String name=request.getParameter("name");
				String what=request.getParameter("what");
				//System.out.println("seraching on name= "+name +" what= "+what);
				arr = new bookDao().getbookData(name,what);
				request.setAttribute("searchList",arr);
				//System.out.println(" search arr in search servlet = "+arr);
				String searchPageFlag=request.getParameter("searchPageFlag");
				RequestDispatcher rd ;
				if (searchPageFlag!=null && searchPageFlag.equalsIgnoreCase("true"))
					rd= request.getRequestDispatcher("customerAccount.jsp?name="+name+"&what="+what);
				else
					rd = request.getRequestDispatcher("home.jsp?name="+name+"&what="+what);
				rd.forward(request, response);
			} 
			catch (SQLException e) 
			{			
				e.printStackTrace();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}