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
import javax.servlet.http.HttpSession;

import dao.SendMailSSL;
import dao.customerDao;
import dao.paymentDao;
@WebServlet("/paymentServlet")

public class paymentServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public paymentServlet() 
    {
        super();    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String totalAmount = null;
		int cid = 0;
		if(request.getParameter("tot")!=null )
			totalAmount = request.getParameter("tot");
		HttpSession session = request.getSession();
		customerDao customerDao = new customerDao();
		if (session.getAttribute("custId")==null)
		{
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
		 cid= Integer.parseInt(session.getAttribute("custId").toString());
		 ArrayList<String> customerInfo = customerDao.getCustomer(cid);
		 request.setAttribute("customerInfo", customerInfo);
		 request.setAttribute("totalAmount",totalAmount);
		 System.out.println("total amount="+totalAmount);
		 RequestDispatcher rd=request.getRequestDispatcher("payment.jsp");  
			rd.forward(request, response);
		 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String pageFlag=request.getParameter("pageFlag");
		System.out.println("pageFlag"+pageFlag);
		//String tomailId = null;
		paymentDao pay=new paymentDao();
		
		if (pageFlag!=null && (pageFlag.equalsIgnoreCase("makepayment")))
		{
			float totalPrice = Float.parseFloat(request.getParameter("amount"));	
			String address=request.getParameter("address").trim();
			String email=request.getParameter("email");
			HttpSession session = request.getSession();
			if (session.getAttribute("custId")==null)
			{
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
			}
			int cid= Integer.parseInt(session.getAttribute("custId").toString());
			System.out.println("Amount="+totalPrice);
			System.out.println("address="+address);
			System.out.println("Customer id="+cid);
			pay.submitOrder(cid,address,totalPrice);
			int oid=pay.getOid();
			System.out.println("the oid and cid for this customer are"+oid +cid);
			int cnt3=pay.addShipItem(oid,cid);
			System.out.println(cnt3);
			if (cnt3>=1)
			{
				try 
				{
					String msg = "<html><br/>" +
							"<img src='C:/Users/Somya/workspace/project1/WebContent/images/bookLogo2.png'><br/>" +
							"<h1 align='center'>671Books-OrderDetails</h1><h2>Order ID- "+oid +"</h2>" +
							"<table cellspacing=10 >" +
								"<tr><th colspan=3>Order Details</th></tr> " +
								"<tr><td><b>Item</b></td><td><b>Qty</b></td><td><b>Price</b></td></tr>" ;
					float totPrice=0;
					ArrayList<ArrayList<String>> arr=pay.getbookDataAfterPayment(oid,cid);
					for (int i=0;i<arr.size();i++)
					{
						ArrayList<String> arr2= arr.get(i);
						msg=msg+"<tr><td >"+arr2.get(1)+"</td><td>"+arr2.get(2)+"</td><td>&#36;"+arr2.get(3)+"</td></tr>" ;
						totPrice=Float.parseFloat(arr2.get(4));
					}		
					System.out.println("totPrice for email= "+totPrice);
					msg=msg+
					"<tr><td colspan=2><b>Total</b></td><td><b>&#36;"+totPrice+"</b></td></tr><tr><td colspan=2><b>Status</b></td><td>Paid</td></tr></table></html>";
					new SendMailSSL().sendMessage("info@671books.com",email, "671 Books-Invoice Details", msg);
					request.setAttribute("flag", "Payment has been processed!Check your email- "+email +" for order details!!");
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
			else			
				request.setAttribute("flag", "Shipping table not updated");		

			RequestDispatcher rd=request.getRequestDispatcher("paymentDone.jsp");  
			rd.forward(request, response);//method may be include or forward  
		}
	}}