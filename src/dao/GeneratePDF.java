package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class GeneratePDF
{
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	public static Document createPDF1(String file, String startMonthYear,String endMonthYear) throws SQLException, ParseException 
	{
 		Document document = null; 
		try 
		{
			document = new Document(PageSize.A3);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open(); 
			addMetaData(document); 
			addTitlePage(document,"Purchase"); 
			createTable1(document,startMonthYear,endMonthYear);
			 
			document.close(); 
		}
		catch (FileNotFoundException e) 
		{
 			e.printStackTrace();
		}
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		return document; 
	}
	
	public static Document createPDF2(String file, String startMonthYear) throws SQLException 
	{
 		Document document = null; 
		try 
		{
			document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open(); 
			addMetaData(document); 
			addTitlePage(document,"Sales"); 
			createTable2(document,startMonthYear);			
			document.close(); 
		}
		catch (FileNotFoundException e) 
		{
 			e.printStackTrace();
		}
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		return document; 
	}
	
 
	private static void addMetaData(Document document) 
	{
		document.addTitle("PDF report");
		document.addSubject("PDF report");
		document.addAuthor("671 Books");
		document.addCreator("671 Books");
	}
 
	private static void addTitlePage(Document document,String reportType) throws DocumentException 
	{
 		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph(reportType+" Report for 671 Books ", TIME_ROMAN));		
		creteEmptyLine(preface, 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		preface.add(new Paragraph("Report created on "+ simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
		document.add(preface); 
	}
 
	private static void creteEmptyLine(Paragraph paragraph, int number) 
	{
		for (int i = 0; i < number; i++)		
			paragraph.add(new Paragraph(" "));		
	}
 
	private static void createTable1(Document document,String startMonthYear,String endMonthYear) throws DocumentException, SQLException, ParseException 
	{
		ResultSet query_set=null;
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		PdfPTable table;
		query_set = new reportDao().getReportListData1(startMonthYear,endMonthYear);
		if (query_set.next())
		{
			table = new PdfPTable(5);
			BaseColor backgroundColor = WebColors.getRGBColor("#CCCCCC");
		
			PdfPCell c1 = new PdfPCell(new Phrase("Front Page"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			table.setHeaderRows(1);
			
			c1 = new PdfPCell(new Phrase("ISBN"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
	 
			c1 = new PdfPCell(new Phrase("Title"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Purchased Qty"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			table.setHeaderRows(1);
			
			c1 = new PdfPCell(new Phrase("Date of Invoice"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			table.setHeaderRows(1);		
				
			do
			{
				table.setWidthPercentage(100);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				Blob imageBlob = query_set.getBlob(5);
				byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				Image image;
				try 
				{
					image = Image.getInstance(imageBytes);
					image.scaleAbsolute(60f,60f);
					table.addCell(new PdfPCell(image));			
					table.addCell(query_set.getString(1));
					table.addCell(query_set.getString(2));
					table.addCell(query_set.getString(3));
					table.addCell(query_set.getString(4));
					//table.addCell(query_set.getString(5));
				/*	table.addCell(query_set.getString(6));
					table.addCell(query_set.getString(7));
					table.addCell(query_set.getString(8));
					table.addCell(query_set.getString(9));*/
				}			
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			while(query_set.next());
			float[] columnWidths = new float[] {5f,10f,20f,8f,10f};
			table.setWidths(columnWidths);
		}
		else
		{	
			table = new PdfPTable(1);
			//BaseColor backgroundColor = WebColors.getRGBColor("#CCCCCC");		
			/*PdfPCell c1 = new PdfPCell(new Phrase("Front Page"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			table.setHeaderRows(1);*/	
			table.setWidthPercentage(50);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			table.addCell("No records Found!!");
			
			float[] columnWidths = new float[] {20f};
			table.setWidths(columnWidths);
		}
		document.add(table);
		//float[] columnWidths = new float[] {8f,30f,23f,10f,15f,10f,10f,15f,10f};
		//float[] columnWidths = new float[] {10f,8f,30f,10f,15f,10f,10f,15f,10f};
		
		
	}
	
	private static void createTable2(Document document,String monthYear) throws DocumentException, SQLException 
	{
		ResultSet query_set=null;
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		query_set = new reportDao().getReportListData2(monthYear);
		float[] columnWidths;
		PdfPTable table;
		if (query_set.next())
		{
			table = new PdfPTable(5);
			BaseColor backgroundColor = WebColors.getRGBColor("#CCCCCC");
			PdfPCell c1 = new PdfPCell(new Phrase("Year"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Month"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("ISBN"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("Title"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Quantity Sold"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(backgroundColor);
			table.addCell(c1);	
	 
			do
			{		
				table.setWidthPercentage(100);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				//Blob imageBlob = query_set.getBlob(10);
				//byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
				//Image image;
				try 
				{
					//image = Image.getInstance(imageBytes);
					//image.scalePercent(2f);
					//table.addCell(image);		
					if (query_set.getString(1)!=null)
					{
						table.addCell(query_set.getString(1));
						table.addCell(query_set.getString(2));
						table.addCell(query_set.getString(3));
						table.addCell(query_set.getString(4));
						table.addCell(query_set.getString(5));
					}							
				}
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}while(query_set.next());
			columnWidths = new float[] {8f,8f,15f,20f,10f};
		}
		else
		{			
			table = new PdfPTable(1);			
			table.addCell("No Records Found!!");
			columnWidths = new float[] {1f};
		}
		table.setWidths(columnWidths);
		document.add(table);
	}	
}