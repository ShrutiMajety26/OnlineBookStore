package servlets;

import dao.GeneratePDF;
import dao.salesChart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream; /*holds the XML output of the SQL results */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.ui.RefineryUtilities;

@WebServlet("/reportServlet")
public class reportServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public reportServlet() 
	{
		super();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		if (request.getParameter("pageFlag")!=null && request.getParameter("pageFlag").equals("chartDisplay"))
		{
			 final salesChart demo = new salesChart("Monthly Sales");
		     demo.pack();
		     RefineryUtilities.centerFrameOnScreen(demo);
		     demo.setVisible(true);
		     RequestDispatcher rd=request.getRequestDispatcher("report.jsp?flag=Sales");  
			 rd.forward(request, response);//method may be include or forward  
		}
		else
		{
			String startMonthYear=request.getParameter("startMonthYear");
			String endMonthYear=request.getParameter("endMonthYear");
			System.out.println("monthyear= "+startMonthYear);
			final ServletContext servletContext = request.getSession().getServletContext();
			final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			final String temperotyFilePath = tempDirectory.getAbsolutePath();
	
			String fileName = "Report_" + System.currentTimeMillis()+ ".pdf";
			response.setContentType("application/pdf");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "max-age=0");
			response.setHeader("Content-disposition", "attachment; " + "filename="+ fileName);
	
			try 
			{
				if (request.getParameter("flag")!=null && request.getParameter("flag").equalsIgnoreCase("purchase"))
					GeneratePDF.createPDF1(temperotyFilePath + "\\" + fileName,startMonthYear,endMonthYear);
				else
					GeneratePDF.createPDF2(temperotyFilePath + "\\" + fileName,startMonthYear);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				baos = convertPDFToByteArrayOutputStream(temperotyFilePath + "\\"+ fileName);
				OutputStream os = response.getOutputStream();
				baos.writeTo(os);
				os.flush();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	private static ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) 
	{
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1)			
				baos.write(buffer, 0, bytesRead);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if (inputStream != null) 
			{
				try
				{
					inputStream.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return baos;
	}
}