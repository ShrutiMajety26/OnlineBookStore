package dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.Part;

public class bookDao 
{
	public int addBook(String bookname, long isbn, float price, String author, String publisher, int publishyear,String genre,int quantity,InputStream inputStream, Part filePart) 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int cnt = 0;
		try 
		{
			con = new connectionDao().createConnection();
			ps=con.prepareStatement("select count(*) from book where isbn="+ isbn);
			rs=ps.executeQuery();
			int exists=0;
			if (rs.next())			
				exists=rs.getInt(1);
			System.out.println("exixts= "+exists);
			ps.close();
			if (exists==1)				
				return -1;
			else
			{				
				ps = con.prepareStatement("insert into book values(?,?,?,?,?,?,?,?,?)");
				ps.setLong(1, isbn);
				ps.setString(2, bookname);						
				ps.setFloat(3, price); 
				ps.setString(4, author);
				ps.setString(5, publisher);
				ps.setInt(6, publishyear);
				ps.setString(7, genre);
				ps.setInt(8, quantity);
				if (inputStream != null)	
					ps.setBinaryStream (9, inputStream,(int)filePart.getSize());			
				cnt = ps.executeUpdate(); // execute insert statement
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		finally 
		{
			try 
			{
				rs.close();
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public ArrayList<String> getBook(String isbn,String bookName) 
	{
		ArrayList<String> arr = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		try 
		{
			con = new connectionDao().createConnection();			
			String query;
			if (isbn.length()>0)			
				query="select * from book where isbn="+isbn+"  order by title";
			else
				query="select * from book where  LOWER(title) like lower('%"+bookName+"%') order by title ";
			
			System.out.println(query);
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next())
			{
				arr = new ArrayList<String>();
				arr.add(rs.getString(1));
				arr.add(rs.getString(2));
				arr.add(rs.getString(3));
				arr.add(rs.getString(4));
				arr.add(rs.getString(5));
				arr.add(rs.getString(6));
				arr.add(rs.getString(7));
				arr.add(rs.getString(8));				
			}
			//System.out.println(arr);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				rs.close();
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return arr;
	}

	public int updateBook(String bookname, long isbn, float price, String author, String publisher, int publishyear,String genre,int quantity,InputStream inputStream, Part filePart ) 
	{		
		Connection con = null;
		PreparedStatement ps = null;
		int cnt = 0;
	/*	System.out.println("keyWord entered  1= " + bookname);
		System.out.println("keywords enetered= " + isbn);
		System.out.println("keyWord entered = " + genre);
		System.out.println("keyWord entered = " + price);
		System.out.println("keyWord entered = " + author);
		System.out.println("keyWord entered = " + publisher);
		System.out.println("keywords enetered= " + publishyear);
		System.out.println("keywords enetered= "+quantity);*/
		String query = null;
		try 
		{			
			if(filePart.getSize()>0)			
				query = "update book set title =?,price=?,author=?,publisher=?,publishing_year=?, genre=?,book_qty=?,front_page=? where ISBN=?";			
			else
				query = "update book set title =?,price=?,author=?,publisher=?,publishing_year=?, genre=?,book_qty=? where ISBN=?";	
			System.out.println("query= "+query);
			con = new connectionDao().createConnection();			
			ps = con.prepareStatement(query);			
			ps.setString(1, bookname);			
			ps.setFloat(2, price); // set input parameter 5
			ps.setString(3, author);
			ps.setString(4, publisher);
			ps.setInt(5, publishyear);
			ps.setString(6, genre); // set input parameter 3
			ps.setInt(7, quantity);
			if(filePart.getSize()>0)
			{
				ps.setBinaryStream (8, inputStream,(int)filePart.getSize());				
				ps.setLong(9, isbn);
			}
			else			
				ps.setLong(8, isbn);		
			cnt = ps.executeUpdate(); // execute insert statement
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		finally 
		{
			try 
			{
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	
	public ArrayList<String> searchBook(String name,String what)
	{		
		ArrayList<String> arr = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{		
			String query=null;
			con = new connectionDao().createConnection();
			if (what.trim().length()==0)
				query="select title from book where LOWER(title) like lower('%"+name+"%') or lower(publisher) like ('%"+name+"%') or lower(author) like ('%"+name+"%') or lower(genre) like ('%"+name+"%') or PUBLISHING_YEAR like ('%"+name+"%') order by title";
			else
				query="select title from book where LOWER("+what+") like lower('%"+name+"%') order by title";
			System.out.println("query= "+query);
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			arr = new ArrayList<String>();
			while (rs.next())			
				arr.add(rs.getString(1));			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				rs.close();
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return arr;
	}
	public byte[] getPhoto (long bookId) throws Exception, SQLException
	{
		Blob img =null;
		Connection con = null;
		byte[] imgData = null ;
		Statement stmt=null;
		ResultSet rset =null;
		try
		{
			con = new connectionDao().createConnection();
			stmt = con.createStatement();			
			//System.out.println("bok id for image= "+bookId);
			rset= stmt.executeQuery("select front_page From book where ISBN= "+bookId +" and front_page is not null order by title");
			while (rset.next())
			{
				img = rset.getBlob(1);
				imgData = img.getBytes(1, (int) img.length());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rset.close();
			stmt.close();
			con.close();
		}
		return imgData;
	}
	public ArrayList<ArrayList<String>> getbookData (String name,String what) throws Exception, SQLException
	{
		ArrayList<ArrayList<String>> arr=null;
		ArrayList<String> arr2=null;
		Connection con = null;
		Statement stmt=null;
		ResultSet rset =null;
		//int cnt=0;
		String query=null;
		try
		{
			con = new connectionDao().createConnection();
			stmt = con.createStatement();
			if (what.trim().length()==0)
				query="select ISBN,TITLE,PRICE,AUTHOR,PUBLISHER,PUBLISHING_YEAR,Book_Qty,genre,FRONT_PAGE from book where LOWER(title) like lower('%"+name+"%') or lower(publisher) like ('%"+name+"%') or lower(author) like ('%"+name+"%') or lower(genre) like ('%"+name+"%') or PUBLISHING_YEAR like ('%"+name+"%') order by title";
			else
				query="select ISBN,TITLE,PRICE,AUTHOR,PUBLISHER,PUBLISHING_YEAR,Book_Qty,genre,FRONT_PAGE from book where LOWER("+what+") like lower('%"+name+"%') order by title";
			System.out.println(query);
			rset= stmt.executeQuery(query);					
			arr=new ArrayList<ArrayList<String>>();
			while (rset.next())
			{			
				//System.out.println("adding to arr "+rset.getString(1));
				arr2=new ArrayList<String>();
				arr2.add(rset.getString(1));
				arr2.add(rset.getString(2));	
				arr2.add(rset.getString(3));	
				arr2.add(rset.getString(4));
				arr2.add(rset.getString(5));
				arr2.add(rset.getString(6));
				arr2.add(rset.getString(7));
				arr2.add(rset.getString(8));
				arr.add(arr2);
			}			
			//System.out.println(" arr size= "+arr.size()+" arr in doa= "+arr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rset.close();
			stmt.close();
			con.close();
		}
		return arr;
	}
	public ArrayList<ArrayList<String>> getBestSellers (String fetchFlag) throws Exception, SQLException
	{
		ArrayList<ArrayList<String>> arr=null;
		ArrayList<String> arr2=null;
		Connection con = null;
		Statement stmt=null;
		ResultSet rset =null;
		try
		{
			con = new connectionDao().createConnection();
			stmt = con.createStatement();
			String query="";
			if (fetchFlag.equalsIgnoreCase("bestSellers"))
				query="SELECT * from (SELECT ISBN,TITLE,PRICE,AUTHOR,PUBLISHER,PUBLISHING_YEAR,book_qty,genre,SUM(o_qty) as TOTAL_SOLD FROM ship_item  natural join book  GROUP BY (ISBN,TITLE,PRICE,AUTHOR,PUBLISHER,PUBLISHING_YEAR,book_qty,genre) ORDER BY SUM(o_qty) DESC ) where ROWNUM <=10";				
			else if (fetchFlag.equalsIgnoreCase("newRelease"))
				query= "select ISBN,TITLE,PRICE,AUTHOR,PUBLISHER,PUBLISHING_YEAR,Book_Qty,genre,FRONT_PAGE from book where PUBLISHING_YEAR>=2012 order by title";
			System.out.println(query);
			rset= stmt.executeQuery(query);
			if (rset.isBeforeFirst())
				arr=new ArrayList<ArrayList<String>>();
			while (rset.next())
			{	
				arr2=new ArrayList<String>();
				arr2.add(rset.getString(1));
				arr2.add(rset.getString(2));	
				arr2.add(rset.getString(3));	
				arr2.add(rset.getString(4));
				arr2.add(rset.getString(5));
				arr2.add(rset.getString(6));
				arr2.add(rset.getString(7));
				arr2.add(rset.getString(8));
				arr.add(arr2);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rset.close();
			stmt.close();
			con.close();
		}
		return arr;
	}
	public int delete(long isbn) 
	{
		int cnt=0;
		Connection con = null;
		PreparedStatement ps = null;
		
		String query="select Count(ISBN) from"+
				"(select b.ISBN from Ship_Item s, Book b where b.ISBN=s.ISBN and b.Isbn=? union"+
				"select b.ISBN from CART s, Book b where b.ISBN=s.ISBN and b.Isbn=? union "+
				"select b.ISBN from WISHLIST s, Book b where b.ISBN=s.ISBN and b.Isbn=?)";
		String query1 = "delete from book where isbn=  "+isbn;
		
		try 
		{
			con = new connectionDao().createConnection();
			System.out.println("===query---"+query1);
			ps=con.prepareStatement(query);
			ps.setLong(1, isbn);
			ps.setLong(2, isbn);
			ps.setLong(3, isbn);
			ResultSet rs=ps.executeQuery(query1);
			if(rs.next())
				cnt=Integer.parseInt(rs.getString(1));
			ps.close();
			if (cnt<=0)
				return -1;	
			else
			{
				ps = con.prepareStatement(query);
				cnt = ps.executeUpdate();
				return cnt;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;		
	}
}