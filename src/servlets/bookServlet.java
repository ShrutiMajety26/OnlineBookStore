package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.bookDao;

@WebServlet("/bookServlet")
@MultipartConfig(maxFileSize = 16177215) // upload file up to 16MB 
public class bookServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public bookServlet() 
    {
        super();    
    }	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String pageFlag=request.getParameter("pageFlag");
		System.out.println("pageFlag "+pageFlag);
		bookDao book=new bookDao();
		if (pageFlag!=null && (pageFlag.equalsIgnoreCase("add")))
		{			
			String bookname = request.getParameter("bookname").trim();			
			float price = Float.parseFloat(request.getParameter("price"));
			String author = request.getParameter("author").trim();
			String publisher = request.getParameter("publisher").trim();
			int publishyear = Integer.parseInt(request.getParameter("publishyear"));
			String genre = request.getParameter("genre").trim();
			int quantity = Integer.parseInt(request.getParameter("quantity"));			
			long isbn = Long.parseLong(request.getParameter("isbn").trim());
			InputStream inputStream = null;			
			Part filePart = request.getPart("cover1");
			System.out.println("file part size= "+filePart.getSize());
			if (filePart != null)			
				inputStream = filePart.getInputStream();
			if (filePart.getSize()>16177215)
			{
				request.setAttribute("flag", "Image too big!!<br>Only Upto 16 MB allowed");
				RequestDispatcher rd = request.getRequestDispatcher("addBook.jsp");
				rd.forward(request, response);
			}
			HttpSession session = request.getSession();
			System.out.println("modifyFlag= "+session.getAttribute("modifyBookFlag"));
			if (session.getAttribute("modifyBookFlag")!=null && session.getAttribute("modifyBookFlag").equals("true"))
			{
				session.removeAttribute("modifyBookFlag");
				System.out.println("updating book....");
				int cnt = book.updateBook(bookname, isbn, price, author,publisher, publishyear,genre, quantity,inputStream,filePart);
				if (cnt == 1)
					request.setAttribute("flag", "Book updated!!");
				else
					request.setAttribute("flag", "Book not updated!");
				RequestDispatcher rd = request.getRequestDispatcher("manageBook.jsp");
				rd.forward(request, response);
			}
			else
			{		
				int cnt = book.addBook(bookname, isbn, price, author,publisher, publishyear,genre, quantity,inputStream,filePart);
				if (cnt==-1)
					request.setAttribute("flag", "Book with same ISBN exisits");
				else if (cnt == 1)
					request.setAttribute("flag", "Book added to the database");
				else
					request.setAttribute("flag", "Book not added!");
				RequestDispatcher rd = request.getRequestDispatcher("addBook.jsp");
				rd.forward(request, response);
			}			
		}
		else if (pageFlag != null && pageFlag.equalsIgnoreCase("manage")) 
		{
			String isbn=request.getParameter("isbn");
			String bookName=request.getParameter("bookname");			
			ArrayList<String> arr = book.getBook(isbn,bookName);
			RequestDispatcher rd;
			if (arr == null)
			{
				request.setAttribute("flag", "Book does not exist");
				rd = request.getRequestDispatcher("manageBook.jsp");
			}
			else
			{
				request.setAttribute("bookDetails", arr);
				rd = request.getRequestDispatcher("addBook.jsp");
			}			
			rd.forward(request, response);
		}
		else if (pageFlag != null && pageFlag.equalsIgnoreCase("delete")) 
		{
			

				long isbn = 0;
				if(request.getParameter("isbn")!=null && !request.getParameter("isbn").equalsIgnoreCase(""))
					isbn = Long.parseLong(request.getParameter("isbn"));
				int cnt = book.delete(isbn);
				String pageName = "manageBook.jsp";
				if (cnt >= 1)
					request.setAttribute("flag", "Book deleted successfully");
				else if(cnt==-1)
					request.setAttribute("flag", "Book can not deleted. It is ordered by a customer!");
			
			RequestDispatcher rd = request.getRequestDispatcher(pageName);
			rd.forward(request, response);
		}
	}
}