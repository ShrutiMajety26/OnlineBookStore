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
import dao.shippingDao;

@WebServlet("/shippingServlet")
public class shippingServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;     
  
    public shippingServlet() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String pageFlag = request.getParameter("pageFlag");
		System.out.println("pageFlag in servlet cart ="+pageFlag);
		shippingDao ship = new shippingDao();
		HttpSession session = request.getSession();
		int cid= Integer.parseInt(session.getAttribute("custId").toString());
		
		if(pageFlag!=null && pageFlag.equalsIgnoreCase("display"))
		{
			ArrayList<ArrayList<String>> arr = ship.viewOrder(cid);
			//System.out.println("the array items are:"+arr);
						
			if (arr !=null && arr.size()==0)
				request.setAttribute("flag", "No Orders!!");
			else		
				request.setAttribute("shipArray", arr);			

			RequestDispatcher rd = request.getRequestDispatcher("customerAccount.jsp?orderFlag=yes&EditPageFlag=yes");
			rd.forward(request, response);
		}
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}