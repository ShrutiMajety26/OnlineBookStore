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
import dao.cartDao;

@WebServlet("/cartServlet")
public class cartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public cartServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String pageFlag = request.getParameter("pageFlag");
		System.out.println("pageFlag in servlet cart ="+pageFlag);
		cartDao cart = new cartDao();		
		HttpSession session = request.getSession();
		if (session.getAttribute("custId")==null)
		{
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp?loginFlag=cust");
			rd.forward(request, response);
			return;
		}
		int cid= Integer.parseInt(session.getAttribute("custId").toString());
		
		if (pageFlag!=null && pageFlag.equalsIgnoreCase("add"))
		{
			long bid = Long.parseLong(request.getParameter("bid"));	
			System.out.println("bid: "+bid);
			int qty=1;
			System.out.println("Quantity:"+qty);
			if (qty==0)
			{
				ArrayList<ArrayList<String>> arr = cart.showcart(cid);
				request.setAttribute("cartArray", arr);
				request.setAttribute("errormsg", "Book is not available");
				RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
				rd.forward(request, response); 
			}	
			else
			{
				int cntRecord=0;				
				//if (request.getParameter("checkWishList")!=null && request.getParameter("checkWishList").equals("yes"))
				{
					cntRecord=cart.checkBookAlreadyThere(bid,cid);
					System.out.println("checkBookAlreadyThere= "+cntRecord);
					if (cntRecord>0)
					{
						ArrayList<ArrayList<String>> arr = cart.showcart(cid);
						request.setAttribute("cartArray", arr);
						request.setAttribute("flag", "This item is already in your Cart.<br> If you want copies of this item, please select quantity in cart.");	
						RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
						rd.forward(request, response);
					}
					else
					{
						cntRecord=cart.removeFromWishList(bid, cid);
						int cnt = cart.addtocart(bid, cid, qty);
						if (cnt==1)
						{
							ArrayList<ArrayList<String>> arr = cart.showcart(cid);
							request.setAttribute("cartArray", arr);
							RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
							rd.forward(request, response);
						}
						else
						{
							ArrayList<ArrayList<String>> arr = cart.showcart(cid);
							request.setAttribute("cartArray", arr);
							request.setAttribute("flag", "This item is already in your Cart.<br> If you want copies of this item, please select quantity in cart.");	
							RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
							rd.forward(request, response);
						}
					}
				}
			}
		}
		if(pageFlag!=null && pageFlag.equalsIgnoreCase("display"))
		{
			ArrayList<ArrayList<String>> arr = cart.showcart(cid);
			System.out.println("the array items are:"+arr);
				
			if (arr !=null && arr.size()==0)
				request.setAttribute("flag", "Cart is empty!");
			else		
				request.setAttribute("cartArray", arr);			
		
			RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
			rd.forward(request, response);
		}		
		else if(pageFlag!=null && pageFlag.equalsIgnoreCase("remove"))
		{
			long bid = Long.parseLong (request.getParameter("bid"));			
			cart.removeFromCart(bid, cid);			
			System.out.println("the removed item:"+bid);			
			ArrayList<ArrayList<String>> arr = cart.showcart(cid);				
			if (arr!=null && arr.size()==0)
				request.setAttribute("flag", "Cart is empty!");
			else
				request.setAttribute("cartArray", arr);
			RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
			rd.forward(request, response);			
		}
		else if(pageFlag!=null && pageFlag.equalsIgnoreCase("updateQuantity"))
		{
			String qty=request.getParameter("qty");
			long bid = Long.parseLong (request.getParameter("bid"));			
			cart.updateQuantity(qty,bid,cid);
			//RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
			//rd.forward(request, response);			
		}
	}
}