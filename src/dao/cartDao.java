package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class cartDao {
	
	public int addtocart(long bid, int cid,int qty)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int cnt = 0;
		try
		{
			con = new connectionDao().createConnection(); //to create a connection with oracle 
			//	System.out.println("con= " + con);
			ps = con.prepareStatement("insert into cart values(?,?,?)");
			ps.setLong(1, bid);
			ps.setInt(2, cid);
			ps.setInt(3, qty);
			
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	
	public int getQty(long bid)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int qty=0;
		try {
			
			con = new connectionDao().createConnection(); //to create a connection with oracle 
			System.out.println("con= " + con);
			ps = con.prepareStatement("select book_qty from book where ISBN="+bid);
			rs = ps.executeQuery();// execute select statement
			
			while(rs.next())
				qty = Integer.parseInt(rs.getString(1));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return qty;
	}
	
	public ArrayList<ArrayList<String>> showcart(int cid) 
	{
		ArrayList<ArrayList<String>>arr = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
			try {
					con = new connectionDao().createConnection();
					//System.out.println("con= " + con);
					String query="select b.ISBN,b.title,b.author,b.price,b.book_qty,Sum(Sum(B.Price*c_qty)) Over (ORDER BY B.Isbn,B.Title,B.Author,B.Price,B.Book_Qty ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)  " +
							"    AS cumulative_amount,C.C_Qty-1 from book b,cart c where b.ISBN=c.ISBN and c.c_id="+cid +"  and book_qty>0 group by B.Isbn,B.Title,B.Author,B.Price,B.Book_Qty,C.C_Qty ";
					System.out.println(query);
					ps = con.prepareStatement(query);
					rs = ps.executeQuery();
					
					arr = new ArrayList<ArrayList<String>>();
					
					while (rs.next()) 
					{	
						ArrayList<String> arr2=new ArrayList<String>();
						arr2.add(rs.getString(1));
						arr2.add(rs.getString(2));
						arr2.add(rs.getString(3));
						arr2.add(rs.getString(4));
						arr2.add(rs.getString(5));
						arr2.add(rs.getString(6));
						arr2.add(rs.getString(7));
						arr.add(arr2);						
					}
					//System.out.println(arr);
				}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally {
				try {
					ps.close();
					con.close();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			return arr;
	}
	
	public int removeFromCart(Long bid,int cid)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int cnt = 0;
		try {
			
			con = new connectionDao().createConnection(); //to create a connection with oracle 
			System.out.println("con= " + con);
			
			ps = con.prepareStatement("delete from cart where ISBN= "+bid +"and c_id=" + cid);
			
			cnt = ps.executeUpdate(); // execute delete statement
			System.out.println("deleted row:"+cnt);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	public int removeFromWishList(Long bid, int cid)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int cnt = 0;
		try 
		{			
			con = new connectionDao().createConnection(); //to create a connection with oracle 			
			ps = con.prepareStatement("delete from wishlist where ISBN= "+bid +"and c_id=" + cid);			
			cnt = ps.executeUpdate(); // execute delete statement
			System.out.println("deleted row:"+cnt);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
	public int checkBookAlreadyThere(long bid,int cid)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int qty=0;
		try {
			
			con = new connectionDao().createConnection(); //to create a connection with oracle 
			ps = con.prepareStatement("select count(*) from cart where isbn="+bid +" and c_id="+cid);
			rs = ps.executeQuery();// execute select statement			
			if(rs.next())
				qty = Integer.parseInt(rs.getString(1));				
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
		return qty;
	}
	public int updateQuantity(String qty,long bid,int cid)
	{
		Connection con = null;		
		PreparedStatement ps = null;
		int rowsUpadted=-1;
		try {
			
			con = new connectionDao().createConnection(); //to create a connection with oracle
			String query="update cart set c_qty="+qty +" where ISBN="+bid+" and c_id= "+cid;
			System.out.println(query);
			ps = con.prepareStatement(query);
			rowsUpadted = ps.executeUpdate();// execute select statement						
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
		return rowsUpadted;
	}
}