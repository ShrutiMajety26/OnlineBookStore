package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.orderDao;

@WebServlet(description = "This class displays list of orders and manage orders.", urlPatterns = { "/orderServlet" })
public class orderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public orderServlet() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		orderDao oDao = new orderDao();
		Map<Integer,List<String>> ordersList = oDao.getOrders();
		request.setAttribute("ordersList", ordersList);
		String pageName = "orders.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(pageName);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		orderDao oDao = new orderDao();
		String orderId = null,actionType=null;
		String pageName = null;
		Map<Integer,List<String>> orderInfoMap = null;
		if(request.getParameter("orderId")!=null)
			orderId = (String)request.getParameter("orderId");
		if(request.getParameter("actionType")!=null)
				actionType = (String)request.getParameter("actionType");
		if(actionType!=null && actionType.equalsIgnoreCase("getInfo"))
		{
			orderInfoMap = oDao.getOrderbyId(orderId);
			request.setAttribute("orderInfoMap", orderInfoMap);
			request.setAttribute("orderId", orderId);
			pageName  = "orderInfo.jsp";
		}
		else if(actionType!=null && actionType.equalsIgnoreCase("cancel"))
		{
			int count =  oDao.cancelOrder(orderId);
			Map<Integer,List<String>> ordersList = oDao.getOrders();
			if (count>0)
				request.setAttribute("flag", "Order Cancelled Successfully");
			else
				request.setAttribute("flag", "Order not Cancelled!!");
			request.setAttribute("ordersList", ordersList);
			pageName = "orders.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(pageName);
		rd.forward(request, response);
	}
}