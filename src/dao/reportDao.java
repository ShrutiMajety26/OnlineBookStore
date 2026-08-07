package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;


public class reportDao 
{
	public ResultSet getReportListData1(String startMonthYear,String endMonthYear) throws ParseException //purchase
	{
		Statement st=null;
		 ResultSet data = null;
		 try
		 {
			 Connection con = new connectionDao().createConnection();
			 st = con.createStatement();			 	
			 System.out.println("startMonthYear= "+startMonthYear);
			 String startdt=startMonthYear;
			 String enddt=		endMonthYear	; 			 
			 //String query=("select ISBN, title, SUM(o_qty) AS purchased,to_char(doi,'MM-DD-YYYY'),front_page FROM (book natural JOIN ship_item JOIN shipping USING (o_id) ) WHERE trunc(doi) BETWEEN to_date('"+startdt+"','YYYY-MM-DD') AND TO_DATE('"+enddt+"','YYYY-MM-DD') GROUP BY ISBN,  title,   o_qty,   doi ORDER BY title");
			 String query="Select V1.*,Front_Page  from (SELECT ISBN, title, SUM(o_qty) AS purchased, TO_CHAR(doi,'MM-DD-YYYY') as doi " +
			 		"FROM (book NATURAL JOIN ship_item JOIN shipping USING (o_id) ) " +
			 		"Where Trunc(Doi) Between To_Date('"+startdt+"','YYYY-MM-DD') And To_Date('"+enddt+"','YYYY-MM-DD') " +
			 		"Group By Isbn, Title, O_Qty ,Doi )  V1  Join Book  ON book.isbn = v1.isbn order by V1.doi";
			 System.out.println(query);
			 data = st.executeQuery(query);
		 } 
		 catch (SQLException ex) 
		 {
			 ex.printStackTrace(System.err);
		 }
		 return data;
	}
	public ResultSet getReportListData2(String monthYear)
	{
		Statement st=null;
		 ResultSet data = null;
		 try
		 {
			 Connection con = new connectionDao().createConnection();
			 st = con.createStatement();
			 String query=("Select Distinct Extract(Year From Doi)  As Year," +
			 		"To_Char(To_Date(Extract(Month From Doi),'MM'),'Month') As Mon,Isbn,Title,Sum(O_Qty)" +
			 		" As Copies_Sold From Book Natural Join Ship_Item Join Shipping Using (O_Id) " +
			 		"Where TO_CHAR(doi,'YYYY-mm')='"+monthYear+"' Group By Isbn,Title,Extract(Month From Doi)," +
			 		" extract(YEAR FROM doi)  order by  copies_sold desc");
			 System.out.println(query);
			 data = st.executeQuery(query);
		 } 
		 catch (SQLException ex) 
		 {
			 ex.printStackTrace(System.err);
		 }
		 return data;
	}
	public ArrayList<Integer> getSalesByMonth()
	{
		Statement st=null;
		ResultSet data = null;
		ArrayList<Integer> arr=null;
		try
		{
			 Connection con = new connectionDao().createConnection();
			 st = con.createStatement();			 
			 String query=("SELECT m.month as Month, nvl(md.purchased, 0) as Count FROM (   select '01' month from dual union all    select '02' month from dual union all    select '03' month from dual union all    select '04' month from dual union all    select '05' month from dual union all    select '06' month from dual union all    select '07' month from dual union all    select '08' month from dual union all    select '09' month from dual union all    select '10' month from dual union all  select '11' month from dual union all select '12' month from dual ) m LEFT OUTER JOIN ( select DISTINCT extract(MONTH FROM doi) AS MONTH,   SUM(o_qty)  AS purchased FROM ship_item JOIN shipping USING (o_id) GROUP BY extract(MONTH FROM doi) ) md ON m.month = md.month ORDER BY m.month");
			 System.out.println(query);
			 data = st.executeQuery(query);
			 arr=new ArrayList<Integer>();
			 while(data.next())			 
				 arr.add(data.getInt(2));			 
		 }
		 catch (SQLException ex) 
		 {
			 ex.printStackTrace(System.err);
		 }
		 return arr;
	}
}