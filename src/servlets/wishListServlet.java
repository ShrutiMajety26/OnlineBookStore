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
import dao.wishListDao;

@WebServlet("/wishListServlet")
public class wishListServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public wishListServlet() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String pageFlag = request.getParameter("pageFlag");
		System.out.println("pageFlag in servlet wishList =" + pageFlag);
		wishListDao wish = new wishListDao();
		HttpSession session = request.getSession();
		if (session.getAttribute("custId") == null) 
		{
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp?loginFlag=cust");
			rd.forward(request, response);
			return;
		}
		int cid = Integer.parseInt(session.getAttribute("custId").toString());

		if (pageFlag != null && pageFlag.equalsIgnoreCase("add")) 
		{
			long bid = Long.parseLong(request.getParameter("bid"));
			System.out.println("bid: " + bid);

			int cnt = wish.addtoWishList(bid, cid);
			if (cnt==-1)
			{
				ArrayList<ArrayList<String>> arr = wish.showWishList(cid);
				request.setAttribute("wishListArray", arr);
				request.setAttribute("flag","This item is already in your WishList.");
				RequestDispatcher rd = request.getRequestDispatcher("customerAccount.jsp?wishFlag=yes&EditPageFlag=yes");
				rd.forward(request, response);
			}
			else if (cnt == 1)
			{
				ArrayList<ArrayList<String>> arr = wish.showWishList(cid);
				request.setAttribute("wishListArray", arr);
				RequestDispatcher rd = request.getRequestDispatcher("customerAccount.jsp?wishFlag=yes&EditPageFlag=yes");
				rd.forward(request, response);
			} 
			else 
			{
				ArrayList<ArrayList<String>> arr = wish.showWishList(cid);
				//System.out.println("the wishList array items are:" + arr);
				request.setAttribute("wishListArray", arr);
				request.setAttribute("flag","This item is already in your WishList.");
				RequestDispatcher rd = request.getRequestDispatcher("customerAccount.jsp?wishFlag=yes&EditPageFlag=yes");
				rd.forward(request, response);
			}
		}
		if (pageFlag != null && pageFlag.equalsIgnoreCase("display")) {
			ArrayList<ArrayList<String>> arr = wish.showWishList(cid);
			System.out.println("the wishList array items are:" + arr);

			if (arr == null)
				request.setAttribute("flag", "Your WishList is empty!!");
			else
				request.setAttribute("wishListArray", arr);
			
			RequestDispatcher rd = request.getRequestDispatcher("customerAccount.jsp?wishFlag=yes&EditPageFlag=yes");
			rd.forward(request, response);
		} else if (pageFlag != null && pageFlag.equalsIgnoreCase("remove")) {
			long bid = Long.parseLong(request.getParameter("bid"));
			wish.removeWishList(bid, cid);
			System.out.println("the wishList item was removed:" + bid);
			ArrayList<ArrayList<String>> arr = wish.showWishList(cid);
			System.out.println("the array items are:" + arr);

			if (arr == null)
				request.setAttribute("flag", "WishList is empty!");
			else
				request.setAttribute("wishListArray", arr);
			RequestDispatcher rd = request
					.getRequestDispatcher("customerAccount.jsp?wishFlag=yes&EditPageFlag=yes");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}